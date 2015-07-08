/*
* NativeJSBridge.java
*说明：JavaScript 与 Native 互相通信
*Created by Sky on 2015/6/8
*Copyright(c) 2015 Sky All Rights Reserved
*/

(function(global){

  var NativeJSBridge= {
    pageId:window.pageId,
    nativeJSQueue:{},
    callbackId:0
  };

  NativeJSBridge.NativeCallbackJS = function(callbackId, data) {
    if (NativeJSBridge.nativeJSQueue.hasOwnProperty(callbackId)) {
      NativeJSBridge.nativeJSQueue[callbackId](data);
      NativeJSBridge.nativeJSQueue[callbackId] = undefined;
      delete NativeJSBridge.nativeJSQueue[callbackId];
    }
  }

  NativeJSBridge.JSCallNative = function(method, args, callback){
    args = args||{};
    if(callback){
      NativeJSBridge.nativeJSQueue[++NativeJSBridge.callbackId]= callback;
      args.callbackId = NativeJSBridge.callbackId;
      args.pageId = NativeJSBridge.pageId;
    }
    return NativeJSBridge.XHR(method, args);
  }

  NativeJSBridge.XHR = function(method, args, callback){
    var params = {"method":method, "args":args};
    var requestUrl = 'http://com.hybrid.app/smart/bridge';
    var xhr = new XMLHttpRequest();
    xhr.open("POST", requestUrl, true);
    xhr.setRequestHeader("Content-Type","application/x-www-urlencoded");
    xhr.onReadyStateChange = function(){
      if (xhr.readyState==4 && xhr.status==200) {
        console.log('>>>xhr result:' + xhr.responseText);
      }
    };
    xhr.send(JSON.stringify(params));
    return xhr.responseText;
  }

  global.NativeJSBridge = NativeJSBridge;

})(window);
