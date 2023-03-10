package com.test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class IntervalNotiService extends Service {
    String CHANNEL_ID = null;
    String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";
    @Override
    public void onCreate() {
        Log.d("CalendarModule", "on create" );
        super.onCreate();
        String sb = "";
        for (int i = 0; i < 5; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb+=AlphaNumericString
                    .charAt(index);
        }
        this.CHANNEL_ID = sb;
    }
    public IntervalNotiService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("CalendarModule", "onStartCommand interval" );
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, CalendarModule.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, this.CHANNEL_ID )
                .setContentTitle("Random app")
                .setContentText("Random notification")
                .setSmallIcon(R.drawable.rn_edit_text_material)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        startForeground(2, notification);


        //do heavy work on a background thread
        // Gets an instance of the NotificationManager service
//        NotificationManager mNotificationManager =
//                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1, notification);

        //stopSelf();

        return START_NOT_STICKY;
    }
    private void createNotificationChannel() {
        Log.d("CalendarModule", "createNotificationChannel" );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    this.CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}