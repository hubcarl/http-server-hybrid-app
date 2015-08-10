/*?
* MessageAction.java?
*?˵����
*?Created?by Sky on??2015/8/4
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.smart.app.framework.message;

/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public enum  MessageAction {

    DEFAULT_ACTION("default_action"),
    SET_TITLE("setTitle"),
    OPEN_WINDOW("openWindow"),
    CLOSE_WINDOW("closeWindow"),
    PULL_REFRESH("pullRefresh"),
    SHOW_TOAST("showToast"),
    SHOW_DIALOG("showLoading"),
    SHOW_LOADING("showDialog"),
    CLOSE_LOADING("closeLoading"),
    HTTP_GET("httpGet"),
    HTTP_POST("httpPost"),
    GET_ENV("getEnv"),
    GET_CACHE("getCache"),
    SET_CACHE("setCache"),
    REGISTER_EVENT("registerEvent"),
    CANCEL_EVENT("cancelEvent");

    private String action;

    private MessageAction(String action){
        this.action = action;
    }

    public static MessageAction find(String action){
        for(MessageAction msgAction: values()){
            if(msgAction.action.equals(action)){
                return msgAction;
            }
        }
        return DEFAULT_ACTION;
    }

}
