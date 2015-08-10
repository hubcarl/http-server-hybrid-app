(function (global) {

  var SmartNativeAPI = {

    callNative: function (method, args, callback) {
      return SmartNativeJSBridge.callNative(SmartNativeJSBridge.defaultClassName, method, args, callback);
    },

    getEnv: function (key) {
      return this.callNative('getEnv', {key: key, sync:true});
    },

    showDialog: function (title, content, description, btnList) {
      this.callNative('showDialog', {title: title, content: content, description: description, btnList: btnList});
    },

    showLoading: function (message) {
      this.callNative('showLoading', {message: message});
    },

    httpGet: function (url, params, callback) {
      this.callNative('httpGet', {url: url, params:params}, callback);
    },

    httpPost: function (url, params, callback) {
      this.callNative('httpPost', {url: url, params:params}, callback);
    }

  };

  global.SmartNativeAPI = SmartNativeAPI;

})(window);
