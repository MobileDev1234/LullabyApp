package com.hr.rain.Global;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by RabbitJang on 9/2/2018.
 */

public class APIManager {
    public static final int HTTP_POST           = 1;
    public static final int HTTP_GET            = 2;
    public static final int HTTP_PUT            = 3;
    public static final int HTTP_DELETE         = 4;

    public static String SERVER_ADDR                       = "http://34.214.47.217/rain/api/";
    private static APIManager instance = null;
    private APIManagerCallback callback = null;

    public static APIManager getInstance() {
        if(instance == null) {
            instance = new APIManager();
        }
        return instance;
    }

    // Set callback function after finished api request..
    public void setCallback(APIManagerCallback callback) {
        this.callback = callback;
    }

    public void getVideo() {
        String API_URL = String.format("get_video.php");
        APITask task = new APITask(SERVER_ADDR, API_URL, null, HTTP_POST);
        task.execute((Void) null);
    }


    // API Task..
    private class APITask extends AsyncTask<Void, Void, Boolean> {
        /* ---------------- API Task Variables ---------------- */
        // For calling api request..
        private String serverAddr = "";
        private String apiURL = "";
        private JSONObject reqObject = null;
        private InputStream inputStream;
        private int method = 0;

        // Result of api request..
        private JSONObject result = null;

        /* ---------------- API Task Functions ---------------- */
        APITask(String serverAddr, String apiURL, JSONObject reqParams, int method) {
            this.serverAddr = serverAddr;
            this.apiURL = apiURL;
            this.reqObject = reqParams;
            this.method = method;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            result = requestAPI();
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (callback != null) {
                callback.APICallback(result);
            }
        }

        @Override
        protected void onCancelled() {
            if (callback != null) {
                callback.APICallback(null);
            }
        }

        private JSONObject requestAPI() {
            JSONObject result = null;

            try {
                String apiRequestURL = serverAddr + apiURL;
                HttpClient httpclient = new DefaultHttpClient();
                if (reqObject != null) {
                    /*if(SharedData.getInstance().userId != 0 && SharedData.getInstance().token != null) {
                        reqObject.accumulate("UserId", SharedData.getInstance().userId);
                        reqObject.accumulate("Token", SharedData.getInstance().token);
                    }*/
                } else{
                    reqObject = new JSONObject();
                    /*if(SharedData.getInstance().userId != 0 && SharedData.getInstance().token != null) {
                        reqObject.accumulate("UserId", SharedData.getInstance().userId);
                        reqObject.accumulate("Token", SharedData.getInstance().token);
                    }*/
                }
                StringEntity se = new StringEntity(reqObject.toString(), HTTP.UTF_8);
                HttpResponse httpResponse;

                switch (method) {
                    case HTTP_GET:
                        HttpGet httpGet = new HttpGet(apiRequestURL);
                        httpGet.setHeader("Accept", "application/json");
                        httpGet.setHeader("Content-type", "application/json");

                        httpResponse = httpclient.execute(httpGet);
                        break;

                    case HTTP_DELETE:
                        HttpDelete httpDelete = new HttpDelete(apiRequestURL);
                        httpDelete.setHeader("Accept", "application/json");
                        httpDelete.setHeader("Content-type", "application/json");

                        httpResponse = httpclient.execute(httpDelete);
                        break;

                    case HTTP_PUT:
                        HttpPut httpPut = new HttpPut(apiRequestURL);
                        httpPut.setEntity(se);
                        httpPut.setHeader("Accept", "application/json");

                        httpResponse = httpclient.execute(httpPut);
                        break;

                    default:
                        HttpPost httpPost = new HttpPost(apiRequestURL);
                        httpPost.setEntity(se);
                        httpPost.setHeader("Accept", "application/json");
                        httpPost.setHeader("Content-type", "application/json");
                        httpResponse = httpclient.execute(httpPost);
                        break;
                }

                inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null) {
                    String iData = convertInputStreamToString(inputStream);
                    result = new JSONObject(iData);
                } else {
                    Log.d("[APITask] requestAPI", "No input stream prepared!");
                }

            } catch (Exception e) {
                Log.e("[APITask] requestAPI", e.getLocalizedMessage());
            }

            return result;
        }

        private String convertInputStreamToString(InputStream inputStream) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String result = "";
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                inputStream.close();
            } catch (Exception e) {
                Log.e("[APITask] ConvertInput", e.getLocalizedMessage());
            }
            return result;
        }
    }
}