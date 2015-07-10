/*?
* SmartService.java?
*?ËµÃ÷£º
*?Created?by Sky on??2015/7/8
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.hybrid.app.smart.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.hybrid.app.smart.server.HttpServer;

/**
 * Created by Administrator on 2015/7/8.
 */
public class SmartService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        new HttpServer().startServer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
