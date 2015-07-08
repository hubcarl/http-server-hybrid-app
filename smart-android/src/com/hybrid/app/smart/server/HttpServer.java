/*?
* HttpServer.java?
*?ËµÃ÷£º
*?Created?by Sky on??2015/7/8
*?Copyright?(c)?2015 Sky All?Rights?Reserved
*/
package com.hybrid.app.smart.server;

import android.content.Context;

import java.io.IOException;

/**
 * Created by sky on 2015/7/5.
 */
public class HttpServer {

    private final static int PORT = 10001 ;
    private Context mContext;
    private NanoHTTPD httpServer;

    public void start(){
        httpServer = new NanoHTTPD(PORT) {
            @Override
            public void start() throws IOException {
                super.start();
            }

            @Override
            public Response serve(IHTTPSession session) {
                return super.serve(session);
            }
        };
        try {
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        httpServer.stop();
    }
}
