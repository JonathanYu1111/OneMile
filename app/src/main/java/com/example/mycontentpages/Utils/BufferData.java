package com.example.mycontentpages.Utils;

import java.util.ArrayList;
import java.util.List;

public class BufferData {
    private static List<String> InRangeIDs=new ArrayList<>();
    private static String selectedPlaceType="";
    public static List<String> getInRangeIDs() {
        return InRangeIDs;
    }
    public static void setInRangeIDs(List<String> inRangeIDs) {
        InRangeIDs = inRangeIDs;
    }

    public static String getSelectedPlaceType() {
        return selectedPlaceType;
    }

    public static void setSelectedPlaceType(String selectedPlaceType) {
        BufferData.selectedPlaceType = selectedPlaceType;
    }
}
