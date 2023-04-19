package com.example.mycontentpages.attractionInfo;

import java.io.Serializable;

public class Attraction implements Serializable {

    private String mName;
    private String mDescription;

    private String picURL;

    public Attraction(String picURL, String name, String description) {
        this.picURL=picURL;
        mName = name;
        mDescription=description;
    }

    public String getPicURL() {
        return picURL;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }
}
