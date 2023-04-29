package com.example.mycontentpages.Utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.mycontentpages.data.Place;
import java.io.IOException;
import java.util.List;
import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Data
public class DataContainer {

    // TODO: 28/04/2023  需要初始化空arraylist，防止为空是没有初始值，程序终止
    private static List<Place> placeContainer;

   private static final OkHttpClient okHttpClient=new OkHttpClient();
        public static void initialize() throws IOException {
            Log.i("DATA","data initializing");
            initPlaces();

        }

        public static void initPlaces() throws IOException {
//            String s = OkHttp.sendGetRequest("http://172.20.10.3:8090/place/get");
//            Log.e("body",s);
//            List<Place> places = JSON.parseArray(s, Place.class);
//            placeContainer=places;


            Request request = new Request.Builder()
                  // .url("http://172.20.10.3:8090/place/get")
                   .url("http://10.183.135.5:8090/place/get")
                    .build();
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()==false)
            { Request request2 = new Request.Builder()
                   //    .url("http://172.20.10.3:8090/place/get")
                    .url("http://10.183.135.5:8090/place/get")
                    .build();
             response=okHttpClient.newCall(request2).execute();
            }
                ResponseBody body = response.body();
                String jsonStr = body.string();
                Log.i("body", jsonStr);
                List<Place> places = JSON.parseArray(jsonStr, Place.class);
                placeContainer = places;



        }
        public static  List<Place> getPlaceContainer(){
            return placeContainer;
        }

}










