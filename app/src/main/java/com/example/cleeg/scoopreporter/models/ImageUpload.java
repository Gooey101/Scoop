package com.example.cleeg.scoopreporter.models;

public class ImageUpload {

    private String mPath;
    private String mUrl;

    public ImageUpload() {}

    public ImageUpload(String path, String url) {
        mPath = path;
        mUrl = url;
    }

    public String getPath(){ return mPath; }
    public String getUrl() { return mUrl; }

}
