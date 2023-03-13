package com.test;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context ;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService ;
import android.service.notification.StatusBarNotification ;
import android.util.Log ;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class NotificationService extends NotificationListenerService {
    Context context;
    private ReadNotiBroadcast broadcastNoti;
    private Broadcast broadcastSMS;


    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final String ZALO_PAKAGE = "com.zing.zalo";
    public static final String SMS_PAKAGE = "com.android.messaging";
    public static final String MESSENGER_PAKAGE = "com.facebook.orca";
    private static boolean isRunningService = false;


    @Override

    public void onCreate() {


        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Log.i("noti service", "Interval in notification service. " + isRunningService);
            }
        },0,1000);
        boolean isNotificationServiceRunning = isNotificationServiceRunning();
        if(!isNotificationServiceRunning){
            this.isRunningService = false;
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS" ) ;
            startActivity(intent);
        }
        super.onCreate();
        context = getApplicationContext();

    }
    public void setIsRunningService(boolean value){
        this.isRunningService = value;
    }
    public boolean getIsRunningService(){
        return this.isRunningService;
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
//        Log.d("noti service", "onStartCommand" );
        String input = intent.getStringExtra("foregroundExtra");
        if(input.equals("START_SERVICE")){
//            Log.i("noti service", "noti service log.");

            broadcastNoti = new ReadNotiBroadcast();
            IntentFilter filterNoti = new IntentFilter("READ_NOTIFICATION_ACTION");

            registerReceiver(broadcastNoti, filterNoti);
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
            startForeground(1902, notification);
        } else if (input.equals("STOP_SERVICE")) {
            unregisterReceiver(broadcastNoti);
//            Log.i("noti service", "Received Stop Foreground Intent");
            //your end servce code
            stopForeground(true);
            stopSelfResult(startId);
            stopService(intent);
            stopSelf();
        }
        return START_NOT_STICKY;
    }
    private void createNotificationChannel() {
//        Log.d("CalendarModule", "createNotificationChannel" );
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

    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {

        String pack = sbn.getPackageName();
        String ticker ="";
        if(sbn.getNotification().tickerText !=null) {
            ticker = sbn.getNotification().tickerText.toString();
        }
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getCharSequence("android.title").toString();
        String text = extras.getCharSequence("android.text").toString();
//        int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
//        Bitmap id = sbn.getNotification().largeIcon;
        //broadcast

        if(ticker.equals("Zalo") && ticker!=""){
            Log.i("Zalo noti do not send", ticker);

        }else if(pack.equals("com.zing.zalo")&& title!=""){
            //kiểm tra kí tự ( trong ticker
            //Ví dụ "Toan (2 tin nhắn)" thì  chỉ lấy "Toan"
            int index = title.indexOf('(');
            if(index > 1){
                String tmp = title.substring(0,index-1);
                title = tmp;
            }
            Intent newIntent = new Intent();
            newIntent.setAction("READ_NOTIFICATION_ACTION");
            newIntent.putExtra("appName", pack);
            newIntent.putExtra("sender", title);
            newIntent.putExtra("content", text);
            sendBroadcast(newIntent);
        }else{
            Log.i("From another app", pack);
        }
//        if(isRunningService){
            Log.i("Package",pack);
            Log.i("Ticker",ticker);
            Log.i("Title",title);
            Log.i("Text",text);
            Log.i("space","-----");

//            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//        }else{
////            Toast.makeText(this, "Service is off", Toast.LENGTH_LONG).show();
//        }
    }
    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("noti service","Notification Removed");
    }
    @Override
    public void onDestroy() {
        Log.i("noti service","destroy");
        unregisterReceiver(broadcastNoti);
        super.onDestroy();
    }
//    @Override
//    public void onDestroy() {
//        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
////        Log.d(TAG, "onDestroy");
////        player.stop();
//    }

//    @Override
//    public void stopForeground(boolean removeNotification) {
//        super.stopForeground(removeNotification);
//    }

}