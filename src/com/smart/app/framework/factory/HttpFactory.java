package com.smart.app.framework.factory;

import android.util.Log;
import com.smart.app.framework.utils.Encrypt;
import com.smart.app.framework.utils.SystemUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by sky on 2015/8/9.
 */
public class HttpFactory {

    public final static String CONTENT_TYPE="application/x-www-form-urlencoded; charset=utf-8";


    private static String signParams(JSONObject jsonObject){

        List<String> list = new ArrayList<String>();
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()){
            list.add(keys.next());
        }

        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<list.size();i++){
            String key = list.get(i);
            if(i>0){
              sb.append("&");
            }
            sb.append(key);
            sb.append("=");
            sb.append(jsonObject.optString(key, ""));
        }
        String sign = Encrypt.md5(sb.toString());
        sb.append("&sign=");
        sb.append(sign);
        Log.d(">>>sign after:", sb.toString());

        return sb.toString();
    }


    public static String paramsFactory(String jsonParams){
        try {
            JSONObject jsonObject = new JSONObject(jsonParams);
            jsonObject.put("imei", SystemUtil.getDeviceId());
            jsonObject.put("ver",  SystemUtil.getApkVersionCode());
            jsonObject.put("model", android.os.Build.MODEL);
            jsonObject.put("sdk",  android.os.Build.VERSION.RELEASE);
            jsonObject.put("time", System.currentTimeMillis());
            return signParams(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParams;
    }

}
