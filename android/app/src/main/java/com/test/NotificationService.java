package com.test;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context ;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.notification.NotificationListenerService ;
import android.service.notification.StatusBarNotification ;
import android.util.Log ;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationService extends NotificationListenerService {
    Context context;

    @Override

    public void onCreate() {
        Log.i("interval", "noti service log.");

        super.onCreate();
        context = getApplicationContext();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("CalendarModule", "onStartCommand notification service" );
        return START_NOT_STICKY;
    }
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {
        String pack = sbn.getPackageName();
        String ticker ="";
        if(sbn.getNotification().tickerText !=null) {
            ticker = sbn.getNotification().tickerText.toString();
        }
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
//        int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
//        Bitmap id = sbn.getNotification().largeIcon;


        Log.i("Package",pack);
        Log.i("Ticker",ticker);
        Log.i("Title",title);
        Log.i("Text",text);
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("param1", "value1");
//        queryParams.put("param2", "value2");
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("appName",pack );
//        queryParams.put("sender", title);
//        queryParams.put("content", text);
        RequestBody formBody = new FormBody.Builder()
                .add("appName", pack)
                .add("sender", title)
                .add("content", text)
                .build();
        Request request = new Request.Builder()
                .url("https://64098fd06ecd4f9e18b44632.mockapi.io/api/notificationLogs")
                .post(formBody)
                .build();
        CallApiTask myAsyncTask = new CallApiTask();
        try {
            myAsyncTask.execute(formBody);
            // Do something with the response.
        } catch (Exception e) {
            e.printStackTrace();
        }

//        OkHttpClient client = new OkHttpClient();
//
//        RequestBody formBody = new FormBody.Builder()
//                .add("appName", pack)
//                .add("sender", title)
//                .add("content", text)
//                .build();
//        Request request = new Request.Builder()
//                .url("https://64098fd06ecd4f9e18b44632.mockapi.io/api/notificationLogs")
//                .post(formBody)
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//
//            // Do something with the response.
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        OkHttpClient client = new OkHttpClient();


//
//        String post("abc", "abc") throws IOException {
//            RequestBody body = RequestBody.create(json, JSON);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();
//            try (Response response = client.newCall(request).execute()) {
//                return response.body().string();
//            }
//        }
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.stackexchange.com")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        performPostCall("https://64098fd06ecd4f9e18b44632.mockapi.io/api/notificationLogs", queryParams);

       ///post api
//        URL url = null;
//        try {
//            url = new URL("https://64098fd06ecd4f9e18b44632.mockapi.io/api/notificationLogs");
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//
//        HttpURLConnection client = null;
//        try {
//            client = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            client.setRequestMethod("POST");
//        } catch (ProtocolException e) {
//            throw new RuntimeException(e);
//        }
//        client.setRequestProperty("appName",pack);
//        client.setDoOutput(true);
//        OutputStream outputPost = null;
//        try {
//            outputPost = new BufferedOutputStream(client.getOutputStream());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
////        writeStream(outputPost);
//        try {
//            outputPost.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            outputPost.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        HttpURLConnection client = (HttpURLConnection) url.openConnection();

//        cityMap.put(new Key(4, "NP"), "Newport");

//        sendMessage(text,pack, title);
//        HttpClient httpclient = HttpClients.createDefault();
//        HttpPost httppost = new HttpPost("http://www.a-domain.example/foo/");
//        URL url = new URL("https://www.example.com/login");
//        URLConnection con = url.openConnection();
//        HttpURLConnection http = (HttpURLConnection)con;
//        http.setRequestMethod("POST"); // PUT is another valid option
//        http.setDoOutput(true);
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
    public void performPostCall(String requestURL,
                                   Map<String, String> queryParams) {
        try {
            // The URL endpoint you want to call
            URL url;
            url = new URL(requestURL);

            URL obj = new URL(url + "?" + getParamsString(queryParams));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method to POST
            con.setRequestMethod("POST");

            // Set the Content-Type header to indicate that the request body is JSON
            con.setRequestProperty("Content-Type", "application/json");

            // Enable output for the request so that you can write the JSON body
            con.setDoOutput(true);

            // Write the JSON body to the request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//            wr.writeBytes(jsonBody);
            wr.flush();
            wr.close();

            // Read the response from the server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response from the server
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

}
    private static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");

    }
}