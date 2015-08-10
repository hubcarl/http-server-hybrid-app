package com.smart.app.framework.bridge;

import com.smart.app.framework.ui.activity.SmartCommonActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public class SmartConfig {

    private static Map<String,Class> bridgeMapping = new HashMap<String, Class>(8);

    private static Map<String,Class> pageMapping = new HashMap<String, Class>(8);


    static {
        bridgeMapping.put("SmartNativeAPI", SmartNativeAPI.class);
        pageMapping.put("common", SmartCommonActivity.class);
    }

    public static Class getTargetPage(String target){
        return pageMapping.get(target);
    }

    public static void registerPageTarget(String target, Class clz) {
        pageMapping.put(target, clz);
    }

    public static Class getBridgeClass(String bridgeName){
        return bridgeMapping.get(bridgeName);
    }

    /**
     * ×¢²áJSBridgeNative
     *
     * @param className
     * @param clz
     */
    public static void registerBridgeClass(String className, Class clz) {
        bridgeMapping.put(className, clz);
    }
}
