package com.zbk.adsdk.listen;

import com.zbk.adsdk.AdTypeUrl;

/**
 * Created by ZBK on 2021-01-27.
 *
 * @function
 */
public interface RewardAdListenter {



    public void onAdLoad();

    public void onAdShow();


    public void onAdExpose();


    public void onReward();


    public void onAdClick();


    public void onVideoComplete();

    public void onAdClose() ;

    public void onAdReady();
    public void onAdFailed(String  adError) ;

}
