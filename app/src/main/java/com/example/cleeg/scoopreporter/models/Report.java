package com.example.cleeg.scoopreporter.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Report {

    private String mTitle;
    private String mInfo;
    private Long mMilliseconds;
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
    public Long getMilliseconds(){ return mMilliseconds; }
    public ImageUpload getImageUpload() { return mImageUpload; }
    public String getReporterKey() { return mReporterKey; }
    public String getOrganization() { return mOrganization; }

    public String setTitle(String title) { return mTitle = title; }
    public String setInformation(String information) { return mInfo = information; }
    public Long setMMilliseconds(Long milliseconds) { return mMilliseconds = milliseconds; }
    public ImageUpload setImageUpload(ImageUpload imageUpload) { return mImageUpload = imageUpload; }
    public String setReporterKey(String reporterKey) { return mReporterKey = reporterKey; }
    public String setOrganization(String organization) { return mOrganization = organization; }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", mTitle);
        result.put("info", mInfo);
        result.put("milliseconds", mMilliseconds);
        result.put("imageUpload", mImageUpload);
        result.put("reporterKey", mReporterKey);
        result.put("organization", mOrganization);
        result.put("decision", mDecision);
        return result;
    }
}
