package com.zbk.adsdk.listen;

/**
 * Created by ZBK on 2021-01-26.
 *
 * @function
 */
public interface SplashAdListenter {


    //展示广告
    public void onAdShow(String type);

    //曝光广告
    public void onAdExposure(String type);
    //点击广告
    public void onAdClick(String type);
    //点击跳过
    public void onAdClickSkip(String tag);

    //广告展示失败
    public void onAdFailed(String err);

    //广告倒计时
    public void onADTick(long l);

    //广告消失
    public void onAdDismiss();



}
