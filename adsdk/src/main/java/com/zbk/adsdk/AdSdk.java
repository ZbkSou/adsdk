package com.zbk.adsdk;

import android.content.Context;

import mobi.oneway.export.Ad.OnewaySdk;



/**
 * Created by ZBK on 2021-01-25.
 *
 * sdk
 *  oei
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

    public  void init(Context context){
        OnewaySdk.configure( context, AdTypeUrl.ONEWAY_PublishID);
        OnewaySdk.setDebugMode(true);
    }


}
