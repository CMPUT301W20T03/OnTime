package com.example.ontime.models;

import android.net.Uri;

import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.mobsandgeeks.saripaar.annotation.Url;

public class PlaceInfo {
    private  String name;
    private String address;
    private String phoneNumber;
    private String id;
    private Url websiteUrl;
    private LatLng latLng;
    private double rating;
    private String attributions;

    public PlaceInfo(String name, String address, String phoneNumber, String id, Url websiteUrl, LatLng latLng, float rating, String attributions) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.websiteUrl = websiteUrl;
        this.latLng = latLng;
        this.rating = rating;
        this.attributions = attributions;
    }
    public PlaceInfo(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Url getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(Url websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public LatLng getLatLng() {
        return latLng;
    }



    public double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getAttributions() {
        return attributions;
    }

    public void setAttributions(String attributions) {
        this.attributions = attributions;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", id='" + id + '\'' +
                ", websiteUrl=" + websiteUrl +
                ", latLng=" + latLng +
                ", rating=" + rating +
                ", attributions='" + attributions + '\'' +
                '}';
    }

    public void setLatlng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUrl = websiteUrl;
    }
}
