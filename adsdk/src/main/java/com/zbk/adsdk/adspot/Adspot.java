package com.zbk.adsdk.adspot;

import org.json.JSONObject;

/**
 * Created by ZBK on 2021-01-24.
 *
 * 对外广告抽象方法
 * @function
 */
public interface Adspot {

    //获取广告
    public void fetchAd( );
    //显示广告
    public void adShow(JSONObject jsonData);
    public void adFailed();

}
