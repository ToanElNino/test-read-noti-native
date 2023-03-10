package com.test;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallApiTask extends AsyncTask<RequestBody,Void, Response> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    @Override
    protected Response doInBackground(RequestBody... requestBodies) {
        OkHttpClient client = new OkHttpClient();

//        RequestBody formBody = new FormBody.Builder()
//                .add("appName", "ihih")
//                .add("sender", "oke")
//                .add("content", "Tòn")
//                .build();
        Request request = new Request.Builder()
                .url("https://64098fd06ecd4f9e18b44632.mockapi.io/api/notificationLogs")
                .post(requestBodies[0])
                .build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try {
//            Response response = client.newCall(request).execute();
////            return response;
//
//            // Do something with the response.
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        return new Response();
    }
}