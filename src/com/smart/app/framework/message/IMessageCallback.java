/*?
* IMessage.java?
*?˵����
*?Created?by Sky on??2015/8/4
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.smart.app.framework.message;

import android.content.Context;
import android.content.Intent;
import org.json.JSONObject;

/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public interface IMessageCallback {

    void onReceiveMessage(MessageAction action,JSONObject json);
}
