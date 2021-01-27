package com.zbk.addemo.adactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zbk.addemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.slashAD).setOnClickListener(this);
        findViewById(R.id.interstitialAD).setOnClickListener(this);

        findViewById(R.id.nativeRecyclerAD).setOnClickListener(this);
        findViewById(R.id.rewardVideoAd).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.slashAD:
                intent.setClass(this, SplashActivity.class);
                startActivity(intent);
                break;
            case R.id.interstitialAD:
                intent.setClass(this, InterstitialAdActivity.class);
                startActivity(intent);
                break;

            case R.id.nativeRecyclerAD:
//                intent.setClass(this, NativeRecyclerListSelectActivity.class);
//                startActivity(intent);
//                break;
            case R.id.rewardVideoAd:
                intent.setClass(this, RewardVideoActivity.class);
                startActivity(intent);
                break;

        }
    }
}