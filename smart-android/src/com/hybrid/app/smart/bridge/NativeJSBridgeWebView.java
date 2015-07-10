/* 
* NativeJSBridgeWebView.java 
* 说明：
* Created by Sky on  2015/6/8
* Copyright (c) 2015 Sky All Rights Reserved
*/
package com.hybrid.app.smart.bridge;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by sky on 2015/7/5
 */
public class NativeJSBridgeWebView extends WebView {

    public int webViewId = 0;

    public NativeJSBridgeWebView(Context context) {
        super(context);
        init(context);
    }

    public NativeJSBridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NativeJSBridgeWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        Log.d(">>>WebView","init");
        WebSettings settings = this.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + ";smart/android");
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setAppCacheEnabled(false);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //settings.setBlockNetworkImage(false);
        this.setWebChromeClient(new NativeJSBridgeWebChromeClient());
        this.setWebViewClient(new NativeJSBridgeWebViewClient());
        // Android开启Chrome DevTools 远程调试WebView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        // 新创建的WebView加入WebView管理
        NativeJSBridgeWebViewManager.addWebView(this);
    }

    @Override
    public void loadUrl(String url) {
        Log.d(">>>loadUrl", url);
        if(!TextUtils.isEmpty(url) && url.startsWith("javaScript:")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String scriptCommand = url.replace("javaScript:", "");
                this.evaluateJavascript(scriptCommand, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.i("NativeJSBridgeWebView", "evaluateJavascript onReceiveValue value=" + value);
                    }
                });
            } else {
                super.loadUrl(url);
            }
        }else{
            super.loadUrl(url);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        NativeJSBridgeWebViewManager.removeWebView(webViewId);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NativeJSBridgeWebViewManager.removeWebView(webViewId);
    }
}
