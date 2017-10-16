package com.example.cleeg.scoopreporter.models;

public class Organization {

    private String mEmail;
    private String mName;
    private String mUsername;

    public Organization() {}

    public Organization(String email, String name, String username) {
        mEmail = email;
        mName = name;
        mUsername = username;
    }

    public String getEmail(){ return mEmail; }
    public String getName() { return mName; }
    public String getUsername() { return mUsername; }

}
