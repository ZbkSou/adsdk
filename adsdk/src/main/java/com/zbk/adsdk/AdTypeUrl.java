package com.zbk.adsdk;


/**
 * Created by ZBK on 2021-01-24.
 * <p>
 * 广告全局变量
 *
 * @function
 */
public interface AdTypeUrl {
    static final String AdDomain = "https://ad.caiyunapp.com";
    static String domain = BuildConfig.DEBUG ? AdDomain : AdDomain;


    //开屏广告
    String splashAPPID = "3020794695029188";
    String splashad = domain + "/v1/xiaoyi/splashad";
    String splashadKey = "Y003";
    //激励广告
    String inspireadAPPID = "4090598667304567";
    String inspireadad = domain + "/v1/xiaoyi/inspiread";
    String inspireadKey = "Y005";
    //回调
    String adTrace = domain + "/v1/trace";


    //三方参数
    //oneway
    String ONEWAY_PublishID = "6dd35a38ac884eba";
    String ONEWAY_Token = "6137e08d890e4697";
    String OW_Feed = "V2B23PMR188TGCO2";
    String OW_Inspiread = "8HHGFX1O27N0CW95";
    String OW_Splash = "H477CR2DQJF91FM6";
    String OW_Reward = "2JIPRPNB8VS0W0Y2";
}
