package com.zbk.addemo.adactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.zbk.addemo.R;
import com.zbk.adsdk.adspot.InterstitialAdspot;
import com.zbk.adsdk.listen.InterstitialAdListenter;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ZBK on 2021-01-27.
 *
 * @function
 */
public class InterstitialAdActivity  extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "InterstitialADActivity";

    private InterstitialAdspot interstitialAd1;


    private String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad);

        ((EditText) findViewById(R.id.alternativeInterstitailADPlaceID)).setText("");

        findViewById(R.id.loadInterstitailAD).setOnClickListener(this);
        findViewById(R.id.showInterstitailAD1).setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loadInterstitailAD:
                findViewById(R.id.showInterstitailAD1).setEnabled(false);

                if (interstitialAd1 != null) {
                    interstitialAd1.destroy();
                }


                placeId = ((EditText) findViewById(R.id.alternativeInterstitailADPlaceID)).getText().toString().trim();

                interstitialAd1 = new InterstitialAdspot(this, placeId, new InterstitialAdListenter() {
                    @Override
                    public void onAdLoad() {
//                        广告加载完成可调用show
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdExpose() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdClickSkip() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdClick() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdClose() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdReady() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdFailed(String adError) {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }
                });
                interstitialAd1.fetchAd();
                break;
            case R.id.showInterstitailAD1:
                if(interstitialAd1.isReady()){
                    interstitialAd1.showAD();
                }
                break;

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (interstitialAd1 != null) {
            interstitialAd1.destroy();
        }

    }
}
