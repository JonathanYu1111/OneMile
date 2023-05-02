package com.example.mycontentpages.Utils;

import static com.example.mycontentpages.Utils.OkHttp.initPlaces;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.mycontentpages.data.Place;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
@Data
public class DataContainer {

    // TODO: 28/04/2023  需要初始化空arraylist，防止为空是没有初始值，程序终止
    private static List<Place> placeContainer=new ArrayList<>();
    private static Map<String,Place> idAndPlace=new HashMap<>();


        public static void initialize() throws IOException {
            Log.i("DATA","data initializing");
           OkHttp.initPlaces();
        }


        public static  List<Place> getPlaceContainer(){
            return placeContainer;
        }
        public static void setPlaceContainer(List<Place> places){
            placeContainer =places;
        }

    public static Map<String, Place> getIDandPlace() {
        return idAndPlace;
    }

    public static void setIDandPlace(Map<String, Place> idAndPlace) {
        DataContainer.idAndPlace = idAndPlace;
    }

    public static Place getByName(String placeName){
            for(Place place: placeContainer){
                if(placeName.equals(place.getName())){
                return place;}
            }
            return null;
    }

}










