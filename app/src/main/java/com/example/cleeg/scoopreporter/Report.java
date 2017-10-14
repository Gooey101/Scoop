package com.example.cleeg.scoopreporter;

public class Report {

    private String mTitle;
    private String mInformation;
    private Integer mDate;
    private String mReporterKey;
    private String mOrganization;

    public Report() {}

    public Report(String title, String information, Integer date, String reporterKey,
                  String organization) {
        mTitle = title;
        mInformation = information;
        mDate = date;
        mReporterKey = reporterKey;
        mOrganization = organization;
    }

    public String getTitle() { return mTitle; }
    public String getInformation() { return mInformation; }
    public Integer getDate(){ return mDate; }
    public String getReporterKey() { return mReporterKey; }
    public String getOrganization() { return mOrganization; }
}
