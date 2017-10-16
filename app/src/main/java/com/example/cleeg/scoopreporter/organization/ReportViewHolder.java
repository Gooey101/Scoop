package com.example.cleeg.scoopreporter.organization;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cleeg.scoopreporter.R;
import com.example.cleeg.scoopreporter.models.Report;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Describe an item view and metadata about its place within the RecyclerView
 */
class ReportViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTitleView;
    private final TextView mDateView;

    public ReportViewHolder(View itemView) {
        super(itemView);

        mTitleView = (TextView) itemView.findViewById(R.id.item_title);
        mDateView = (TextView) itemView.findViewById(R.id.item_date);
    }

    void bindToItem(Report report) {
        mTitleView.setText(report.getTitle());
        mDateView.setText("October 15, 2017");
    }
}
