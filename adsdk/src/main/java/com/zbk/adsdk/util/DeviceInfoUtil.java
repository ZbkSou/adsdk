package com.zbk.adsdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by ZBK on 2021-01-24.
 *
 * @function
 */
public class DeviceInfoUtil {
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("ads VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static String getDisplay(Context context) {
        WindowManager windowManager = (WindowManager) ((Activity) context).getApplication().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        int mRealSizeWidth;//手机屏幕真实宽度
        int mRealSizeHeight;//手机屏幕真实高度
        mRealSizeHeight = outPoint.y;
        mRealSizeWidth = outPoint.x;
        return mRealSizeWidth + "," + mRealSizeHeight;

    }

    /**
     * 手机厂商
     * @return
     */
    public static String getPhoneProducer() {
        return android.os.Build.BRAND;
    }

    /**
     *   获取手机型号
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 获取应用程序名称
     */
    public static  String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
              context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


