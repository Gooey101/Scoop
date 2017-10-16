package com.example.cleeg.scoopreporter.organization;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cleeg.scoopreporter.R;
import com.example.cleeg.scoopreporter.models.Report;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrgFragment extends Fragment {

    private static final String TAG = "OrgFragment";

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Report, ReportViewHolder> mAdapter;

    private RecyclerView mRecycler;

    public OrgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_org, container, false);

        // Connect to Firebase
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("organizations").child("-KwUnjXTT5pHFv9Ubwh-").child("reports");

        // Find the RecyclerView
        mRecycler = (RecyclerView) rootView.findViewById(R.id.item_list);
        mRecycler.setHasFixedSize(false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        // Used to reverse item traversal and layout order
        linearLayoutManager.setReverseLayout(true);
        // List fills its content starting from the bottom of the view
        linearLayoutManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(linearLayoutManager);

        // Create custom FirebaseRecyclerAdapter subclass
        mAdapter = new FirebaseRecyclerAdapter<Report, ReportViewHolder>(
                Report.class, R.layout.item_report, ReportViewHolder.class, mDatabaseReference) {
            @Override
            protected void populateViewHolder(ReportViewHolder viewHolder, Report model, int position) {
                final DatabaseReference eORef = getRef(position);
                final String eOKey = eORef.getKey();

                // Set click listener for the whole post view
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch ReportDetailActivity
                        Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
                        intent.putExtra(ReportDetailActivity.EXTRA_REPORT_KEY, eOKey);
                        startActivity(intent);
                    }
                });

                // Bind Report to ViewHolder
                viewHolder.bindToItem(model);
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }
}
