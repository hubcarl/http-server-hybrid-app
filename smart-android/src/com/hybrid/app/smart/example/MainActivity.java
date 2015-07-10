package com.hybrid.app.smart.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.hybrid.app.smart.R;
import com.hybrid.app.smart.bridge.NativeJSBridgeWebView;



public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        System.out.println(">>>Current Thread[MainActivity]:" + Thread.currentThread().getName());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        NativeJSBridgeWebView webView = new NativeJSBridgeWebView(this);
        webView.loadUrl("file:///android_asset/main.html");

        addContentView(webView, params);

    }
}
