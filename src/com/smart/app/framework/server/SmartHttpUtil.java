package com.smart.app.framework.server;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public class SmartHttpUtil {

    public static void responseJSONSuccess(OutputStream output, String responseText) {
        response(output, "application/json", "200 OK", responseText);
    }

    public static void responseJSONError(OutputStream output, String responseText) {
        response(output, "application/json", "400 OK", responseText);
    }

    public static void responseTextSuccess(OutputStream output, String responseText) {
        response(output, "text/plain", "200 OK", responseText);
    }

    public static void responseTextError(OutputStream output, String responseText) {
        response(output, "text/plain", "400 ", responseText);
    }


    public static void responseError(OutputStream output, String responseText) {
        response(output, "text/plain", "400 ", responseText);
    }

    public static void responseSuccess(OutputStream output, String contentType, String responseText) {
        response(output, contentType, "200 OK", responseText);
    }

    public static void response(OutputStream output, String contentType, String description, String responseText) {
        Log.d(">>>NativeH5", " xhr response return start time:" + System.currentTimeMillis() + " responseText:" + responseText);

        PrintWriter pw = new PrintWriter(output);
        pw.println("HTTP/1.1 " + description);
        pw.println("Content-Type: " + contentType);
        pw.println("Date: " + new Date());
        pw.println("Access-Control-Allow-Origin: *");
        pw.println("Content-Length: " + responseText.length());
        pw.println();
        pw.println(responseText);
        pw.flush();
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
