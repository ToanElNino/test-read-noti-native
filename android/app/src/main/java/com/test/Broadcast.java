package com.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Broadcast extends BroadcastReceiver {
    private static final String TAG =
            Broadcast.class.getSimpleName();
    public static final String pdu_type = "pdus";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("interval", "sms boardcast log.");

// Get the SMS message.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM =
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    }
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // Build the message to show.
                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                strMessage += " :" + msgs[i].getMessageBody() + "\n";
                // Log and display the SMS message.
                Log.d(TAG, "onReceive: " + strMessage);
                Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
            }
        }


        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Log.i("interval", "boardcast log.");
            }
        },0,5000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(Broadcast.class.getSimpleName(), "Air Plane mode");
        }
    }
}
