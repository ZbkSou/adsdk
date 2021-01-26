package com.zbk.adsdk;



/**
 * Created by ZBK on 2021-01-24.
 *
 * 广告全局变量
 * @function
 */
public interface AdTypeUrl {
    static final String AdDomain = "https://ad.caiyunapp.com";
    static String domain = BuildConfig.DEBUG ? AdDomain : AdDomain;
    String GDTAPPID = "1105852039";
    //开屏广告
    String splashAPPID = "3020794695029188";
    String  splashad = domain+"/v1/xiaoyi/splashad";
    String splashadKey= "Y003";
    //激励广告
    String inspireadAPPID = "4090598667304567";
    String  inspireadad = domain+"/v1/xiaoyi/inspiread";
    String inspireadKey= "Y005";
    //回调
    String  adTrace = domain+"/v1/trace";
}
