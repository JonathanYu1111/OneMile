package com.example.mycontentpages.Utils;

import android.provider.ContactsContract;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.mycontentpages.data.Place;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp {
    public static final String Base_url = "http://172.20.10.3:8090";
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

    public static void initPlaces() throws IOException {
        String s = OkHttp.sendGetRequest("/place/get");
        Log.e("body",s);
        List<Place> places = JSON.parseArray(s, Place.class);
       DataContainer.setPlaceContainer(places);
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