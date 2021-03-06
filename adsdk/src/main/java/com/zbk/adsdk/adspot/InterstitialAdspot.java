package com.zbk.adsdk.adspot;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.zbk.adsdk.AdSdk;
import com.zbk.adsdk.AdService;
import com.zbk.adsdk.AdTypeUrl;
import com.zbk.adsdk.adspot.service.InterstitialAdService;
import com.zbk.adsdk.listen.InterstitialAdListenter;
import com.zbk.adsdk.adspot.service.oneway.*;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by ZBK on 2021-01-27.
 *
 * @function
 */
public class InterstitialAdspot  extends BaseAdspot {


    private InterstitialAdService interstitialAdService;




    //三方广告
    private InterstitialAdListenter interstitialAdListenter;

    //对外广告监听
    public InterstitialAdListenter adListener;


    private String type;
    private boolean adLoaded ;
    public InterstitialAdspot(Context mContext,String placementId, InterstitialAdListenter adListener) {
        super(mContext, placementId);
        this.adListener = adListener;
        adService = new AdService(this, mContext);

    }

    @Override
    public void fetchAd() {
        adService.requestAd(AdTypeUrl.inspireadad,  AdSdk.getSingleton().getAppID(),  placementId);
    }



    @Override
    public void adShow(JSONObject jsonData) {

        try {
            type = jsonData.getString("type");
//            adService.reportPull(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
            if ("2".equals(type)) {
                //只处理2的情况，后续其他情况需要再else if 添加
                interstitialAdListenter = generatedListener();
                interstitialAdService = new OwInterstitialImageAdImpl();
            }
            adLoaded =false;
            interstitialAdService.initAD((Activity) mContext,AdTypeUrl.OW_Inspiread,interstitialAdListenter);
            loadAD();
        } catch (JSONException e) {
            e.printStackTrace();
            adListener.onAdFailed("广告请求失败");
        }


    }

    @Override
    public void adFailed() {
//        adService.reportFail(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
        adListener.onAdFailed("广告请求失败");
    }

    private void loadAD(){

        if(interstitialAdService!=null){
            interstitialAdService.loadAD();
        }

    }
    public void showAD(){
        if(interstitialAdService!=null&&adLoaded){

            interstitialAdService.show((Activity) mContext,type);
        }
    }
    public boolean isReady(){
        if(interstitialAdService!=null){

           return interstitialAdService.isReady();
        }
        return  false;
    }

    public void destroy(){
        if(interstitialAdService!=null){
            interstitialAdService.destory();
        }

    }

    private InterstitialAdListenter generatedListener() {

        return new InterstitialAdListenter() {


            @Override
            public void onAdLoad() {
                adLoaded =false;
                adListener.onAdLoad();
            }



            @Override
            public void onAdShow() {
                Log.i("AD_DEMO", "SplashADPresent");
//                adService.reportShow(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
                adListener.onAdShow();
            }

            @Override
            public void onAdExpose() {
                Log.i("AD_DEMO", "onADExposure");
//                adService.reportExposure(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
                adListener.onAdExpose();
            }

            @Override
            public void onAdClickSkip() {
                adListener.onAdClickSkip();
            }



            @Override
            public void onAdClick() {
                Log.i("AD_DEMO", "onADClick");
//                adService.reportClick(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");

                adListener.onAdClick();
            }

            @Override
            public void onComplete() {
                adListener.onComplete();
            }


            @Override
            public void onAdClose() {
                Log.i("AD_DEMO", "onADClose");
                adListener.onAdClose();
//                adService.reportSkip(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
            }

            @Override
            public void onAdFailed(String adError) {
                Log.i("AD_DEMO", "onError" + adError);
                adListener.onAdFailed(adError);
//                adService.reportFail(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
            }

            @Override
            public void onAdReady() {
                adLoaded =true;
                adListener.onAdReady();
            }


        };

    }

}
