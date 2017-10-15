package com.example.cleeg.scoopreporter.models;

public class Organization {

    private String mEmail;
    private String mName;

    public Organization() {}

    public Organization(String email, String name) {
        mEmail = email;
        mName = name;
    }

    public String getEmail(){ return mEmail; }
    public String getName() { return mName; }

}
