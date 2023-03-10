package com.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

    public class ReadNotiBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Log.i("interval", "noti boardcast log.");
                }
            }, 0, 5000);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d(com.test.Broadcast.class.getSimpleName(), "Air Plane mode");
            }
        }
}
