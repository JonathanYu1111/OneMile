package com.example.mycontentpages.Utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp {
    private static final OkHttpClient httpClient = new OkHttpClient();


    public static String sendGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
            String responseBody = response.body().string();
            System.out.println(responseBody);
            return "111";
        }

    }

    public static String sendPostRequest(String url, String jsonBody) throws Exception{
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Log.e("TAG-ONE", jsonBody + "post");

        Response response = httpClient.newCall(request).execute();
        if (response==null){Log.i("response","response is null");}
        String resData = response.body().string();
        Log.e("TAG-ONE","2222222222 "+resData);

        return resData;

//        try {Response response = httpClient.newCall(request).execute();
//            Log.e("TAG-ONE", response.body().string() + "post");
//            return response.body().string();
//        }catch (Exception e){
//            Log.e("TAG-ONE","2222222222 "+e.getMessage());
//
//        }
//        return "null";
    }

}