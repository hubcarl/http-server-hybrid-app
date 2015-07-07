/*
* NativeJSBridge.java
*˵����JavaScript �� Native ����ͨ��
*Created by Sky on 2015/6/8
*Copyright(c) 2015 Sky All Rights Reserved
*/

(function(global){

  // webViewId ��Native��ʼ��
  var NativeJSBridge= {
    webViewId:window.webViewId,
    nativeJSQueue:{},
    callbackId:0
  };

  NativeJSBridge.nativeCallJS = function(callbackId, data) {
    if (NativeJSBridge.nativeJSQueue.hasOwnProperty(callbackId)) {
      NativeJSBridge.nativeJSQueue[callbackId](data);
      NativeJSBridge.nativeJSQueue[callbackId] = undefined;
      delete NativeJSBridge.nativeJSQueue[callbackId];
    }
  }

  NativeJSBridge.jsCallNative = function(method, args, callback){
    args = args||{};
    if(callback){
      NativeJSBridge.nativeJSQueue[++NativeJSBridge.callbackId]= callback;
      args.callbackId = NativeJSBridge.callbackId;
      args.webViewId = NativeJSBridge.webViewId;
    }
    return NativeJSBridge.xhr(method, args);
  }

  NativeJSBridge.xhr = function(method, args, callback){

    var params = {"method":method, "args":args};

    var isAsync = true;
    if(args && args.async!=undefined){
      isAsync = args.async;
    }

    var requestUrl = 'http://com.hybrid.app/smart/bridge';
    if(window.location.protocol=='http:' || window.location.protocol=='https:'){
      requestUrl = window.location.host + "/com.hybrid.app/smart/bridge";
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", requestUrl, isAsync);
    xhr.setRequestHeader("Content-Type","application/x-www-urlencoded");
    xhr.onReadyStateChange = function(){
      if (xhr.readyState==4 && xhr.status==200) {
        console.log('>>>xhr result:' + xhr.responseText);
      }
    };

    xhr.send(JSON.stringify(params));
    if(!isAsync){
      return xhr.responseText;
    }else{
      return "";
    }
  }

  global.NativeJSBridge = NativeJSBridge;

})(window);
