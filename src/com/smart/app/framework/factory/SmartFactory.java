package com.smart.app.framework.factory;

import com.squareup.okhttp.MediaType;

/**
 * Created by sky on 2015/8/9.
 */
public class SmartFactory {

    public static String makeHttpPostParams(String params){
        return HttpFactory.paramsFactory(params);
    }


    public static MediaType getContentType(String url){
        return MediaType.parse(HttpFactory.CONTENT_TYPE);
    }
}
