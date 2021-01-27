package com.zbk.adsdk.listen;

/**
 * Created by ZBK on 2021-01-27.
 *
 * @function
 */
public interface InterstitialAdListenter {




    public void onAdLoad();

    public void onAdShow();


    public void onAdExpose();



    public void  onAdClickSkip();


    public void onAdClick();


    public void onComplete();

    public void onAdClose() ;

    public void onAdReady();
    public void onAdFailed(String  adError) ;
}
