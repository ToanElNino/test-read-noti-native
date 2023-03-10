package com.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ReadNotiBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.i("interval", "noti boardcast log.");

//            new Timer().scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    Log.i("interval", "noti boardcast log.");
//                }
//            }, 0, 5000);
            String appName = intent.getStringExtra("appName");
            String content = intent.getStringExtra("content");
            String sender = intent.getStringExtra("sender");
            String groupName = intent.getStringExtra("groupName");
            String time = intent.getStringExtra("time");
            int type = intent.getIntExtra("type", 0);
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("appName", appName)
                    .add("sender", sender)
                    .add("content", content)
                    .add("groupName", groupName)
                    .add("type", Integer.toString(type))
                    .add("time", time)
                    .build();
            Request request = new Request.Builder()
                    .url("https://64098fd06ecd4f9e18b44632.mockapi.io/api/notificationLogs")
                    .post(formBody)
                    .build();
            CallApiTask myAsyncTask = new CallApiTask();
            try {
                myAsyncTask.execute(formBody);
                // Do something with the response.
            } catch (Exception e) {
                e.printStackTrace();
            }
//            try {
//                 client.newCall(request).execute();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
}
