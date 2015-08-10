package com.smart.app.framework.bridge;

import android.app.Activity;
import android.util.Log;
import android.webkit.WebView;
import com.smart.app.framework.message.MessageAction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public class SmartNativeJSBridge {

    public static String callNative(OutputStream out, String params) {
        Log.d(">>>H5Native#", "callNative params:" + params);
        String result = "";
        try {
            Object obj = new JSONTokener(params).nextValue();
            if (obj instanceof JSONObject) {
                JSONObject json = (JSONObject) obj;
                String className = json.optString("className");
                String methodName = json.optString("method");
                JSONObject args = new JSONObject(json.optString("args"));
                int webViewId = args.optInt("webViewId");
                if(webViewId>0){
                    SmartWebView webView = SmartWebViewManager.getWebView(webViewId);
                    MessageAction messageAction = MessageAction.find(methodName);
                    switch (messageAction) {
                        case GET_ENV:
                            result = SmartNativeAPI.getEnv(webView,args);
                            break;
                        case REGISTER_EVENT:
                            break;
                        case CANCEL_EVENT:
                            break;
                        case HTTP_GET:
                            SmartNativeAPI.httpGet(webView, args, out);
                            break;
                        case HTTP_POST:
                            SmartNativeAPI.httpPost(webView, args, out);
                            break;
                        default:
                            webView.executeMessageCallback(webView, messageAction, args);
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
