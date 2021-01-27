package com.zbk.addemo.adactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.zbk.addemo.R;
import com.zbk.adsdk.adspot.RewardAdspot;
import com.zbk.adsdk.listen.RewardAdListenter;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ZBK on 2021-01-27.
 *
 * @function
 */
public class RewardVideoActivity extends AppCompatActivity implements View.OnClickListener {




    private static final String TAG = "RewardVideoActivity";

    private RewardAdspot ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);

        findViewById(R.id.load_video).setOnClickListener(this);

        String posId = "";

//        ((EditText) findViewById(R.id.alternativeRewardVideoAdPlaceID)).setText(posId);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.load_video:
                this.ad = null;
                this.ad = new RewardAdspot(this,
                  "2JIPRPNB8VS0W0Y2", new RewardAdListenter() {
                    @Override
                    public void onAdLoad() {
                        //加载完成
                        Log.d(TAG, "DEMO onAdLoad " );
                        //也可以通过此方法获取广告状态
                        ad.isReady();

                        // 显示广告
                        ad.showAD();
                    }

                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "DEMO onAdShow " );
                    }

                    @Override
                    public void onAdExpose() {
                        Log.d(TAG, "DEMO onADExpose " );
                    }

                    @Override
                    public void onReward() {
                        Log.d(TAG, "DEMO onReward " );
                    }

                    @Override
                    public void onAdClick() {
                        Log.d(TAG, "DEMO onAdClick " );
                    }

                    @Override
                    public void onVideoComplete() {
                        Log.d(TAG, "DEMO onVideoComplete " );
                    }

                    @Override
                    public void onAdClose() {
                        Log.d(TAG, "DEMO onAdClose " );
                    }

                    @Override
                    public void onAdReady() {
                        Log.d(TAG, "DEMO onAdReady " );
                    }

                    @Override
                    public void onAdFailed(String adError) {
                        Log.d(TAG, "DEMO onAdFailed " );
                    }
                });
                this.ad.fetchAd();
                break;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 单独销毁广告对象
        if (ad != null) {
            ad.destory();
        }


    }
}
