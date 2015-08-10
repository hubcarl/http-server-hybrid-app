package com.smart.app.framework.bridge;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public class SmartWebViewClient extends WebViewClient {

    private final static String INIT_WEBVIEW_JAVASCRIPT = "javaScript:if(window.SmartNativeJSBridge){window.SmartNativeJSBridge.webViewId=%d;}else{window.SmartNativeJSBridge={webViewId:%d}};";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        int webViewId = ((SmartWebView) view).getWebViewId();
        view.loadUrl(String.format(INIT_WEBVIEW_JAVASCRIPT, webViewId, webViewId));
        Log.d(">>>Native", "onPageStarted");
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        int webViewId = ((SmartWebView) view).getWebViewId();
        view.loadUrl(String.format(INIT_WEBVIEW_JAVASCRIPT, webViewId, webViewId));
        Log.d(">>>Native", "onPageFinished");
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return super.shouldInterceptRequest(view, url);
    }
}
