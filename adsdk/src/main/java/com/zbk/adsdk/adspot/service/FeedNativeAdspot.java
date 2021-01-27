package com.zbk.adsdk.adspot.service;

import android.app.Activity;
import android.content.Context;

import com.zbk.adsdk.AdSdk;
import com.zbk.adsdk.AdService;
import com.zbk.adsdk.AdTypeUrl;
import com.zbk.adsdk.adspot.BaseAdspot;
import com.zbk.adsdk.adspot.service.oneway.OwFeedNativeAdImpl;
import com.zbk.adsdk.adspot.service.oneway.OwInterstitialImageAdImpl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZBK on 2021-01-28.
 *
 * @function
 */
public class FeedNativeAdspot  extends BaseAdspot {

    private FeedNativeAdService feedNativeAD;

    private String type;
    private int count;
    public FeedNativeAdspot(Context mContext, String placementId,int count) {
        super(mContext, placementId);
        adService = new AdService(this, mContext);
        this.count = count;
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

            feedNativeAD.initAD((Activity) mContext,AdTypeUrl.OW_Inspiread,count);

        } catch (JSONException e) {
            e.printStackTrace();
            adListener.onAdFailed("广告请求失败");

        }

    }

    @Override
    public void adFailed() {

    }
}
