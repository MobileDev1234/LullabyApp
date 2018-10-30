package com.hr.rain;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by RabbitJang on 9/2/2018.
 */

public class VideoCategoryActivity extends Activity {
    public static ListView videoCategoryLv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_category);
        configureDesign();
    }

    private void configureDesign() {
        videoCategoryLv = (ListView)findViewById(R.id.category_lv);
        VideoCategoryAdapter adapter = new VideoCategoryAdapter(this);
        videoCategoryLv.setAdapter(adapter);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
