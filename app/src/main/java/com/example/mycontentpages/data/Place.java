package com.example.mycontentpages.data;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Place implements Serializable {
    private static final long serialVersionUID = 1L;

    private String googlePlaceId;
    private String name;
    private String type;
    private Double latitude;

    private Double longitude;

    private String streetNumber;

    private String route;

    private String locality;

    private String administrativeAreaLevel2;


    private String administrativeAreaLevel1;

    private String country;


    private String postalCode;

    private String openingHours;
    private String photos;

    private Integer rating;

    private Integer createTime;


    private Integer modifyTime;

    private String icon;

    private Integer googleRating;

    private String description;

    private String url;

    private String website;


    private String openTime;


    private String closeTime;

    private String phone;

    private String fullAddress;


    //特别注意此无参构造器不可改动，否则无法完成json对象封装
public Place(){
//不可改动不可改动不可改动，重事说三
}
    public Place(String picURL, String name, String description) {
        this.url=picURL;
        this.name = name;
        this.description=description;
    }



    public void setGooglePlaceId(String googlePlaceId) {
        this.googlePlaceId = googlePlaceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setAdministrativeAreaLevel2(String administrativeAreaLevel2) {
        this.administrativeAreaLevel2 = administrativeAreaLevel2;
    }

    public void setAdministrativeAreaLevel1(String administrativeAreaLevel1) {
        this.administrativeAreaLevel1 = administrativeAreaLevel1;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setGoogleRating(Integer googleRating) {
        this.googleRating = googleRating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getGooglePlaceId() {
        return googlePlaceId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getRoute() {
        return route;
    }

    public String getLocality() {
        return locality;
    }

    public String getAdministrativeAreaLevel2() {
        return administrativeAreaLevel2;
    }

    public String getAdministrativeAreaLevel1() {
        return administrativeAreaLevel1;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getPhotos() {
        return photos;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public Integer getModifyTime() {
        return modifyTime;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getGoogleRating() {
        return googleRating;
    }

    public String getDescription() {
        return description;
    }


    //不要用这个方法，因为返回的是未处理的后台数据，形如： [https://xxxxxxx,https://aaaaaa,https://bbbb,https://ccccc]
    public String getUrl() {
        return url;
    }

    public List<String> getUrlList(){
        String pureUrl=this.url.substring(1,this.url.length()-1);
        String[] arr=pureUrl.split(",");
        List<String> urls= Arrays.asList(arr);
        return urls;
    }
    public String getUrlWithIndex(int index){
       String pureUrl=this.url.substring(1,this.url.length()-1);
       String[] arr=pureUrl.split(",");
        List<String> urls= Arrays.asList(arr);
       return urls.get(index);
    }
    public String getFirstPhoto(){
//        String pureUrl=this.photos.substring(1,this.photos.length()-1);
//        String[] arr=pureUrl.split(",");
//        List<String> photos= Arrays.asList(arr);
       // return photos.get(0);
         String theUrl="url is blank";
         ArrayList<String> urls=new ArrayList<>();
       urls.add("https://images.unsplash.com/photo-1681312407157-19ec16888a6f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEzfGJEbzQ4Y1Vod25ZfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=600&q=60");
        urls.add("https://images.unsplash.com/photo-1680095297939-5f69d7f139e9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8YkRvNDhjVWh3bll8fGVufDB8fHx8&auto=format&fit=crop&w=600&q=60");
       urls.add( "https://images.unsplash.com/photo-1675111575738-80a06bbdb643?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDI1fGJEbzQ4Y1Vod25ZfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=600&q=60");
        Random rand = new Random();
        int random = rand.nextInt(3);
        switch (random){
            case 0: theUrl=urls.get(0);
                    break;
            case 1:theUrl=urls.get(1);
                    break;
            case 2:theUrl=urls.get(2);
                    break;
        }
        return theUrl;
}

    public String getWebsite() {
        return website;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    @Override
    public String toString() {
        return "Place{" +
                "googlePlaceId='" + googlePlaceId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", streetNumber='" + streetNumber + '\'' +
                ", route='" + route + '\'' +
                ", locality='" + locality + '\'' +
                ", administrativeAreaLevel2='" + administrativeAreaLevel2 + '\'' +
                ", administrativeAreaLevel1='" + administrativeAreaLevel1 + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", openingHours='" + openingHours + '\'' +
                ", photos='" + photos + '\'' +
                ", rating=" + rating +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", icon='" + icon + '\'' +
                ", googleRating=" + googleRating +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", website='" + website + '\'' +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", phone='" + phone + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }
}
