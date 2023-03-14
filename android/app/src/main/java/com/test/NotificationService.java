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

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class NotificationService extends NotificationListenerService {
    Context context;
    private ReadNotiBroadcast broadcastNoti;


    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final String ZALO_PAKAGE = "com.zing.zalo";
    public static final String SMS_PAKAGE = "com.android.messaging";
    public static final String MESSENGER_PAKAGE = "com.facebook.orca";
    public static final String GROUP_MESSENGER_PREFIX = "Nhóm:";
    public static final String GROUP_PHOTO_MESSENGER_SUFFIX = "đã gửi ảnh";
    public static final String GROUP_VIDEO_MESSENGER_SUFFIX = "đã gửi video";
    public static final String GROUP_FILE_MESSENGER_SUFFIX = "đã gửi tập tin";
    public static final String GROUP_STICKER_MESSENGER_SUFFIX = "đã gửi hình động";
    public static final String GROUP_TAG_MESSENGER_SUFFIX = "nhắc đến bạn:";
    public static final String GROUP_GIF_MESSENGER_SUFFIX = "đã gửi GIF";
    public static final String GROUP_DRAW_MESSENGER_SUFFIX = "đã gửi hình vẽ";
    public static final String GROUP_LOCATION_MESSENGER_SUFFIX = "đã gửi vị trí";
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
//            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS" ) ;
//            startActivity(intent);
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
        Log.i("title before: ", title);
        Log.i("text before: ", text);
        Log.i("ticker before: ", ticker);

        //thông báo hệ thống của Zalo
        if(ticker.equals("Zalo") && ticker!=""){
            Log.i("Zalo noti do not send", ticker);
        //thông báo tin nhắn của Zalo
        }else if(pack.equals(ZALO_PAKAGE)&& title!=""){
            Date currentTime = Calendar.getInstance().getTime();
            //kiểm tra xem tin nhắn từ nhóm hay cá nhân
            int indexGroup = title.indexOf(GROUP_MESSENGER_PREFIX);
            //xử lý group chat
            if (indexGroup != -1){
                handleGroupMessage(title,text,ticker,pack,currentTime.toString());
                //xử lý tin nhắn 1-1
            }else{
                handle2P2Message(title,text,ticker,pack,currentTime.toString());
            }
            //thông báo từ app khác
        }else{
            Log.i("From another app", pack);
        }
            Log.i("Package",pack);
            Log.i("Ticker",ticker);
            Log.i("Title",title);
            Log.i("Text",text);
            Log.i("space","-----");
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
    public void handleGroupMessage(String title, String text, String ticker, String pack, String time){
        Log.i("Tin nhắn nhóm", title);
        String groupName = title.substring(6);
        Log.i("groupName before: ", groupName);

        //check (
        int index = groupName.indexOf('(');
        if(index > 1){
            groupName = groupName.substring(0,index-1);
        }
        //tìm tên người gửi
        String sender = "";
        String content = "";
        int indexDotName = text.indexOf(':');

        //trong text có : => gửi tin nhắn text hoặc tag
        if(indexDotName!=-1){
            sender =  text.substring(0,indexDotName);
            content =  text.substring(indexDotName+2);
            //được người trong nhóm tag đến chính mình

            int indexTag = text.indexOf(GROUP_TAG_MESSENGER_SUFFIX);
            Log.i("tag group", Integer.toString(indexTag));
            if(indexTag > 4){
                sender = sender.substring(4, indexTag-1);
                content = "Đã nhắc đến bạn";
            }
            //gửi ảnh, sticker, tag, file trong nhóm
        }else{
            //gửi ảnh
            int indexPhoto = text.lastIndexOf(GROUP_PHOTO_MESSENGER_SUFFIX);
            if(indexPhoto!=-1){
                sender = text.substring(0,indexPhoto-1);
                content = "Đã gửi một ảnh";
            }
            //gửi sticker
            int indexSticker = text.lastIndexOf(GROUP_STICKER_MESSENGER_SUFFIX);
            if(indexSticker!=-1){
                sender = text.substring(0,indexSticker-1);
                content = "Đã gửi một sticker";
            }
            //gửi video
            int indexVideo = text.lastIndexOf(GROUP_VIDEO_MESSENGER_SUFFIX);
            if(indexVideo!=-1){
                sender = text.substring(0,indexVideo-1);
                content = "Đã gửi một video";
            }
            //gửi file
            int indexFile = text.lastIndexOf(GROUP_FILE_MESSENGER_SUFFIX);
            if(indexFile!=-1){
                sender = text.substring(0,indexFile-1);
                content = "Đã gửi một file";
            }
            //gửi GIF
            int indexGIF = text.lastIndexOf(GROUP_GIF_MESSENGER_SUFFIX);
            if(indexGIF!=-1){
                sender = text.substring(0,indexGIF-1);
                content = "Đã gửi một GIF";
            }
            //gửi vị trí
            int indexLocation = text.lastIndexOf(GROUP_LOCATION_MESSENGER_SUFFIX);
            if(indexLocation!=-1){
                sender = text.substring(0,indexLocation-1);
                content = "Đã gửi gửi vị trí";
            }
        }
        Intent newIntent = new Intent();
        newIntent.setAction("READ_NOTIFICATION_ACTION");
        newIntent.putExtra("appName", pack);
        newIntent.putExtra("sender", sender);
        newIntent.putExtra("content", content);
        newIntent.putExtra("groupName", groupName);
        newIntent.putExtra("type", 2);
        newIntent.putExtra("time", time);
        sendBroadcast(newIntent);
        //log
        Log.i("Package",pack);
        Log.i("sender",sender);
        Log.i("content",content);
        Log.i("groupName",groupName);
        Log.i("space","-----");
    }
    public void handle2P2Message(String title, String text, String ticker,String pack, String time){
        int index = title.indexOf('(');
        if(index > 1){
            String tmp = title.substring(0,index-1);
            title = tmp;
        }
        Intent newIntent = new Intent();
        newIntent.setAction("READ_NOTIFICATION_ACTION");
        newIntent.putExtra("appName", pack);
        newIntent.putExtra("sender", title);
        newIntent.putExtra("groupName", "");
        newIntent.putExtra("content", text);
        newIntent.putExtra("type", 1);
        newIntent.putExtra("time", time);
        sendBroadcast(newIntent);
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