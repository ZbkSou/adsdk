package com.zbk.adsdk.util;

import org.json.JSONObject;

/**
 * Created by ZBK on 2021-01-24.
 *
 * @function
 */
public interface NetworkListening {
    public void onResponse(JSONObject response);
    public void onErrorListener(String errMsg);
}
