package com.example.cleeg.scoopreporter.reporter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cleeg.scoopreporter.BaseActivity;
import com.example.cleeg.scoopreporter.R;
import com.example.cleeg.scoopreporter.SignInActivity;
import com.example.cleeg.scoopreporter.models.Reporter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReporterMainActivity extends BaseActivity {

    private static final String TAG = "ReporterMainActivity";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private String mUsername;
    private String mCred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_main);

        final TextView username = (TextView) findViewById(R.id.display_username);
        final TextView cred = (TextView) findViewById(R.id.display_cred_score);

        /*
        // Get username and cred to display in TextViews
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child("reporters").child(getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reporter reporter = dataSnapshot.getValue(Reporter.class);
                username.setText(reporter.getUsername());
                cred.setText(reporter.getCred());
                Log.d(TAG, "username: " + reporter.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
        */

        Button createReportButton = (Button) findViewById(R.id.button_create_report);
        createReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReporterMainActivity.this, CreateReportActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
