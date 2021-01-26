package com.zbk.adsdk.adspot;

import android.content.Context;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.AdService;

/**
 * Created by ZBK on 2021-01-24.
 *
 * @function
 */
public abstract class BaseAdspot implements Adspot {

    public Context mContext;


    //广告请求类
    public AdService adService;
    //设备id
    public String deviceId;
    //用户
    public String userId;

    public BaseAdspot(Context mContext, String deviceId, String userId) {
        this.mContext = mContext;

        this.deviceId = deviceId;
        this.userId = userId;
    }
}