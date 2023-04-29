package com.example.mycontentpages.Utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp {

    public static final String Base_url = "http://10.58.212.5:8090";

    public static String sendGetRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HandlerUtils())
                .build();

        Request request = new Request.Builder()
                .url(Base_url + url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String sendPostRequest(String url, String jsonBody) throws Exception{
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        // add a interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HandlerUtils())
                .build();

        Request request = new Request.Builder()
                .url(Base_url + url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (response==null){Log.i("response","response is null");}
        String resData = response.body().string();

        return resData;
    }

}