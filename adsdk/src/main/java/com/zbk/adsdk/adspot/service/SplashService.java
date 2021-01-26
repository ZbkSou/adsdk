package com.zbk.adsdk.adspot.service;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.zbk.adsdk.AdListenter;

/**
 * Created by ZBK on 2021-01-25.
 *
 * @function
 */
public interface SplashService {

    public void showSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                             String appId, String posId, int fetchDelay, AdListenter adListener);
}
