/*?
* CacheManager.js
*?说明：
*?Created?by Sky on??2015/7/7
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
 (function(global){

   var supportLocalStorage = window.localStorage && window.localStorage.getItem;

   var cacheManager ={

     /**
      * 设置缓存，支持有效期，单位秒
      * @param key
      * @param value
      * @param age 秒
      */
     setCache:function(key, value, age){
        if(supportLocalStorage){
          try{
            window.localStorage.setItem(key, JSON.stringify({value:value, time:(new Date()).getTime()/1000 + age}));
          }catch(e){
            //localStorage.clear();
            //window.localStorage.setItem(key, JSON.stringify({value:value, time:(new Date()).getTime()/1000 + age}));
          }
        }
     },

     /**
      *
      * @param key
      * @param expireEmpty 如果超过有效期 true: 返回"", 否则返回已过期值
      * @returns
      */
     getCache:function(key, expireEmpty, clearCache){
       var ret ="";
       if(supportLocalStorage){
         var ret = window.localStorage.getItem(key);
         var json = JSON.parse(ret||'[]');
         if(json.time && json.time>(new Date()).getTime()/1000){
           return json.value;
         }else if(expireEmpty){
           return "";
         }else{
           return json.value;
         }
       }
       if(clearCache){
         window.localStorage.removeItem(key);
       }
       return ret;
     },

     /**
      * 移除缓存
      * @param key
      */
     remove: function(key){
       if(supportLocalStorage) {
         window.localStorage.removeItem(key);
       }
     }
   };


   global.CacheManager = cacheManager;
 })(window);