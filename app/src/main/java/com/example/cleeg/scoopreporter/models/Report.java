package com.example.cleeg.scoopreporter.models;

public class Report {

    private String mTitle;
    private String mInfo;
    private Long mMilliseconds;
    /*
        long milliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        Date resultDate = new Date(milliseconds);
        Log.d(TAG, sdf.format(resultDate));
     */
    private ImageUpload mImageUpload;
    private String mReporterKey;
    private String mOrganization;
    private String mDecision;

    public Report() {}

    public Report(String title, String info, Long milliseconds, ImageUpload imageUpload,
                  String reporterKey, String organization, String decision) {
        mTitle = title;
        mInfo = info;
        mMilliseconds = milliseconds;
        mImageUpload = imageUpload;
        mReporterKey = reporterKey;
        mOrganization = organization;
        mDecision = decision;
    }

    public String getTitle() { return mTitle; }
    public String getInformation() { return mInfo; }
    public Long getDate(){ return mMilliseconds; }
    public ImageUpload getImageUpload() { return mImageUpload; }
    public String getReporterKey() { return mReporterKey; }
    public String getOrganization() { return mOrganization; }
}
