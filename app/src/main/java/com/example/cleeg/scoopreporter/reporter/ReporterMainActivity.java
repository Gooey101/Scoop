package com.example.cleeg.scoopreporter.reporter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cleeg.scoopreporter.BaseActivity;
import com.example.cleeg.scoopreporter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReporterMainActivity extends BaseActivity {

    private static final String TAG = "ReporterMainActivity";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_main);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("reporters");
        mDatabaseReference.child(getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button createReportButton = (Button) findViewById(R.id.button_create_report);
        createReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReporterMainActivity.this, CreateReportActivity.class);
                startActivity(intent);
            }
        });
    }
}
