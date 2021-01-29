package com.zbk.adsdk.adspot;

import android.app.Activity;
import android.content.Context;

import com.zbk.adsdk.AdSdk;
import com.zbk.adsdk.AdService;
import com.zbk.adsdk.AdTypeUrl;
import com.zbk.adsdk.adspot.BaseAdspot;
import com.zbk.adsdk.adspot.service.FeedNativeAdService;
import com.zbk.adsdk.adspot.service.oneway.OwFeedNativeAdImpl;
import com.zbk.adsdk.adspot.service.oneway.OwInterstitialImageAdImpl;
import com.zbk.adsdk.bean.FeedAdBean;
import com.zbk.adsdk.listen.FeedNativeAdListenter;
import com.zbk.adsdk.listen.RewardAdListenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ZBK on 2021-01-28.
 *
 * @function
 */
public class FeedNativeAdspot  extends BaseAdspot {

    private FeedNativeAdService feedNativeAD;

    //对外广告监听
    public FeedNativeAdListenter adListener;
    private String type;
    private int count;
    public FeedNativeAdspot(Context mContext, String placementId, int count, FeedNativeAdListenter listenter) {
        super(mContext, placementId);
        adService = new AdService(this, mContext);
        this.count = count;
        this.adListener = listenter;
    }

    @Override
    public void fetchAd() {
        adService.requestAd(AdTypeUrl.splashad, AdSdk.getSingleton().getAppID(),  placementId);
    }

    @Override
    public void adShow(JSONObject jsonData) {
        try {
            type = jsonData.getString("type");
//            adService.reportPull(AdTypeUrl.splashadKey, deviceId, userId, "inspiread", "xiaoyi");
            if ("2".equals(type)) {
                //只处理2的情况，后续其他情况需要再else if 添加

                feedNativeAD = new OwFeedNativeAdImpl();
            }

            feedNativeAD.initAD((Activity) mContext, AdTypeUrl.OW_Inspiread, new FeedNativeAdListenter() {
                @Override
                public void onAdLoad(List<FeedAdBean> feedAds) {
                    adListener.onAdLoad(feedAds);
                }

                @Override
                public void onAdFailed(String err) {
                    adListener.onAdFailed(err);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            adListener.onAdFailed("广告请求失败");

        }

    }
    public void loadAD(int count){

        if(feedNativeAD!=null){
            feedNativeAD.load(count);
        }

    }
    @Override
    public void adFailed() {

        adListener.onAdFailed("广告请求失败");
    }
}
