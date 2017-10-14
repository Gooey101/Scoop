package com.example.cleeg.scoopreporter;

public class Report {

    private String mTitle;
    private String mInformation;
    private Integer mDate;
    private Reporter mReporter;
    private String mOrganization;

    public Report() {}

    public Report(String title, String information, Integer date, Reporter reporter,
                  String organization) {
        mTitle = title;
        mInformation = information;
        mDate = date;
        mReporter = reporter;
        mOrganization = organization;
    }

    public String getTitle() { return mTitle; }
    public String getInformation() { return mInformation; }
    public Integer getDate(){ return mDate; }
    public Reporter getReporter() { return mReporter; }
    public String getOrganization() { return mOrganization; }
}
