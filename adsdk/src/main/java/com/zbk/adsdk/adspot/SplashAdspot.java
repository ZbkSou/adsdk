package com.zbk.adsdk.adspot;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.AdService;
import com.zbk.adsdk.AdTypeUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZBK on 2021-01-24.
 *
 * @function
 */
public class SplashAdspot extends BaseAdspot {

    private SplashAD splashAD;
    private TextView skipView;
    private SplashADListener gdtAdListener;
    private CountDownTimer timer;
    //   显示广告容器
    public ViewGroup adContainer;
    public ImageView imageView;
    private String type;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //加载网络成功进行UI的更新,处理得到的图片资源
                case 1:
                    //通过message，拿到字节数组
                    byte[] Picture = (byte[]) msg.obj;
                    //使用BitmapFactory工厂，把字节数组转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    //通过imageview，设置图片
                    imageView.setImageBitmap(bitmap);
                    adListener.onAdShow(type);
                    timer = new CountDownTimer(5000, 500) {//广告总展现时长5秒，倒计时刷新频率200毫秒

                        @Override
                        public void onTick(long l) {
                            if (l >= 3000) {
                                adService.reportExposure(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
                            }
                            if (adListener != null) {
                                adListener.onADTick(l); //广告展现计时
                            }

                        }

                        @Override
                        public void onFinish() {
                            if (adListener != null) {
                                adListener.onAdDismiss(); //广告展示完毕
                            }
                        }
                    };
                    adContainer.addView(imageView);
                    //广告展现倒计时
                    timer.start();
                    skipView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            timer.cancel();
                            adService.reportSkip(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
                            if (adListener != null) {
                                adListener.onAdDismiss(); //广告展示完毕
                            }
                        }
                    });
                    break;

            }
        }
    };

    public SplashAdspot(Context mContext, ViewGroup adContainer, AdListenter adListener, TextView skipView, String deviceId, String userId) {
        super(mContext, adListener, deviceId, userId);
        this.adContainer = adContainer;
        this.skipView = skipView;
        adService = new AdService(this, mContext);

    }

    @Override
    public void fetchAd() {
        adListener.onRequestAd();
        adService.requestAd(AdTypeUrl.splashad, AdTypeUrl.splashadKey, deviceId, userId);
    }

    @Override
    public void adShow(JSONObject jsonData) {
        String image = null;
        String page_style = null;
        String banner_height = null;
        try {
            adListener.onRequestedAd();
            type = jsonData.getString("type");
            try {
                String bucket = jsonData.getString("bucket");
                adListener.onAdBucket(AdTypeUrl.splashadKey+"_"+bucket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            adService.reportPull(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
            if ("2".equals(type)) {

                gdtAdListener = generatedGDTListener();
                if (Build.VERSION.SDK_INT >= 23) {
                    checkAndRequestPermission();
                } else {
                    // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
                    fetchSplashAD((Activity) mContext, adContainer, skipView, AdTypeUrl.GDTAPPID, getPosId(), gdtAdListener, 0);
                }
            } else if ("1".equals(type)) {
                image = jsonData.getString("img");
                page_style = jsonData.getString("page_style");
                banner_height = jsonData.getString("banner_height");
                final String openin = jsonData.getString("openin");
                final String target = jsonData.getString("target");
                final String title = jsonData.getString("title");
                imageView = new ImageView(mContext);
                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adService.reportClick(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
                        if (!"webview".equals(openin)) {
                            timer.cancel();
                            //app 外部打开
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(target);
                            intent.setData(content_url);
                            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                            mContext.startActivity(intent);
                        } else {
                            if (adListener != null) {
                                //app 内部打开
                                timer.cancel();
                                adListener.onAdGoAppWeb(target, title);
                            }
                        }
                    }
                });
                showImg(image);

            }else {
                adFailed();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            adFailed();
        }
    }


    @Override
    public void adFailed() {
        adService.reportFail(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
        adListener.onAdFailed(null); //广告展示失败
    }


    /**
     * ----------非常重要----------
     * <p>
     * Android6.0以上的权限适配简单示例：
     * <p>
     * 如果targetSDKVersion >= 23，那么必须要申请到所需要的权限，再调用广点通SDK，否则广点通SDK不会工作。
     * <p>
     * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
     * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，如果您的App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(mContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            fetchSplashAD((Activity) mContext, adContainer, skipView, AdTypeUrl.GDTAPPID, getPosId(), gdtAdListener, 0);
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            ((Activity) mContext).requestPermissions(requestPermissions, 1024);
        }
    }

    private String getPosId() {
        return AdTypeUrl.splashAPPID;
    }

    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity      展示广告的activity
     * @param adContainer   展示广告的大容器
     * @param skipContainer 自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId         应用ID
     * @param posId         广告位ID
     * @param adListener    广告状态监听器
     * @param fetchDelay    拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, skipContainer, appId, posId, adListener, fetchDelay);
        splashAD.fetchAndShowIn(adContainer);
    }

    private SplashADListener generatedGDTListener() {

        return new SplashADListener() {
            @Override
            public void onADDismissed() {
                Log.i("AD_DEMO", "onADDismissed");
                adListener.onAdDismiss();
            }

            @Override
            public void onNoAD(AdError adError) {
                Log.i("AD_DEMO", "onNoAD");
                adListener.onAdFailed("onNoAD");
                adService.reportFail(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
            }

            @Override
            public void onADPresent() {
                Log.i("AD_DEMO", "SplashADPresent");
                adService.reportShow(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
                adListener.onAdShow(type);
            }

            @Override
            public void onADClicked() {
                adService.reportClick(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
                adListener.onAdClick(type);
            }

            @Override
            public void onADTick(long l) {
                Log.i("AD_DEMO", "onADTick" + l);
                adListener.onADTick(l);
            }

            @Override
            public void onADExposure() {
                Log.i("AD_DEMO", "onADExposure");
                adService.reportExposure(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
                adListener.onAdExposure(type);
            }
        };

    }

    /**
     * 请求图片
     *
     * @param url
     */
    public void showImg(String url) {
        final Request request = new Request.Builder()
          .url(url)
          .get()//默认就是GET请求，可以不写
          .build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtil", "onFailure: ");
                adListener.onAdFailed(null);
                adService.reportFail(AdTypeUrl.splashadKey, deviceId, userId, "kaiping", "xiaoyi");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                byte[] Picture_bt = response.body().bytes();


                Message message = handler.obtainMessage();
                message.obj = Picture_bt;
                message.what = 1;
                handler.sendMessage(message);

            }
        });
    }
}
