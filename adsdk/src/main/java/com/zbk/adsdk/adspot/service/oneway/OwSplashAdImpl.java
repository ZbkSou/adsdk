package com.zbk.adsdk.adspot.service.oneway;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.adspot.BaseAdspot;
import com.zbk.adsdk.adspot.service.SplashService;
import com.zbk.adsdk.listen.SplashAdListenter;

import org.json.JSONObject;

import mobi.oneway.export.Ad.OWSplashAd;
import mobi.oneway.export.AdListener.OWSplashAdListener;
import mobi.oneway.export.enums.OnewaySdkError;

import static android.content.ContentValues.TAG;

/**
 * Created by ZBK on 2021-01-25.
 *
 * @function
 */
public class OwSplashAdImpl implements SplashService {

    OWSplashAd owSplashAd;

    public OwSplashAdImpl( String appId, String posId) {
        this.owSplashAd = new OWSplashAd(posId);
    }

    @Override
    public void showSplashAD(Activity activity, ViewGroup adContainer, View skipContainer, int fetchDelay, SplashAdListenter adListener) {

        skipContainer.setVisibility(View.GONE);
        owSplashAd.show(activity,adContainer, new OWSplashAdListener() {
            @Override
            public void onAdShow() {
                // 广告已经加载完成并且展示到界面上
                adListener.onAdShow("ow");
            }

            @Override
            public void onAdError(OnewaySdkError var1,String err) {
                // 广告加载或者展示失败
                Log.e(TAG, err);
                adListener.onAdFailed(err);

            }

            @Override
            public void onAdFinish() {
                // 开屏广告成功展示完毕，有可能是用户按了跳过按钮，也可能是展示完毕自动关闭
                adListener.onAdExposure("ow");
                adListener.onAdDismiss();
            }

            @Override
            public void onAdClick() {
                // 用户点击了广告，此时 SDK 会根据广告类型执行相应的动作(下载 APK 或者打开链接)。
                adListener.onAdClick("ow");
            }
        },fetchDelay);
    }
}
