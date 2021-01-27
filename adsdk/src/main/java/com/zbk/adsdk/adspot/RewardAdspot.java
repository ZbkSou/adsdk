package com.zbk.adsdk.adspot;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.zbk.adsdk.AdSdk;
import com.zbk.adsdk.AdService;
import com.zbk.adsdk.AdTypeUrl;
import com.zbk.adsdk.adspot.service.RewardAdService;
import com.zbk.adsdk.adspot.service.oneway.*;
import com.zbk.adsdk.listen.RewardAdListenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZBK on 2021-01-24.
 * <p>
 * 激励视频
 *
 * @function
 */
public class RewardAdspot extends BaseAdspot {

    private RewardAdService rewardAdService;

    //三方广告
    private RewardAdListenter rewardAdListenter;

    //对外广告监听
    public RewardAdListenter adListener;


    private String type;
    private boolean adLoaded ;

    public RewardAdspot(Context mContext,   String placementId,RewardAdListenter listenter) {

        super(mContext,   placementId);

        this.adListener = listenter;
        adService = new AdService(this, mContext);

    }

    @Override
    public void fetchAd() {
        adService.requestAd(AdTypeUrl.inspireadad, AdSdk.getSingleton().getAppID(),  placementId);
    }

    @Override
    public void adShow(JSONObject jsonData) {

        try {
            type = jsonData.getString("type");
//            adService.reportPull(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
            if ("2".equals(type)) {
                //只处理2的情况，后续其他情况需要再else if 添加
                rewardAdListenter = generatedListener();
                rewardAdService = new OwRewardedAdImpl(); // 有声播放


            }
            adLoaded =false;
            rewardAdService.initAD((Activity) mContext,AdTypeUrl.OW_Reward,rewardAdListenter);
            loadAD();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void loadAD(){

        if(rewardAdService!=null){
            rewardAdService.loadAD();
        }

    }
    public void showAD(){
        if(rewardAdService!=null&&adLoaded){

            rewardAdService.show((Activity) mContext,type);
        }
    }
    public boolean isReady(){
        if(rewardAdService!=null){

         return    rewardAdService.isReady();
        }
        return false;
    }

    public void destory(){
        if(rewardAdService!=null){
            rewardAdService.destory();
        }

    }

    private RewardAdListenter generatedListener() {

        return new RewardAdListenter() {


            @Override
            public void onAdLoad() {
                adLoaded =false;
                adListener.onAdLoad();
            }



            @Override
            public void onAdShow() {
                Log.i("AD_DEMO", "SplashADPresent");
//                adService.reportShow(AdTypeUrl.splashadKey, placementId,  "inspiread", "xiaoyi");
                adListener.onAdShow();
            }

            @Override
            public void onAdExpose() {
                Log.i("AD_DEMO", "onADExposure");
//                adService.reportExposure(AdTypeUrl.splashadKey, placementId,  "inspiread", "xiaoyi");
                adListener.onAdExpose();
            }

            @Override
            public void onReward() {
                Log.i("AD_DEMO", "onADonReward");
                adListener.onReward();
            }

            @Override
            public void onAdClick() {
                Log.i("AD_DEMO", "onADClick");
//                adService.reportClick(AdTypeUrl.splashadKey, placementId,  "inspiread", "xiaoyi");

                adListener.onAdClick();
            }

            @Override
            public void onVideoComplete() {
                Log.i("AD_DEMO", "onVideoComplete");
                adListener.onVideoComplete();
            }

            @Override
            public void onAdClose() {
                Log.i("AD_DEMO", "onADClose");
                adListener.onAdClose();
//                adService.reportSkip(AdTypeUrl.splashadKey, placementId,  "inspiread", "xiaoyi");
            }

            @Override
            public void onAdFailed(String adError) {
                Log.i("AD_DEMO", "onError" + adError);
                adListener.onAdFailed(adError);
//                adService.reportFail(AdTypeUrl.splashadKey, placementId,  "inspiread", "xiaoyi");
            }

            @Override
            public void onAdReady() {
                adLoaded =true;
                adListener.onAdReady();
            }


        };

    }

    @Override
    public void adFailed() {
        adListener.onAdFailed("广告请求失败");
//        adService.reportFail(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
    }



}
