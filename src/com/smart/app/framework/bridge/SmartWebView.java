package com.smart.app.framework.bridge;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.smart.app.framework.message.IMessageCallback;
import com.smart.app.framework.message.MessageAction;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public class SmartWebView extends WebView {

    private int webViewId = 0;

    private Map<String, String> registerEventList;

    private IMessageCallback messageCallback;

    public void setWebViewId(int webViewId){
        this.webViewId = webViewId;
    }

    public int getWebViewId(){
        return webViewId;
    }

    public Map<String, String> getEventList(){
        return registerEventList;
    }

    public void addEvent(String name, String params) {
        if (registerEventList == null) {
            registerEventList = new HashMap<String, String>(8);
        }
        registerEventList.put(name, params);
    }

    public void removeEvent(String name) {
        if (registerEventList != null && registerEventList.containsKey(name)) {
            registerEventList.remove(name);
        }
    }

    public Activity getActivity(){
        return (Activity) this.getContext();
    }

    public SmartWebView(Context context, IMessageCallback messageCallback) {
        super(context);
        init(context, null);
        setMessageCallback(messageCallback);
    }

    public SmartWebView(Context context, IMessageCallback messageCallback, ProgressBar progressBar) {
        super(context);
        init(context, progressBar);
        setMessageCallback(messageCallback);
    }

    public SmartWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public SmartWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, null);
    }

    private void init(Context context, ProgressBar progressBar) {
        Log.d(">>>WebView", "init");
        WebSettings settings = this.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + ";smart/android");
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setAppCacheEnabled(false);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //settings.setBlockNetworkImage(false);
        this.setWebChromeClient(new SmartWebChromeClient(progressBar));
        this.setWebViewClient(new SmartWebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        SmartWebViewManager.addWebView(this);
    }

    public void setMessageCallback(IMessageCallback messageCallback){
        this.messageCallback = messageCallback;
    }


    public void executeMessageCallback(SmartWebView webView, MessageAction action, JSONObject args){
        if(this.messageCallback!=null){
            this.messageCallback.onReceiveMessage(action, args);
        }
    }

    public void loadJavaScript(String scriptCommand){
        Log.d(">>>loadJavaScript","#Native#start:" + System.currentTimeMillis());
        loadUrl("javaScript:" + scriptCommand);
    }

    @Override
    public void loadUrl(String url) {
        Log.d(">>>loadUrl", url);
        if (!TextUtils.isEmpty(url) && url.startsWith("javaScript:")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String scriptCommand = url.replace("javaScript:", "");
                this.evaluateJavascript(scriptCommand, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.i("SmartWebView", "evaluateJavascript onReceiveValue value=" + value);
                    }
                });
            } else {
                super.loadUrl(url);
            }
        } else {
            super.loadUrl(url);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        SmartWebViewManager.removeWebView(webViewId);
    }
}
