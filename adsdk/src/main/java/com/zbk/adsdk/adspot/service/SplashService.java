package com.zbk.adsdk.adspot.service;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.listen.SplashAdListenter;

/**
 * Created by ZBK on 2021-01-25.
 *
 * @function
 */
public interface SplashService {

    public void showSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                             int fetchDelay, SplashAdListenter adListener);
}
