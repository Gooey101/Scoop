package com.example.cleeg.scoopreporter.organization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cleeg.scoopreporter.R;

public class ReportDetailActivity extends AppCompatActivity {

    private static final String TAG = "ReportDetalActivity";
    public static final String EXTRA_REPORT_KEY = "eo_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
    }
}
