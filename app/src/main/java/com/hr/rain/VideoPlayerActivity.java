package com.hr.rain;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by RabbitJang on 9/2/2018.
 */

public class VideoPlayerActivity extends Activity {
    TextView titleTv;
    ConstraintLayout controlCl;
    ImageView prevIv;
    ImageView nextIv;
    ImageView playIv;
    boolean isPlaying;
    public static HashMap<String, Object> videoObj;
    HashMap<String, Object> curVideoObj;
    int videoIndex;
    JZVideoPlayerStandard videoView;
    ImageView minimizeIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        initValue();
        configureDesign();
        playCurrentVideo();
    }

    private void initValue() {
        videoIndex = VideoCategoryAdapter.videoArr.indexOf(videoObj);
        curVideoObj = videoObj;
    }

    private void configureDesign() {
        ImageView backIv = (ImageView)findViewById(R.id.imageView15);
        minimizeIv = (ImageView)findViewById(R.id.imageView18);
        minimizeIv.setVisibility(View.INVISIBLE);
        controlCl = (ConstraintLayout)findViewById(R.id.constraintLayout2);
        ImageView fullscreenIv = (ImageView)findViewById(R.id.imageView8);
        fullscreenIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlCl.setVisibility(View.GONE);
                minimizeIv.setVisibility(View.VISIBLE);
            }
        });

        minimizeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlCl.setVisibility(View.VISIBLE);
                minimizeIv.setVisibility(View.INVISIBLE);
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTv = (TextView)findViewById(R.id.textView9);
        videoView = (JZVideoPlayerStandard) findViewById(R.id.video_view);
        videoView.fullscreenButton.setVisibility(View.INVISIBLE);
        prevIv = (ImageView)findViewById(R.id.imageView12);
        playIv = (ImageView)findViewById(R.id.imageView13);
        nextIv = (ImageView)findViewById(R.id.imageView14);

        playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying = !isPlaying;
                if(isPlaying) {
                    playIv.setImageResource(R.drawable.pause);
                    videoView.goOnPlayOnResume();
                }
                else {
                    playIv.setImageResource(R.drawable.play);
                    videoView.goOnPlayOnPause();
                }
            }
        });

        prevIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoIndex = (videoIndex + 1) % VideoCategoryAdapter.videoArr.size();
                curVideoObj = VideoCategoryAdapter.videoArr.get(videoIndex);
                playCurrentVideo();
            }
        });

        nextIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoIndex == 0) {
                    videoIndex = VideoCategoryAdapter.videoArr.size() - 1;
                }
                else {
                    videoIndex = (videoIndex - 1) % VideoCategoryAdapter.videoArr.size();
                }
                curVideoObj = VideoCategoryAdapter.videoArr.get(videoIndex);
                playCurrentVideo();
            }
        });
    }

    private void playCurrentVideo() {
        titleTv.setText((String)curVideoObj.get("Name"));
        videoView.setUp((String)curVideoObj.get("VideoUrl"), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        videoView.startVideo();
        playIv.setImageResource(R.drawable.pause);
        isPlaying = true;
    }

    @Override
    public void onBackPressed() {
        if(videoView.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.releaseAllVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
