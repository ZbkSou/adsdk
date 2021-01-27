package com.zbk.adsdk;

import android.content.Context;
import android.util.Log;

import com.zbk.adsdk.adspot.Adspot;
import com.zbk.adsdk.util.DeviceInfoUtil;
import com.zbk.adsdk.util.HttpUtil;
import com.zbk.adsdk.util.NetworkListening;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZBK on 2021-01-24.
 *
 * @function
 */
public class AdService {

    private Adspot adspot;

    private Context context;

    public AdService(Adspot adspot, Context context) {
        this.adspot = adspot;
        this.context = context;
    }

    /**
     * 请求广告
     *
     * @param AdTypeUrl
     * @param appid
     * @param placementId
     */
    public void requestAd(String AdTypeUrl, final String appid,  final String placementId) {

        JSONObject requestBody = new JSONObject();
        try {

            requestBody.put("placementId", placementId);
            requestBody.put("ostype", "android");
            requestBody.put("version", DeviceInfoUtil.getAppVersionName(context));
            requestBody.put("screen", DeviceInfoUtil.getDisplay(context));
            requestBody.put("code", appid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtil.post(AdTypeUrl, requestBody, new NetworkListening() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonData = response.getJSONObject("result");
                    String type = jsonData.getString("type");

                    adspot.adShow(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                    adspot.adFailed();
                }

            }

            @Override
            public void onErrorListener(String errMsg) {
                adspot.adFailed();
            }
        });

    }


    //广告行为 action String (pullad: 拉取广告  adShow: 广告展现  fail: 广告拉取失败或未拉取到广告 exposure: 广告展现被记为曝光 click_direct_to： 广告被点击 skip: 跳过广告)
    public void reportShow(String code, String deviceId, String userId, String page, String group) {
        trace(code, deviceId, userId, "adShow", page, group);
    }

    public void reportClick(String code, String deviceId, String userId, String page, String group) {
        trace(code, deviceId, userId, "click_direct_to", page, group);
    }

    public void reportPull(String code, String deviceId, String userId, String page, String group) {
        trace(code, deviceId, userId, "pullad", page, group);
    }

    public void reportFail(String code, String deviceId, String userId, String page, String group) {
        trace(code, deviceId, userId, "fail", page, group);
    }

    public void reportExposure(String code, String deviceId, String userId, String page, String group) {
        trace(code, deviceId, userId, "exposure", page, group);
    }

    public void reportSkip(String code, String deviceId, String userId, String page, String group) {
        trace(code, deviceId, userId, "skip", page, group);
    }

    private void trace(String code, String deviceId, String userId, String action, String page, String group) {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("appName", DeviceInfoUtil.getAppName(context));
            requestBody.put("group", group);
            requestBody.put("page", page);

            requestBody.put("deviceId", deviceId);
            requestBody.put("userId", userId);
            requestBody.put("ostype", "android");
            requestBody.put("os", "android");

            requestBody.put("brand", DeviceInfoUtil.getPhoneProducer());
            requestBody.put("channel", "android");
            requestBody.put("action", action);
            requestBody.put("version", DeviceInfoUtil.getAppVersionName(context));
            requestBody.put("mobile", DeviceInfoUtil.getPhoneModel());
            requestBody.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtil.post(AdTypeUrl.adTrace, requestBody, new NetworkListening() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    Log.i("AD_DEMO", "trace" + status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorListener(String errMsg) {

            }
        });
    }
}
