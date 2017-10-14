package com.example.cleeg.scoopreporter.models;

public class ImageUpload {

    public String mName;
    public String mUrl;

    public ImageUpload(String name, String url) {
        mName = name;
        mUrl = url;
    }

    public String getName() { return mName; }
    public String getUrl() { return mUrl; }
}
