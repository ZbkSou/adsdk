package com.zbk.addemo.adactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zbk.addemo.R;
import com.zbk.adsdk.adspot.SplashAdspot;
import com.zbk.adsdk.listen.SplashAdListenter;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ZBK on 2021-01-27.
 *
 * @function
 */
public class SplashActivity extends AppCompatActivity implements  SplashAdListenter {

    private static final String TAG = "SplashActivity";

    private SplashAdspot splashAd;
    private boolean canJump = true;

    private TextView btnSkip;
    private boolean autoShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_splash);
        final ViewGroup adContainer = findViewById(R.id.splash_container);

        String pid = "H477CR2DQJF91FM6";

        btnSkip = findViewById(R.id.btn_skip);

        splashAd = new SplashAdspot(this, adContainer, btnSkip, pid, this);

        //请求广告
        if (splashAd != null) {
            splashAd.fetchAd();
        }


    }


    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: 暂停");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: 开屏界面停止运行");
        super.onStop();
        canJump = true;
    }

    private void next() {
        // 此处打开需要的 activity 即可
        this.finish();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        if (this.splashAd != null && canJump) {
//            next();
        }
        canJump = true;
    }

    /// 广告 事件
    @Override
    public void onAdShow(String type) {
        Log.d(TAG, "DEMO onAdShow ");
    }

    @Override
    public void onAdExposure(String type) {
        Log.d(TAG, "DEMO onAdExposure ");
    }

    @Override
    public void onAdClick(String type) {
        if (canJump) {
            next();
        }
        canJump = true;
    }

    @Override
    public void onAdClickSkip(String tag) {
        Log.d(TAG, "DEMO onAdClickSkip ");
    }

    @Override
    public void onAdFailed(String err) {
        Log.d(TAG, "DEMO onAdFailed ");
        Toast.makeText(SplashActivity.this, "没有加载到广告", Toast.LENGTH_SHORT).show();
        next();
    }

    @Override
    public void onAdTick(long l) {
        btnSkip.setText(l + "");
    }

    @Override
    public void onAdDismiss() {
        if (canJump) {
            next();
        }
        canJump = true;
    }

}