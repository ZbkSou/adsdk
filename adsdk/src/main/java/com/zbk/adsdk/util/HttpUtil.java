package com.zbk.adsdk.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZBK on 2021-01-24.
 *
 * @function
 */

public class HttpUtil {
    private static OkHttpClient instance;

    public static OkHttpClient getInstance() {

        if (instance == null) {

            instance = new OkHttpClient();
        }

        return instance;
    }

    public static void get(String url, final NetworkListening listening) {
        Log.d("HttpUtil", "get: " + url);
        final Request request = new Request.Builder()
          .url(url)
          .get()//默认就是GET请求，可以不写
          .build();
        Call call = getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtil", "onFailure: ");
                listening.onErrorListener(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtil", "onResponse: " + response.body().string());
                try {
                    listening.onResponse(new JSONObject(response.body().string()));
                } catch (JSONException e) {
                    listening.onErrorListener(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public static void post(String url, JSONObject JSON, final NetworkListening listening) {
        Log.d("HttpUtil", "post: " + url);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toString());
        Request request = new Request.Builder()
          .url(url)
          .post(body)
          .build();
        getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtil", "onFailure: " + e.getMessage());
                listening.onErrorListener(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtil", response.protocol() + " " + response.code() + " " + response.message());

                try {
                    listening.onResponse(new JSONObject(response.body().string()));
                } catch (JSONException e) {
                    listening.onErrorListener(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

}
