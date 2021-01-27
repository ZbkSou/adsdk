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

    //广告位
    public String placementId;

    public BaseAdspot(Context mContext, String placementId) {
        this.mContext = mContext;

        this.placementId = placementId;
    }




}