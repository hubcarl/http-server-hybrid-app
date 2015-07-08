/*?
* CacheManager.js
*?˵����
*?Created?by Sky on??2015/7/7
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
 (function(global){

   var supportLocalStorage = window.localStorage && window.localStorage.getItem;

   var cacheManager ={

     /**
      * ���û��棬֧����Ч�ڣ���λ��
      * @param key
      * @param value
      * @param age ��
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
      * @param expireEmpty ���������Ч�� true: ����"", ���򷵻��ѹ���ֵ
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
      * �Ƴ�����
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