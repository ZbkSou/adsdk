package com.zbk.adsdk.adspot;

import android.content.Context;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.AdService;
import com.zbk.adsdk.AdTypeUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZBK on 2021-01-24.
 * <p>
 * 激励视频
 *
 * @function
 */
public class InspireadAdspot extends BaseAdspot {
    private RewardVideoAD rewardVideoAD;
    private boolean adLoaded = false;
    private RewardVideoADListener gdtAdListener;
    private String type;

    public InspireadAdspot(Context mContext, AdListenter adListener, String deviceId, String userId) {
        super(mContext, adListener, deviceId, userId);

        adService = new AdService(this, mContext);

    }

    @Override
    public void fetchAd() {
        adService.requestAd(AdTypeUrl.inspireadad, AdTypeUrl.inspireadKey, deviceId, userId);
    }

    @Override
    public void adShow(JSONObject jsonData) {

        try {
            type = jsonData.getString("type");
            adService.reportPull(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
            if ("2".equals(type)) {
                //只处理2的情况，后续其他情况需要再else if 添加
                gdtAdListener = generatedGDTListener();
                rewardVideoAD = new RewardVideoAD(mContext, AdTypeUrl.GDTAPPID, AdTypeUrl.inspireadAPPID, gdtAdListener); // 有声播放
                rewardVideoAD.loadAD();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private RewardVideoADListener generatedGDTListener() {

        return new RewardVideoADListener() {


            @Override
            public void onADLoad() {
                adLoaded = true;
                gdtAdshow();
            }

            @Override
            public void onVideoCached() {

            }

            @Override
            public void onADShow() {
                Log.i("AD_DEMO", "SplashADPresent");
                adService.reportShow(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
                adListener.onAdShow(type);
            }

            @Override
            public void onADExpose() {
                Log.i("AD_DEMO", "onADExposure");
                adService.reportExposure(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");

            }

            @Override
            public void onReward() {
                Log.i("AD_DEMO", "onADonReward");
                adListener.onAdReward();
            }

            @Override
            public void onADClick() {
                Log.i("AD_DEMO", "onADClick");
                adService.reportClick(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");

                adListener.onAdClick(type);
            }

            @Override
            public void onVideoComplete() {
                Log.i("AD_DEMO", "onVideoComplete");
            }

            @Override
            public void onADClose() {
                Log.i("AD_DEMO", "onADClose");
                adService.reportSkip(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
            }

            @Override
            public void onError(AdError adError) {
                Log.i("AD_DEMO", "onError" + adError.getErrorMsg());
                adListener.onAdFailed(adError.getErrorMsg());
                adService.reportFail(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");

            }
        };

    }

    @Override
    public void adFailed() {
        adService.reportFail(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
    }

    public void gdtAdshow() {
        // 3.展示广告
        if (adLoaded) {//广告展示检查1：广告成功加载，此处也可以使用videoCached来实现视频预加载完成后再展示激励视频广告的逻辑
            if (!rewardVideoAD.hasShown()) {//广告展示检查2：当前广告数据还没有展示过
                long delta = 1000;//建议给广告过期时间加个buffer，单位ms，这里demo采用1000ms的buffer
                //广告展示检查3：展示广告前判断广告数据未过期
                if (SystemClock.elapsedRealtime() < (rewardVideoAD.getExpireTimestamp() - delta)) {
                    rewardVideoAD.showAD();
                } else {
                    Toast.makeText(mContext, "激励视频广告已过期，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(mContext, "此条广告已经展示过，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(mContext, "成功加载广告后再进行广告展示！", Toast.LENGTH_LONG).show();
        }
    }

}
