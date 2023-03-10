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
import android.graphics.Bitmap;
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
import java.util.Timer;
import java.util.TimerTask;


public class ForcegroundService extends NotificationListenerService{
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
        //App notification
//        new NotificationService().setListener( this ) ;
        boolean isNotificationServiceRunning = isNotificationServiceRunning();
        if(!isNotificationServiceRunning){
//            Intent paIntent = new Intent(this, permission_activity.class);
//            paIntent.putExtra("inputExtra", "Foreground Service Example in Android");
//            startService(paIntent);
//            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS" ) ;
            startActivity(intent);
        }

//        startForegroundService(intent);
//        startService(intent);
        notiBroadCast = new ReadNotiBroadcast();
        IntentFilter filterNoti = new IntentFilter("android.service.notification.NotificationListenerService");
        registerReceiver(notiBroadCast, filterNoti);
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
        Log.d("CalendarModule", "onStartCommand" );
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, CalendarModule.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Toan elnino")
                .setSmallIcon(R.drawable.rn_edit_text_material)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        startForeground(1, notification);
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Log.i("interval", "This function is called every 5 seconds.");
            }
        },0,5000);

//        Intent notiIntent = new Intent(this, NotificationService.class);
//        notiIntent.putExtra("inputExtra", "Foreground Service Example in Android");
//        startService(notiIntent);


        //do heavy work on a background thread
        // Gets an instance of the NotificationManager service
//        NotificationManager mNotificationManager =
//                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1, notification);

        //stopSelf();

        return START_NOT_STICKY;
    }
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d("noti","**********  onNotificationPosted");
//        String pack = sbn.getPackageName();
//        String ticker ="";
//        if(sbn.getNotification().tickerText !=null) {
//            ticker = sbn.getNotification().tickerText.toString();
//        }
//        Bundle extras = sbn.getNotification().extras;
//        String title = extras.getString("android.title");
//        String text = extras.getCharSequence("android.text").toString();
//        int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
//        Bitmap id = sbn.getNotification().largeIcon;
//
//
//        Log.i("Package",pack);
//        Log.i("Ticker",ticker);
//        Log.i("Title",title);
//        Log.i("Text",text);
//
//        Intent msgrcv = new Intent("Msg");
//        msgrcv.putExtra("package", pack);
//        msgrcv.putExtra("ticker", ticker);
//        msgrcv.putExtra("title", title);
//        msgrcv.putExtra("text", text);
//        if(id != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            id.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] byteArray = stream.toByteArray();
//            msgrcv.putExtra("icon",byteArray);
//        }
//        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
    }

    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");

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
