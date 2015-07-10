/*?
* AppMain.java?
*?ËµÃ÷£º
*?Created?by Sky on??2015/7/9
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.hybrid.app.smart;

import android.app.Application;
import android.content.Intent;
import com.hybrid.app.smart.service.SmartService;

/**
 * Created by Administrator on 2015/7/9.
 */
public class App extends Application{

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, SmartService.class));
        app = this;
    }

    public static App getInstance(){
        return app;
    }
}
