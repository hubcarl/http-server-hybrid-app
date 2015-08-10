/*
*HttpServer.java
*˵����
*Created by Sky on2015/7/8
*Copyright(c)2015 Sky All Rights Reserved
*/
package com.smart.app.framework.server;


import android.util.Log;
import com.smart.app.framework.bridge.SmartNativeJSBridge;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public class SmartHttpServer implements Runnable {

    private int port = 9999;

    private Thread httpServerThread;

    //private ExecutorService threadPoll = Executors.newCachedThreadPool();

    public void startServer() {
        this.httpServerThread = new Thread(this);
        this.httpServerThread.start();
    }

    public void restartServer() {
        this.startServer();
    }

    public void run() {

        try {

            ServerSocket serverSocket = new ServerSocket(this.port);

            do {
                final Socket socket = serverSocket.accept();

//                threadPoll.execute(new Runnable() {
//                    @Override
//                    public void run() {
                try {
                    System.out.println(">>>Current Thread[SmartHttpServer]:" + Thread.currentThread().getName());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                    String line = null;
                    int contentLength = 0;
                    final String contentHeader = "Content-Length:";
                    while (!(line = reader.readLine()).equals("")) {
                        System.out.println(">>>input:" + line);
                        if (line.startsWith(contentHeader)) {
                            contentLength = Integer.parseInt(line.replace(contentHeader, "").trim());
                            System.out.println(">>>input contentLength:" + contentLength);
                        }
                    }

                    StringBuilder body = new StringBuilder();
                    int c = 0;
                    for (int i = 0; i < contentLength; i++) {
                        c = reader.read();
                        body.append((char) c);
                    }
                    OutputStream out = socket.getOutputStream();
                    String result = SmartNativeJSBridge.callNative(out, URLDecoder.decode(body.toString(), "UTF-8"));
                    if (result != "") {
                        SmartHttpUtil.responseTextSuccess(out, result);
                    }
                } catch (IOException e) {
                    Log.e("SmartHttpServer", "InputStream read failed" + e.toString());
                    try {
                        SmartHttpUtil.responseError(socket.getOutputStream(),"");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
//                    }
//                });
            } while (!serverSocket.isClosed());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
