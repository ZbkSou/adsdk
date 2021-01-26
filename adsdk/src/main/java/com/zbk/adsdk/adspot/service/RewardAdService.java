package com.zbk.adsdk.adspot.service;

import android.app.Activity;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.listen.RewardAdListenter;

/**
 * Created by ZBK on 2021-01-25.
 *
 * @function
 */
public interface RewardAdService {

    void initAD(Activity activity, String placementId, RewardAdListenter adListener);

    void loadAD();

    boolean isReady();

    /**
     * activity: 当前 Activity
     * tag : 自定义标记
     *
     * @param activity
     * @param tag
     */
    void show(Activity activity, String tag);

    /**
     * 销毁
     */
    void destory();
}
