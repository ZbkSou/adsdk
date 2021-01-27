package com.zbk.adsdk;

import android.content.Context;

import com.zbk.adsdk.adspot.service.oneway.OnewayConfig;

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

    private String AppID;
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

    public  void init(Context context,String AppID){
        this.AppID = AppID;
        OnewayConfig.configure( context, AdTypeUrl.ONEWAY_PublishID);
        OnewayConfig.setDebugMode(true);
    }


    public String getAppID() {
        return AppID;
    }
}
