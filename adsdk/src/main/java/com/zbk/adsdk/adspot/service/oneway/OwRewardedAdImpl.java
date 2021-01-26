package com.zbk.adsdk.adspot.service.oneway;

import android.app.Activity;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.adspot.service.RewardAdService;
import com.zbk.adsdk.listen.RewardAdListenter;

import mobi.oneway.export.Ad.OWRewardedAd;
import mobi.oneway.export.AdListener.OWRewardedAdListener;
import mobi.oneway.export.enums.OnewayAdCloseType;
import mobi.oneway.export.enums.OnewaySdkError;

/**
 * Created by ZBK on 2021-01-26.
 *
 * @function
 */
public class OwRewardedAdImpl implements RewardAdService {
    private OWRewardedAd owRewardedAd;
    private RewardAdListenter adListener;
    @Override
    public void initAD(Activity activity, String placementId, RewardAdListenter adListener) {
        this.adListener =adListener;
        //创建激励视频事件监听器
        OWRewardedAdListener owRewardedAdListener = new OWRewardedAdListener() {
            @Override
            public void onAdReady() {
                // 广告已经准备就绪，可以调用 show() 方法播放广告
                adListener.onAdReady();
            }

            @Override
            public void onAdShow(String tag) {
                // 广告已经开始播放
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

                switch (type){
                    case ERROR:
                        adListener.onAdFailed("视频播放失败");
                        break;
                    case SKIPPED:
                        adListener.onAdClose();
                        break;

                }

            }

            @Override
            public void onAdFinish(String tag, OnewayAdCloseType type,String sessionId) {
                //广告视频播放完成
                //  ERROR(视频播放失败), SKIPPED(只对插屏视频，用户点击了跳过按钮), COMPLETED(视频正常播放完成)
                switch (type){
                    case ERROR:
                        adListener.onAdFailed("视频播放失败");
                        break;
                    case SKIPPED:
                        adListener.onAdClose();
                        break;
                    case COMPLETED:
                        adListener.onReward();
                        break;
                }
            }


            @Override
            public void onSdkError(OnewaySdkError error, String message) {
                // SDK 初始化或者广告播放过程中出现错误，可以在此保存错误日志，以便排查错误原因
                adListener.onAdFailed(message);
            }
        };

        //创建激励视频对象
        owRewardedAd = new OWRewardedAd(activity,placementId, owRewardedAdListener);
    }

    @Override
    public void loadAD() {
        //请求广告
        if (owRewardedAd != null) {
            this.adListener.onAdLoad();
            owRewardedAd.loadAd();
        }
    }

    @Override
    public boolean isReady() {
        //展示广告
        if (owRewardedAd != null) {
            //判断广告是否准备好了
           return  owRewardedAd.isReady();
        }
        return false;
    }

    @Override
    public void show(Activity activity, String tag) {
        owRewardedAd.show(activity, tag);
    }

    @Override
    public void destory() {
        //销毁激励视频 调用destory后 不能再调用其他方法,需要重新创建广告对象
        if (owRewardedAd != null) {
            owRewardedAd.destory();
        }
    }
}
