package com.zbk.adsdk.adspot.service.oneway;

import android.content.Context;

import mobi.oneway.export.Ad.OnewaySdk;

/**
 * Created by ZBK on 2021-01-25.
 *
 * @function
 */
public class OnewayConfig {

    public static void configure(Context context, String publishId){
        OnewaySdk.configure( context,  publishId);
    }

    public static void setDebugMode(boolean debugMode){
        OnewaySdk.setDebugMode( debugMode);
    }



}
