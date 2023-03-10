package com.test; // replace com.your-app-name with your app’s name

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

class CalendarModule extends ReactContextBaseJavaModule {
//   private Context context;
   ForcegroundService test = new ForcegroundService();
   CalendarModule(ReactApplicationContext context) {
      super(context);
   }

   // add to CalendarModule.java
   @Override
   public String getName() {
      return "CalendarModule";
   }

   @ReactMethod(isBlockingSynchronousMethod = true)
   public void createCalendarEvent(String name, String location) {
      Log.d("CalendarModule", "Create event called with name: " + name
      + " and location: " + location);
      Intent serviceIntent = new Intent(this.getReactApplicationContext(), ForcegroundService.class);
      serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");

//      Intent notiIntent = new Intent(this.getReactApplicationContext(), NotificationService.class);
//      notiIntent.putExtra("inputExtra", "Foreground Service Example in Android");

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         Log.d("check version", "test force ground service" );
         ContextCompat.startForegroundService(this.getReactApplicationContext(), serviceIntent);
      }
   }
   @ReactMethod(isBlockingSynchronousMethod = true)
   public void startInterValNotification(){
      Log.d("check version", "start to create interval notifications" );
      Intent intervalServiceIntent = new Intent(this.getReactApplicationContext(), IntervalNotiService.class);
      intervalServiceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         Log.d("check version", "test force ground service" );
         ContextCompat.startForegroundService(this.getReactApplicationContext(), intervalServiceIntent);
      }
   }
//   public void startService() {
////      Log.d("CalendarModule", "test force ground service" );
////      Intent serviceIntent = new Intent(this.getReactApplicationContext(), ForcegroundService.class);
////      serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
////      ContextCompat.startForegroundService(this.getReactApplicationContext(), serviceIntent);
//      Intent serviceIntent = new Intent(this.getReactApplicationContext(), ForcegroundService.class);
//      serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
////      ContextCompat.startForegroundService(this.getReactApplicationContext(), serviceIntent);
////      Intent serviceIntent = new Intent(this.getReactApplicationContext(), ForegroundService.class);
////      test.onCreate();
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//         Log.d("check version", "start force ground service" );
//         ContextCompat.startForegroundService(this.getReactApplicationContext(), serviceIntent);
//      }
//   }
//   public void stopService1() {
//      Intent serviceIntent = new Intent(this.getReactApplicationContext(), ForcegroundService.class);
////      serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
////      ContextCompat.startForegroundService(this.getReactApplicationContext(), serviceIntent);
////      Intent serviceIntent = new Intent(this.getReactApplicationContext(), ForegroundService.class);
////      test.onCreate();
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//         Log.d("check version", "stop force ground service" );
////         @Override
////         stopService();
////         ContextCompat.startForegroundService(this.getReactApplicationContext(), serviceIntent);
//
//      }
//   }
//   public void stopService() {
//      Intent serviceIntent = new Intent(this.getReactApplicationContext(), ForcegroundService.class);
//      stopService(serviceIntent);
//   }

}