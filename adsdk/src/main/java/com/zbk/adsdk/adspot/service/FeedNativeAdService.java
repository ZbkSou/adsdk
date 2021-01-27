package com.zbk.adsdk.adspot.service;

import android.app.Activity;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.listen.FeedNativeAdListenter;

/**
 * Created by ZBK on 2021-01-26.
 *
 * @function
 */
public interface FeedNativeAdService {
    void initAD (Activity activity, String placementId,int count);

    void load(FeedNativeAdListenter adListener);
}
