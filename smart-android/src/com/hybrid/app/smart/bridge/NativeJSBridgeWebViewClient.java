/*?
* NativeJSBridgeWebViewClient.java?
*?˵����
*?Created?by Sky on??2015/7/8
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.hybrid.app.smart.bridge;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by sky on 2015/7/5
 */
public class NativeJSBridgeWebViewClient extends WebViewClient {

    private final static String INIT_WEBVIEW_JAVASCRIPT = "javaScript:window.NativeJSBridge={webViewId:%d}";
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        view.loadUrl(String.format(INIT_WEBVIEW_JAVASCRIPT, ((NativeJSBridgeWebView) view).webViewId));
        Log.d(">>>Native", "onPageStarted");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
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