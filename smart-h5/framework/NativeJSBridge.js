/*
* bridge.java
*说明：JavaScript 与 Native 互相通信
*Created by Sky on 2015/6/8
*Copyright(c) 2015 Sky All Rights Reserved
*/

(function(global){

  var bridge= {
    pageId:window.pageId,
    nativeJSQueue:{},
    callbackId:0,
    mode: {server: 1 /* http server */, prompt: 2 /* android prompt模式 */, iframe: 3 /* iframe模式 */, protocol: 4 /* 自定义协议 */},
    isAndroid:navigator.userAgent&&navigator.userAgent.indexOf('smart/android')>-1,
    isIOS:navigator.userAgent&&navigator.userAgent.indexOf('smart/ios')>-1,
    android:{},
    ios:{}
  };

  // 默认bridge模式
  bridge.android.defaultMode = bridge.mode.server;
  bridge.ios.defaultMode = bridge.mode.server;

  bridge.nativeCallbackJS = function(callbackId, data) {
    if (bridge.nativeJSQueue.hasOwnProperty(callbackId)) {
      bridge.nativeJSQueue[callbackId](data);
      bridge.nativeJSQueue[callbackId] = undefined;
      delete bridge.nativeJSQueue[callbackId];
    }
  }

  bridge.nativeCallJS = function(type, params, data){

  }

  bridge.jsCallNative = function(method, args, callback){
    args = args||{};
    if(callback){
      bridge.nativeJSQueue[++bridge.callbackId]= callback;
      args.callbackId = bridge.callbackId;
      args.pageId = bridge.pageId;
    }
    if(bridge.isAndroid){
      if(bridge.android.defaultMode == bridge.mode.server){
        return bridge.xhr(method, args);
      }else if(bridge.android.defaultMode == bridge.mode.prompt){
        return prompt(JSON.stringify({"method":method, "args":args}));
      }else{
        console.log('The android mode ' + bridge.android.defaultMode + ' not suport');
      }
    }else if(bridge.isIOS){
      if(bridge.ios.defaultMode == bridge.mode.server){
        return bridge.xhr(method, args);
      }else if(bridge.ios.defaultMode == bridge.mode.iframe){

      }else if(bridge.ios.defaultMode == bridge.mode.protocol){

      }else{
        console.log('The ios mode ' + bridge.defaultMode.ios + ' not suport');
      }
    }
  }

  bridge.xhr = function(method, args, callback){
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

  global.NativeJSBridge = bridge;

})(window);
