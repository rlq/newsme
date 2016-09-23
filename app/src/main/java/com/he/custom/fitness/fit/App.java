package com.he.custom.fitness.fit;

import android.app.Application;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

/**
 * Created by lqren on 16/7/30.
 */
public class App extends Application {
    private static String TAG = "fit";
    private WakeLock mLock;
    private static App sApp;
    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        acquireWakeLock();
    }

    public static App getApp(){
        return sApp;
    }

    public void acquireWakeLock(){
        releaseWakeLock();

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        mLock.acquire();
    }

    public void releaseWakeLock(){
        if(mLock != null){
            mLock.release();
            mLock = null;
        }
    }
}
