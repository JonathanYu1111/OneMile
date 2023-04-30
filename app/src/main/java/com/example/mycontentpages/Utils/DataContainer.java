package com.example.mycontentpages.Utils;

import static com.example.mycontentpages.Utils.OkHttp.initPlaces;

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





        public static  List<Place> getPlaceContainer(){
            return placeContainer;
        }
        public static void setPlaceContainer(List<Place> places){
            placeContainer =places;
        }

}










