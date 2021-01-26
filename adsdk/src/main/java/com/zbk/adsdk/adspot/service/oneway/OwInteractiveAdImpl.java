package com.zbk.adsdk.adspot.service.oneway;

import android.app.Activity;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.adspot.service.InterstitialAdService;

import mobi.oneway.export.Ad.OWInteractiveAd;
import mobi.oneway.export.AdListener.OWInteractiveAdListener;
import mobi.oneway.export.enums.OnewayAdCloseType;
import mobi.oneway.export.enums.OnewaySdkError;

/**
 * Created by ZBK on 2021-01-26.
 *
 * @function
 */
public class OwInteractiveAdImpl implements InterstitialAdService {

    //激励互动广告对象
    private OWInteractiveAd owInteractiveAd;

    @Override
    public void initAD(Activity activity, String placementId, AdListenter adListener) {
        //创建插屏图片事件监听器
        OWInteractiveAdListener owInteractiveAdListener = new OWInteractiveAdListener() {
            @Override
            public void onAdReady() {
                // 广告已经准备就绪，可以调用 show() 方法播放广告
                adListener.onADLoad();
            }

            @Override
            public void onAdShow(String tag) {
                // 广告已经开始播放
                adListener.onAdShow(tag);
            }

            @Override
            public void onReward(String s) {
                //激励事件触发，可以在此给用户奖励
                // 广告已经开始播放
                adListener.onAdReward();
            }

            @Override
            public void onAdClose(String tag, OnewayAdCloseType type) {

                switch (type){
                    case ERROR:
                        adListener.onAdFailed("视频播放失败");
                        break;
                    case SKIPPED:
                        adListener.onAdClickSkip(tag);
                        break;

                }
            }


            @Override
            public void onSdkError(OnewaySdkError onewaySdkError, String s) {
                // SDK 初始化或者广告播放过程中出现错误，可以在此保存错误日志，以便排查错误原因
                adListener.onAdFailed(s);

           }
        };

        //创建激励互动对象
        owInteractiveAd = new OWInteractiveAd (activity, placementId, owInteractiveAdListener);

    }

    @Override
    public void loadAD() {
        //请求广告
        if (owInteractiveAd != null) {
            owInteractiveAd.loadAd();
        }
    }

    @Override
    public boolean isReady() {
        //展示广告
        if (owInteractiveAd != null) {
            //判断广告是否准备好了
            return  owInteractiveAd.isReady();
        }
        return false;
    }

    @Override
    public void show(Activity activity, String tag) {
        owInteractiveAd.show(activity, tag);
    }

    @Override
    public void destory() {
//销毁激励视频 调用destory后 不能再调用其他方法,需要重新创建广告对象
        if (owInteractiveAd != null) {
            owInteractiveAd.destory();
        }
    }
}
