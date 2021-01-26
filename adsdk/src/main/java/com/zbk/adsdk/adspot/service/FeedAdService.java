package com.zbk.adsdk.adspot.service;

import android.app.Activity;

import com.zbk.adsdk.AdListenter;

/**
 * Created by ZBK on 2021-01-26.
 *
 * @function
 */
public interface FeedAdService {
    void initAD (Activity activity, String placementId);

    void load(AdListenter adListener);
}
