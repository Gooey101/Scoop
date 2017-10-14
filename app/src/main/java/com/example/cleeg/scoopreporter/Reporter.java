package com.example.cleeg.scoopreporter;

public class Reporter {

    private Integer mCred;
    private String mName;
    private String mEmail;
    private Integer mPhoneNumber;

    public Reporter() {
        // Default constructor required for calls to DataSnapshot.getValue(Reporter.class)
    }

    public Integer getCred() { return mCred; }
    public String getName() { return mName; }
    public String getEmail() {return mEmail; }
    public Integer getPhoneNumber(){ return mPhoneNumber; }

}
