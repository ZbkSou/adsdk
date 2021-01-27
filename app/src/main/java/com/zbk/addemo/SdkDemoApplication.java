package com.zbk.addemo;

import android.app.Application;

import com.zbk.adsdk.AdSdk;

public class SdkDemoApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        AdSdk.getSingleton().init(this,"");


    }



}
