package com.test; // replace com.your-app-name with your app’s name

import com.facebook.hermes.intl.Constants;
import com.facebook.react.bridge.Callback;
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
   NotificationService test1 = new NotificationService();

   CalendarModule(ReactApplicationContext context) {
      super(context);
   }

   // add to CalendarModule.java
   @Override
   public String getName() {
      return "CalendarModule";
   }

   @ReactMethod(isBlockingSynchronousMethod = true)
   public void startFeature() {
//      Intent serviceIntent = new Intent(this.getReactApplicationContext(), ForcegroundService.class);
//      serviceIntent.putExtra("foregroundExtra", "START_SERVICE");
      Intent serviceIntent1 = new Intent(this.getReactApplicationContext(), NotificationService.class);
      serviceIntent1.putExtra("foregroundExtra", "START_SERVICE");
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         Log.d("check version", "test force ground service" );
//         ContextCompat.startForegroundService(this.getReactApplicationContext(), serviceIntent);
         ContextCompat.startForegroundService(this.getReactApplicationContext(), serviceIntent1);

//         getReactApplicationContext().stopService(serviceIntent1);
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
   @ReactMethod(isBlockingSynchronousMethod = true)
   public void stopFeature() {
      Intent stopIntent = new Intent(this.getReactApplicationContext(), NotificationService.class);
      stopIntent.putExtra("foregroundExtra", "STOP_SERVICE");
      ContextCompat.startForegroundService(this.getReactApplicationContext(), stopIntent);
//      getReactApplicationContext().stopService(stopIntent);

}
   @ReactMethod(isBlockingSynchronousMethod = true)
   public void TurnOnService() {
      startFeature();
     test1.setIsRunningService(true);
   }
   @ReactMethod(isBlockingSynchronousMethod = true)
   public void TurnOffService() {
      stopFeature();
      test1.setIsRunningService(false);
   }
   @ReactMethod(isBlockingSynchronousMethod = true)
   public void getValueFromJavaLayer(int param, Callback callback) {
      try {
         // do something
         callback.invoke(test1.getIsRunningService()); // Invoke the callback here
      } catch (Exception e) {
         // exception code here
      }
   }
}
