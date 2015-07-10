/*?
* WebViewManager.java?
*?ËµÃ÷£º
*?Created?by Sky on??2015/7/10
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.hybrid.app.smart.bridge;

import android.webkit.WebView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/7/10.
 */
public class NativeJSBridgeWebViewManager {

    private static int webViewId = 0;
    private static Map<Integer,WeakReference<WebView>> views = new HashMap<Integer, WeakReference<WebView>>();

    public static WebView getWebView(int webViewId){
        WeakReference<WebView> reference = views.get(webViewId);
        if(reference!=null){
            return reference.get();
        }else{
            return null;
        }
    }

    public static void addWebView(NativeJSBridgeWebView view){
        view.webViewId = ++webViewId;
        views.put(view.webViewId,new WeakReference<WebView>(view));
    }

    public static void removeWebView(int webViewId){
        views.remove(webViewId);
    }

    public static void removeAllView(){
        views.clear();
    }
}
