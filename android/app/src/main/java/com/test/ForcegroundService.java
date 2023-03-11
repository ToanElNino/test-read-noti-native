package com.test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class ForcegroundService extends Service{
    private Broadcast broadcast;
    private ReadNotiBroadcast notiBroadCast;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        Log.d("CalendarModule", "on create" );
        //SMS
        broadcast = new Broadcast();
        IntentFilter filterSMS = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(broadcast, filterSMS);
        super.onCreate();
    }
    private boolean isNotificationServiceRunning() {
        ContentResolver contentResolver = getContentResolver();
        String enabledNotificationListeners =
                Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = getPackageName();
        return enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        broadcast = new Broadcast();
//        IntentFilter filterSMS = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
//        registerReceiver(broadcast, filterSMS);
        Log.d("CalendarModule", "onStartCommand" );
        String input = intent.getStringExtra("foregroundExtra");
        if(input.equals("START_SERVICE")){
            boolean isNotificationServiceRunning = isNotificationServiceRunning();
            if(!isNotificationServiceRunning){
                Intent intentSetting = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS" ) ;
                startActivity(intentSetting);
            }
            createNotificationChannel();
            Intent notificationIntent = new Intent(this, CalendarModule.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("App's running in foreground")
                    .setContentText("App is reading zalo notification")
                    .setSmallIcon(R.drawable.rn_edit_text_material)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            startForeground(1, notification);
            new Timer().scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    Log.i("interval", "Interval in foreground service");
                }
            },0,5000);
        } else if (input.equals("STOP_SERVICE")) {
            Log.i("stop service", "Received Stop Foreground Intent");
            //your end servce code
            stopForeground(true);
            stopSelf();
//            stopSelfResult(startId);
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        Log.d("CalendarModule", "createNotificationChannel" );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel serviceChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "Foreground Service Channel",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(serviceChannel);
            }
        }

}
