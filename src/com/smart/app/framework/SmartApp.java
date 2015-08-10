/*?
* AppMain.java?
*?˵����
*?Created?by Sky on??2015/7/9
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.smart.app.framework;

import android.app.Application;
import com.smart.app.framework.server.SmartHttpServer;

/**
 * Created by sky on 2015/7/9.
 */
public class SmartApp extends Application{

    private static SmartApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        new SmartHttpServer().startServer();
        app = this;
    }

    public static SmartApp getInstance(){
        return app;
    }
}
