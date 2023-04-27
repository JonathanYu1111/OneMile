package com.example.mycontentpages.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Place {
    private int place_id;
    private String name;
    private String type;
    private double latitude;
    private double longitude;
    private String opening_hours;
    private String photos;
    private double rating;
    private String icon;
    private double google_rating;
    private String url;
    private String description;
    private String website;
    private String full_address;


    public int getPlace_id() {
        return place_id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public String getPhotos() {
        return photos;
    }

    public double getRating() {
        return rating;
    }

    public String getIcon() {
        return icon;
    }

    public double getGoogle_rating() {
        return google_rating;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public String getFull_address() {
        return full_address;
    }
}
