package com.test;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;

public class MainActivity extends ReactActivity {
//  private static final int NOTIFICATION_PERMISSION_CODE = 123;
//  @Override
// public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    Log.d("CalendarModule", "on create main activity");
//      requestNotificationPermission();
//      boolean isNotificationServiceRunning = isNotificationServiceRunning();
//      if(!isNotificationServiceRunning){
//          requestNotificationPermission();
//
////          Intent paIntent = new Intent(this, permission_activity.class);
////          paIntent.putExtra("inputExtra", "Foreground Service Example in Android");
////          startService(paIntent);
////            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
//      }

//  }
//    private void requestNotificationPermission() {
////        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED)
////            return;
//
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {
//
//        }
//
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, NOTIFICATION_PERMISSION_CODE );
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        // Checking the request code of our request
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
//
//            // If permission is granted
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Displaying a toast
//                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
//            } else {
//                // Displaying another toast if permission is not granted
//                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//    private boolean isNotificationServiceRunning() {
//        ContentResolver contentResolver = getContentResolver();
//        String enabledNotificationListeners =
//                Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
//        String packageName = getPackageName();
//        return enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName);
//    }
  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "Test";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
//    requestNotificationPermission();
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled(), // fabricEnabled
        // If you opted-in for the New Architecture, we enable Concurrent React (i.e. React 18).
        DefaultNewArchitectureEntryPoint.getConcurrentReactEnabled() // concurrentRootEnabled
        );
  }
}
