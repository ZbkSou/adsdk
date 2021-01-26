package com.zbk.adsdk;

/**
 * Created by ZBK on 2021-01-24.
 *
 * @function
 */
public interface AdListenter {
    //请求广告接口
    public void onRequestAd();
    //请求广告接口完成
    public void onRequestedAd();
    //广告就绪，主要是激励视频
    public void  onADLoad();
    //展示广告
    public void onAdShow(String type);
    //回调bucket
    public void onAdBucket(String bucket);
    //曝光广告
    public void onAdExposure(String type);
    //点击广告
    public void onAdClick(String type);
    //点击跳过
    public void onAdClickSkip(String tag);

    public void onAdGoAppWeb(String target, String title);
    //广告展示失败
    public void onAdFailed(String err);

    public void onADTick(long l);

    public void onAdDismiss();

    public void onAdReward();
}
