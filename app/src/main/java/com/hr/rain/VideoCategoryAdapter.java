package com.hr.rain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.rain.Global.APIManager;
import com.hr.rain.Global.APIManagerCallback;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RabbitJang on 9/2/2018.
 */

public class VideoCategoryAdapter extends BaseAdapter {
    Activity activity;
    public static ArrayList<HashMap<String, Object>> videoArr;
    @Override
    public int getCount() {
        int videoCnt = videoArr.size();
        if(videoCnt == 0) {
            return 0;
        }

        int remainCnt = videoCnt % 5;
        int pairCnt = videoCnt / 5;
        if(remainCnt == 0) {
            return pairCnt * 2;
        }
        else if(remainCnt > 3) {
            return pairCnt * 2 + 2;
        }
        else {
            return pairCnt * 2 + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View vi;
        if(position % 2 == 0) {
            vi = inflater.inflate(R.layout.item_video3, parent, false);
            configureVideo3Item(vi, position);
        }
        else {
            vi = inflater.inflate(R.layout.item_video2, parent, false);
            configureVideo2Item(vi, position);
        }
        return vi;
    }

    private void configureVideo3Item(View view, int position) {
        TextView firstNameTv = view.findViewById(R.id.textView1);
        ImageView firstBackIv = view.findViewById(R.id.imageView1);
        TextView secondNameTv = view.findViewById(R.id.textView2);
        ImageView secondBackIv = view.findViewById(R.id.imageView2);
        TextView thirdNameTv = view.findViewById(R.id.textView3);
        ImageView thirdBackIv = view.findViewById(R.id.imageView3);

        final int firstIndex = position / 2 * 5;
        RelativeLayout videoRl1 = view.findViewById(R.id.videoRl1);
        RelativeLayout videoRl2 = view.findViewById(R.id.videoRl2);
        RelativeLayout videoRl3 = view.findViewById(R.id.videoRl3);
        videoRl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstIndex < videoArr.size()) {
                    HashMap<String, Object> videoObj = videoArr.get(firstIndex);
                    Intent intent = new Intent(activity, VideoPlayerActivity.class);
                    VideoPlayerActivity.videoObj = videoObj;
                    activity.startActivity(intent);
                }
            }
        });

        videoRl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstIndex + 1 < videoArr.size()) {
                    HashMap<String, Object> videoObj = videoArr.get(firstIndex + 1);
                    Intent intent = new Intent(activity, VideoPlayerActivity.class);
                    VideoPlayerActivity.videoObj = videoObj;
                    activity.startActivity(intent);
                }
            }
        });

        videoRl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstIndex + 2 < videoArr.size()) {
                    HashMap<String, Object> videoObj = videoArr.get(firstIndex + 2);
                    Intent intent = new Intent(activity, VideoPlayerActivity.class);
                    VideoPlayerActivity.videoObj = videoObj;
                    activity.startActivity(intent);
                }
            }
        });

        if(firstIndex < videoArr.size()) {
            firstNameTv.setVisibility(View.VISIBLE);
            firstBackIv.setVisibility(View.VISIBLE);
            HashMap<String, Object> firstVideoObj = videoArr.get(firstIndex);
            firstNameTv.setText((String)firstVideoObj.get("Name"));
            String imgUrl = (String) firstVideoObj.get("ImgUrl");
            ImageLoader.getInstance().displayImage(imgUrl, firstBackIv, Util.optionsImg);
        }
        else {
            firstNameTv.setVisibility(View.INVISIBLE);
            firstBackIv.setVisibility(View.INVISIBLE);
        }

        if(firstIndex + 1 < videoArr.size()) {
            secondNameTv.setVisibility(View.VISIBLE);
            secondBackIv.setVisibility(View.VISIBLE);
            HashMap<String, Object> secondVideoObj = videoArr.get(firstIndex + 1);
            secondNameTv.setText((String)secondVideoObj.get("Name"));
            String imgUrl = (String) secondVideoObj.get("ImgUrl");
            ImageLoader.getInstance().displayImage(imgUrl, secondBackIv, Util.optionsImg);
        }
        else {
            secondNameTv.setVisibility(View.INVISIBLE);
            secondBackIv.setVisibility(View.INVISIBLE);
        }

        if(firstIndex + 2 < videoArr.size()) {
            thirdNameTv.setVisibility(View.VISIBLE);
            thirdBackIv.setVisibility(View.VISIBLE);
            HashMap<String, Object> thirdVideoObj = videoArr.get(firstIndex + 2);
            thirdNameTv.setText((String) thirdVideoObj.get("Name"));
            String imgUrl = (String) thirdVideoObj.get("ImgUrl");
            ImageLoader.getInstance().displayImage(imgUrl, thirdBackIv, Util.optionsImg);
        }
        else {
            thirdNameTv.setVisibility(View.INVISIBLE);
            thirdBackIv.setVisibility(View.INVISIBLE);
        }
    }

    private void configureVideo2Item(View view, int position) {
        TextView firstNameTv = view.findViewById(R.id.textView1);
        ImageView firstBackIv = view.findViewById(R.id.imageView1);
        TextView secondNameTv = view.findViewById(R.id.textView2);
        ImageView secondBackIv = view.findViewById(R.id.imageView2);

        final int firstIndex = position / 2 * 5 + 3;
        RelativeLayout videoRl1 = view.findViewById(R.id.videoRl1);
        RelativeLayout videoRl2 = view.findViewById(R.id.videoRl2);
        videoRl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstIndex < videoArr.size()) {
                    HashMap<String, Object> videoObj = videoArr.get(firstIndex);
                    Intent intent = new Intent(activity, VideoPlayerActivity.class);
                    VideoPlayerActivity.videoObj = videoObj;
                    activity.startActivity(intent);
                }
            }
        });

        videoRl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstIndex + 1 < videoArr.size()) {
                    HashMap<String, Object> videoObj = videoArr.get(firstIndex + 1);
                    Intent intent = new Intent(activity, VideoPlayerActivity.class);
                    VideoPlayerActivity.videoObj = videoObj;
                    activity.startActivity(intent);
                }
            }
        });

        if(firstIndex < videoArr.size()) {
            firstNameTv.setVisibility(View.VISIBLE);
            firstBackIv.setVisibility(View.VISIBLE);
            HashMap<String, Object> firstVideoObj = videoArr.get(firstIndex);
            firstNameTv.setText((String)firstVideoObj.get("Name"));
            String imgUrl = (String) firstVideoObj.get("ImgUrl");
            ImageLoader.getInstance().displayImage(imgUrl, firstBackIv, Util.optionsImg);
        }
        else {
            firstNameTv.setVisibility(View.INVISIBLE);
            firstBackIv.setVisibility(View.INVISIBLE);
        }

        if(firstIndex + 1 < videoArr.size()) {
            secondNameTv.setVisibility(View.VISIBLE);
            secondBackIv.setVisibility(View.VISIBLE);
            HashMap<String, Object> secondVideoObj = videoArr.get(firstIndex + 1);
            secondNameTv.setText((String)secondVideoObj.get("Name"));
            String imgUrl = (String) secondVideoObj.get("ImgUrl");
            ImageLoader.getInstance().displayImage(imgUrl, secondBackIv, Util.optionsImg);
        }
        else {
            secondNameTv.setVisibility(View.INVISIBLE);
            secondBackIv.setVisibility(View.INVISIBLE);
        }
    }


    public VideoCategoryAdapter(final Activity activity) {
        this.activity = activity;
        videoArr = new ArrayList<>();
        getVideo();
    }

    private void getVideo() {
        videoArr = new ArrayList<>();
        APIManager.getInstance().setCallback(new APIManagerCallback() {
            @Override
            public void APICallback(JSONObject objAPIResult) {
                Util.hideProgressDialog();
                if(objAPIResult == null) {
                    Util.showToast("Check network and try again", activity);
                    return;
                }
                try {
                    if(objAPIResult.getBoolean("Success")) {
                        JSONArray reservationJSONArr = objAPIResult.getJSONArray("Data");
                        videoArr = Util.toList(reservationJSONArr);
                        notifyDataSetChanged();
                    }
                    else {
                        String message = objAPIResult.getString("Message");
                        Util.showToast(message, activity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Util.showToast("Sign in failed and try again.", activity);
                }
            }
        });

        Util.showProgressDialog("Loading..", activity);
        APIManager.getInstance().getVideo();
    }
}
