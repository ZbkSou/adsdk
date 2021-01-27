package com.zbk.adsdk.listen;

import com.zbk.adsdk.bean.FeedAdBean;

import java.util.List;

/**
 * Created by ZBK on 2021-01-28.
 *
 * @function
 */
public interface FeedNativeAdListenter {

    //广告加载
    public void onAdLoad(List<FeedAdBean> feedAds);

    //广告失败
    public void onAdFailed(String err);



}
