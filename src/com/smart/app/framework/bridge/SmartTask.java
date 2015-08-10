package com.smart.app.framework.bridge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hybrid App Framework
 * User: sky
 * Date: 2015-8-9
 */
public class SmartTask {
    private final static ExecutorService threadPoll = Executors.newCachedThreadPool();
    public static void execute(Runnable runnable){
        threadPoll.execute(runnable);
    }
}
