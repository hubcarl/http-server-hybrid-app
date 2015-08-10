package com.smart.app.framework.bridge;

import android.content.Intent;
import android.util.Log;
import com.smart.app.framework.SmartApp;
import com.smart.app.framework.message.MessageAction;
import com.smart.app.framework.server.SmartHttpUtil;
import com.smart.app.framework.utils.SystemUtil;
import com.smart.app.vendor.ACache;
import com.smart.app.vendor.OkHttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;

/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public class SmartNativeAPI {

    private final static String SCRIPT_CALLBACK_COMMAND = "SmartNativeJSBridge.callbackJS(%s,%s)";

    private final static String KEY_PARAM_TITLE = "title";

    private final static String KEY_PARAM_URL = "url";

    private final static String KEY_PARAM_PARAMS = "params";

    private final static String KEY_PARAM_OPTIONS = "options";

    private final static String KEY_PARAM_CALLBACK_ID = "callbackId";

    public static String getEnv(SmartWebView webView, JSONObject args) {
        return "test123";
    }


    public static void broadcastEvent(final SmartWebView webView, final JSONObject args) {
        String name = args.optString("name");
        String params = args.optString(KEY_PARAM_PARAMS);
        Map<Integer, WeakReference<SmartWebView>> views = SmartWebViewManager.getAllWebView();
        Collection<WeakReference<SmartWebView>> list = views.values();
        for (WeakReference<SmartWebView> weakReference : list) {
            SmartWebView smartWebView = weakReference.get();
            if (smartWebView != null
                    && smartWebView.getEventList() != null
                    && smartWebView.getEventList().containsKey(name)) {
                smartWebView.loadJavaScript(String.format(SCRIPT_CALLBACK_COMMAND, name, params));
            }
        }
    }

    public static void cancelEvent(final SmartWebView webView, final JSONObject args) {
        String name = args.optString("name");
        webView.removeEvent(name);
    }

    public static void httpGet(final SmartWebView webView, final JSONObject args, final OutputStream out) {
        OkHttpUtil.httpGet(args.optString(KEY_PARAM_URL), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
//                webView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        webView.loadJavaScript(String.format(SCRIPT_CALLBACK_COMMAND,
//                                args.optInt(KEY_PARAM_CALLBACK_ID), "{code:50000}"));
//                    }
//                });
                SmartHttpUtil.responseTextError(out, "http request error:" + e.toString());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String responseText = response.body().string();
//
//                final JSONObject json = new JSONObject();
//                try {
//                    json.put("data", responseText);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                webView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        webView.loadJavaScript(String.format(SCRIPT_CALLBACK_COMMAND,
//                                args.optInt(KEY_PARAM_CALLBACK_ID), json.toString()));
//                    }
//                });
                SmartHttpUtil.responseTextSuccess(out, responseText);
            }
        });
    }

    public static void httpPost(final SmartWebView webView, final JSONObject args, final OutputStream out) {
        final long startTime = System.currentTimeMillis();
        OkHttpUtil.httpPost(args.optString(KEY_PARAM_URL), args.optString(KEY_PARAM_PARAMS), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                SmartHttpUtil.responseTextError(out, "http request error:" + e.toString());
//                webView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        webView.loadJavaScript(String.format(SCRIPT_CALLBACK_COMMAND,
//                                args.optInt(KEY_PARAM_CALLBACK_ID), "{code:50000}"));
//                    }
//                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String responseText = response.body().string();
                final long endTime = System.currentTimeMillis();
                Log.d(">>>NativeH5 [httpPost]", "native http request cost time:" + (endTime - startTime));
                SmartHttpUtil.responseTextSuccess(out, responseText);

//                SmartTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        final JSONObject json = new JSONObject();
//                        try {
//                            json.put("data", responseText);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        webView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                webView.loadJavaScript(String.format(SCRIPT_CALLBACK_COMMAND,
//                                        args.optInt(KEY_PARAM_CALLBACK_ID), json.toString()));
//                            }
//                        });
//                    }
//                });
            }
        });
    }

    public static void showImageGallery(final SmartWebView webView, final JSONObject args) {

    }
}
