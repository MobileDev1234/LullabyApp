package com.hr.rain;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by RabbitJang on 9/2/2018.
 */

public class Util {
    public static Toast toast = null;
    public static Dialog progressDlg = null;

    public static DisplayImageOptions optionsImg = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.placeholder)
            .showImageForEmptyUri(R.drawable.placeholder)
            .showImageOnFail(R.drawable.placeholder)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public static void showToast (String toastStr, Context context) {
        if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
            toast = Toast.makeText(context, toastStr, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void showProgressDialog(String titleStr, Context context)  {
        progressDlg = new Dialog(context);
        progressDlg.requestWindowFeature(((Activity)context).getWindow().FEATURE_NO_TITLE);
        progressDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDlg.setContentView(R.layout.dialog_loading);
        progressDlg.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        progressDlg.getWindow().setGravity(Gravity.CENTER);

        TextView titleTv = (TextView)progressDlg.findViewById(R.id.textView39);
        titleTv.setText(titleStr);
        progressDlg.show();
    }

    public static void hideProgressDialog() {
        if(progressDlg != null) {
            progressDlg.dismiss();
        }
    }

    public static HashMap<String, Object> toMap(JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static ArrayList<HashMap<String, Object>> toList(JSONArray array) throws JSONException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            HashMap<String, Object> hashObj = (HashMap<String, Object>) toMap(obj);
            list.add(hashObj);
        }
        return list;
    }
}
