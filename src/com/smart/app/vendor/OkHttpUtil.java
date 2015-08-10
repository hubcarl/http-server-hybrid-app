/*?
* OkHttpUtil.java?
*?˵����
*?Created?by Sky on??2015/7/28
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.smart.app.vendor;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.util.Log;
import com.smart.app.framework.bridge.SmartTask;
import com.smart.app.framework.factory.SmartFactory;
import com.squareup.okhttp.*;

//http://lzyblog.com/2015/01/06/Square%E5%BC%80%E6%BA%90%E5%BA%93OKHttp%E7%9A%84%E5%88%86%E6%9E%90%E5%92%8C%E4%BD%BF%E7%94%A8%EF%BC%88%E5%9B%9B%EF%BC%89/
public class OkHttpUtil {

    private static final String CHARSET_NAME = "UTF-8";

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    static {
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    public static String httpGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String httpGet(String url, Map<String, String> params) throws IOException {
        if (params != null) {
            StringBuffer buffer = new StringBuffer();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buffer.append(entry.getKey());
                buffer.append("=");
                buffer.append(entry.getValue());
                buffer.append("&");
            }
            if (url.indexOf("?") > -1) {
                url += buffer.toString();
            } else {
                url += "?" + buffer.toString();
            }
            url = url.substring(0, url.lastIndexOf("&") - 1);
        }
        return httpGet(url);
    }

    public static void httpGet(final String url, final Callback responseCallback) {
        SmartTask.execute(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url).build();
                mOkHttpClient.newCall(request).enqueue(responseCallback);
            }
        });
    }

    public static void httpPost(final String url, final String jsonParams, final Callback responseCallback) {
        SmartTask.execute(new Runnable() {
            @Override
            public void run() {
                String params = SmartFactory.makeHttpPostParams(jsonParams);
                Log.d(">>>httpPost", "#params:" + params);
                RequestBody body = RequestBody.create(SmartFactory.getContentType(url), params);
                Request request = new Request.Builder().url(url).post(body).build();
                mOkHttpClient.newCall(request).enqueue(responseCallback);
            }
        });
    }

    public static void enqueue(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Response arg0) throws IOException {

            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {

            }
        });
    }
}