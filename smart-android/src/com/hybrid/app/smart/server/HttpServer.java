/*
*HttpServer.java
*说明：
*Created by Sky on2015/7/8
*Copyright(c)2015 Sky All Rights Reserved
*/
package com.hybrid.app.smart.server;


import android.util.Log;
import com.hybrid.app.smart.bridge.NativeJSBridge;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpServer implements Runnable {

    private int port = 9999;

    private Thread httpServerThread;

    private ExecutorService threadPoll = Executors.newCachedThreadPool();

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

                OutputStream out = null;

                threadPoll.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            System.out.println(">>>Current Thread[HttpServer]:" + Thread.currentThread().getName());
                            BufferedReader reader =new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                            String line=null;
                            int contentLength = 0;
                            // AJAX body内容在header的空行之后, 通过取得Content-Length获取body的长度
                            final String contentHeader= "Content-Length:";
                            while (!(line = reader.readLine()).equals("")) {
                                System.out.println(">>>input:" + line);
                                if (line.startsWith(contentHeader)) {
                                    contentLength = Integer.parseInt(line.replace(contentHeader,"").trim());
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
                            response(out, "text/plain", "200 OK");
                            NativeJSBridge.callNative(URLDecoder.decode(body.toString(), "UTF-8"));
                        }catch (IOException e){
                            Log.e("HttpServer","InputStream read failed" + e.toString());
                            try {
                                response(socket.getOutputStream(), "text/plain", "400 OK");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            } while (!serverSocket.isClosed());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response(OutputStream output, String contentType, String description){
        PrintWriter pw = new PrintWriter(output);
        pw.print("HTTP/1.1 " + description + " \r\n");
        pw.print("Content-Type: " + contentType + "\r\n");
        pw.print("Date: " + new Date() + "\r\n");
        pw.print("Access-Control-Allow-Origin: *\r\n");
        pw.print("\r\n");
        pw.flush();
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
