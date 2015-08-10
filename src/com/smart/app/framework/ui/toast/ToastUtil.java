/*?
* ToastUtil.java?
*?ËµÃ÷£º
*?Created?by Sky on??2015/7/20
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.smart.app.framework.ui.toast;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by sky on 2015/7/22.
 */
public class ToastUtil {

    public static void show(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, String message, int duration){
        Toast.makeText(context, message, duration).show();
    }
}
