package com.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NotiPermissionAcitivity extends AppCompatActivity {
    private static final int NOTIFICATION_PERMISSION_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_permission);
        Button ok_button = findViewById(R.id.button_enable);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish(); //Kết thúc Activity (sẽ quay trở về Activiy trước nó)
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
                requestNotificationPermission();
//                Toast.makeText(view.getContext(), "open", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void requestNotificationPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED)
//            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, NOTIFICATION_PERMISSION_CODE );

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // Checking the request code of our request
        boolean isNotificationServiceRunning = isNotificationServiceRunning();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {

            // If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && isNotificationServiceRunning) {
                // Displaying a toast
                Toast.makeText(this, "You've enabled Notification Access Permission for this application", Toast.LENGTH_LONG).show();
                Intent serviceIntent = new Intent(this, NotificationService.class);
                // serviceIntent.putExtra("foregroundExtra", "START_SERVICE");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    finish();
//                    Log.d("check version", "test force ground service" );
                    // ContextCompat.startForegroundService(this, serviceIntent);
                }
            } else {
                // Displaying another toast if permission is not granted
                Toast.makeText(this, "You need to allow Notification Access Permission to use app", Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean isNotificationServiceRunning() {
        ContentResolver contentResolver = getContentResolver();
        String enabledNotificationListeners =
                Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = getPackageName();
        return enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName);
    }
}