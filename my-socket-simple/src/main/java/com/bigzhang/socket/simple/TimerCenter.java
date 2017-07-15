package com.bigzhang.socket.simple;

import java.util.Timer;
import java.util.TimerTask;

public enum TimerCenter {
    self;

    private long currentTime;
    private Timer timer;

    TimerCenter() {
        currentTime = System.currentTimeMillis();
        timer = new Timer("TimeWatcher");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentTime += 1000;
            }
        }, 1000, 1000);
    }

    public long getCurrentTimeMillis() {
        return currentTime;
    }

    public void stopQuietly(){
        if(timer!=null){
            timer.cancel();
        }
    }
}
