package com.zbk.adsdk.adspot.service.oneway;

import android.app.Activity;

import com.zbk.adsdk.adspot.service.InterstitialAdService;
import com.zbk.adsdk.listen.InterstitialAdListenter;

import mobi.oneway.export.Ad.OWInterstitialImageAd;
import mobi.oneway.export.AdListener.OWInterstitialImageAdListener;
import mobi.oneway.export.enums.OnewayAdCloseType;
import mobi.oneway.export.enums.OnewaySdkError;

/**
 * Created by ZBK on 2021-01-26.
 *
 * @function
 */
public class OwInterstitialImageAdImpl implements InterstitialAdService {

    //插屏视频广告对象
    private OWInterstitialImageAd owInterstitialImageAd;
    private InterstitialAdListenter adListener;

    @Override
    public void initAD(Activity activity, String placementId, InterstitialAdListenter adListener) {
        this.adListener =adListener;
        //创建插屏视频事件监听器
        OWInterstitialImageAdListener owInterstitialAdListener = new OWInterstitialImageAdListener() {
            @Override
            public void onAdReady() {
                // 广告已经准备就绪，可以调用 show() 方法播放广告
                adListener.onAdReady();
            }

            @Override
            public void onAdShow(String tag) {
                // 广告已经开始播放
                adListener.onAdExpose();
                adListener.onAdShow();
            }

            @Override
            public void onAdClick(String tag) {
                // 广告点击事件
                adListener.onAdClick();
            }

            @Override
            public void onAdClose(String tag, OnewayAdCloseType type) {

                // 广告页面被关闭， type 枚举说明了关闭的类型:

                //  ERROR(视频播放失败), SKIPPED(只对插屏视频，用户点击了跳过按钮), COMPLETED(视频正常播放完成)

                switch (type) {
                    case ERROR:
                        adListener.onAdFailed("视频播放失败");
                        break;
                    case SKIPPED:
                        adListener.onAdClickSkip();
                        break;
                    case COMPLETED:
                        adListener.onComplete();
                        break;
                }
                adListener.onAdClose();
            }

            @Override
            public void onAdFinish(String s, OnewayAdCloseType onewayAdCloseType, String s1) {

            }


            @Override
            public void onSdkError(OnewaySdkError error, String message) {
                // SDK 初始化或者广告播放过程中出现错误，可以在此保存错误日志，以便排查错误原因
                adListener.onAdFailed(message);
            }
        };

        //创建插屏视频对象
        owInterstitialImageAd = new OWInterstitialImageAd (activity, placementId, owInterstitialAdListener);
    }

    @Override
    public void loadAD() {
        //请求广告
        if (owInterstitialImageAd != null) {
            adListener.onAdLoad();
            owInterstitialImageAd.loadAd();
        }
    }

    @Override
    public boolean isReady() {
        //展示广告
        if (owInterstitialImageAd != null) {
            //判断广告是否准备好了

            return owInterstitialImageAd.isReady();
        }
        return false;
    }

    @Override
    public void show(Activity activity, String tag) {
        owInterstitialImageAd.show(activity, tag);
    }

    @Override
    public void destory() {
        //销毁激励视频 调用destory后 不能再调用其他方法,需要重新创建广告对象
        if (owInterstitialImageAd != null) {
            owInterstitialImageAd.destory();
        }
    }
}