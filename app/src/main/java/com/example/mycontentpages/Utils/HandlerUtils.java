package com.example.mycontentpages.Utils;

import android.util.Log;

import com.example.mycontentpages.MainActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HandlerUtils implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // get token
        String token = SpUtils.getString(MainActivity.getContext(), "token");

        if (token.isEmpty()) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        }else{
            Request originalRequest = chain.request();
            Request updateRequest = originalRequest.newBuilder().header("Authorization", token).build();
            return chain.proceed(updateRequest);
        }
    }
}