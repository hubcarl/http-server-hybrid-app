/*?
* NativeJSBridge.java?
*?ËµÃ÷£º
*?Created?by Sky on??2015/7/10
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.hybrid.app.smart.bridge;

import android.util.Log;
import android.webkit.WebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Administrator on 2015/7/10.
 */
public class NativeJSBridge {

   public static void callNative(String jsonText){
       Log.d(">>>input:jsonText", jsonText);
       try {
           Object obj = new JSONTokener(jsonText).nextValue();
           if (obj instanceof JSONObject){
               JSONObject json = (JSONObject)obj;
               String method = json.optString("method");
               String args =  json.optString("args");
               int callbackId = json.optInt("callbackId");
               int webVieId = json.optInt("webVieId");
               WebView webView = NativeJSBridgeWebViewManager.getWebView(webVieId);
               if(webView!=null){

               }
           } else if (obj instanceof JSONArray) {
               JSONArray jsonArray = (JSONArray)obj;
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
   }
}
