package com.zbk.adsdk;

import android.content.Context;

import mobi.oneway.export.Ad.OnewaySdk;

/**
 * Created by ZBK on 2021-01-25.
 *
 *
 * @function
 */
public class AdSdk {
    private volatile static AdSdk instance; //声明成 volatile
    private AdSdk (){}

    public static AdSdk getSingleton() {
        if (instance == null) {
            synchronized (AdSdk.class) {
                if (instance == null) {
                    instance = new AdSdk();
                }
            }
        }
        return instance;
    }

    public  void init(Context context, String publishId){
        OnewaySdk.configure( context,  publishId);
        OnewaySdk.setDebugMode(boolean debugMode);
    }


}
