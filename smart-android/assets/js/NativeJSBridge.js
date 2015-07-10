/*
 * bridge.java
 *说明：JavaScript 与 Native 互相通信
 *Created by Sky on 2015/6/8
 *Copyright(c) 2015 Sky All Rights Reserved
 */

(function (global) {

  console.log(">>>init NativeJSBridge:" + window.NativeJSBridge);

  var bridge = window.NativeJSBridge || {};

  bridge.nativeJSQueue = {};

  bridge.callbackId = 0;

  bridge.mode = {
    server: 1 /* http server */,
    prompt: 2 /* android prompt模式 */,
    iframe: 3 /* iframe模式 */,
    protocol: 4 /* 自定义协议 */
  };

  bridge.isAndroid = navigator.userAgent && navigator.userAgent.indexOf('smart/android') > -1;
  bridge.isIOS = navigator.userAgent && navigator.userAgent.indexOf('smart/ios') > -1;
  bridge.android = {defaultMode: bridge.mode.server};
  bridge.ios = {defaultMode: bridge.mode.server};

  bridge.callbackJS = function (callbackId, data) {
    if (bridge.nativeJSQueue.hasOwnProperty(callbackId)) {
      bridge.nativeJSQueue[callbackId](data);
      bridge.nativeJSQueue[callbackId] = undefined;
      delete bridge.nativeJSQueue[callbackId];
    }
  }

  bridge.callJS = function (type, params, data) {

  }

  bridge.callNative = function (method, args, callback) {
    var jsonText =  {"method": method, "args": args};
    if (callback) {
      bridge.nativeJSQueue[++bridge.callbackId] = callback;
      jsonText.callbackId = bridge.callbackId;
      jsonText.webViewId = bridge.webViewId;
    }
    if (bridge.isAndroid) {
      if (bridge.android.defaultMode == bridge.mode.server) {
        return bridge.xhr(jsonText,callback);
      } else if (bridge.android.defaultMode == bridge.mode.prompt) {
        return prompt(JSON.stringify(jsonText));
      } else {
        console.log('The android mode ' + bridge.android.defaultMode + ' not suport');
      }
    } else if (bridge.isIOS) {
      if (bridge.ios.defaultMode == bridge.mode.server) {
        return bridge.xhr(jsonText,callback);
      } else if (bridge.ios.defaultMode == bridge.mode.iframe) {

      } else if (bridge.ios.defaultMode == bridge.mode.protocol) {

      } else {
        console.log('The ios mode ' + bridge.defaultMode.ios + ' not suport');
      }
    }
  }

  bridge.xhr = function (jsonText, callback) {
    console.log('>>>H5 jsonText:' + jsonText);
    var encodeJsonText = encodeURIComponent(JSON.stringify(jsonText));
    var requestUrl = 'http://127.0.0.1:9999?st='+ new Date().getTime();
    var xhr = new XMLHttpRequest();
    xhr.open("POST", requestUrl, true);
    xhr.setRequestHeader("Connection", "close");
    xhr.setRequestHeader("Content-length", encodeJsonText.length);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onReadyStateChange = function () {
      if (xhr.readyState == 4 && xhr.status == 200) {
        console.log('>>>xhr result:' + xhr.responseText);
      }
    };
    xhr.send(encodeJsonText);
    return xhr.responseText;
  }

  global.NativeJSBridge = bridge;

})(window);
