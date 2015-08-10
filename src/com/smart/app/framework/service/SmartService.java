/*?
* SmartService.java?
*?˵����
*?Created?by Sky on??2015/7/8
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.smart.app.framework.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by sky on 2015/7/8.
 */
public class SmartService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
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
