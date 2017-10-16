package com.example.cleeg.scoopreporter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cleeg.scoopreporter.models.Reporter;
import com.example.cleeg.scoopreporter.organization.OrgMainActivity;
import com.example.cleeg.scoopreporter.reporter.ReporterMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    private EditText mReporterEmailField;
    private EditText mReporterPasswordField;
    private EditText mOrgEmailField;
    private EditText mOrgPasswordField;

    private Button mReporterSignInButton;
    private Button mReporterSignUpButton;
    private Button mOrgSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize Firebase components
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

        // Initialize references to views & buttons
        mReporterEmailField = (EditText) findViewById(R.id.reporter_field_email);
        mReporterPasswordField = (EditText) findViewById(R.id.reporter_field_password);
        mOrgEmailField = (EditText) findViewById(R.id.org_field_email);
        mOrgPasswordField = (EditText) findViewById(R.id.org_field_password);
        mReporterSignInButton = (Button) findViewById(R.id.reporter_button_sign_in);
        mReporterSignUpButton = (Button) findViewById(R.id.reporter_button_sign_up);
        mOrgSignInButton = (Button) findViewById(R.id.org_button_sign_in);

        // Click listeners
        mReporterSignInButton.setOnClickListener(this);
        mReporterSignUpButton.setOnClickListener(this);
        mOrgSignInButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mFirebaseAuth.getCurrentUser() != null) {
            String domain = domainFromEmail(mFirebaseAuth.getCurrentUser().getEmail());
            if (domain.equals("scoop.com")) {
                // Go to OrgMainActivity
                startActivity(new Intent(SignInActivity.this, OrgMainActivity.class));
                finish();
            } else {
                // Go to ReporterMainActivity
                startActivity(new Intent(SignInActivity.this, ReporterMainActivity.class));
                finish();
            }
        }
    }

    private void reporterSignIn() {
        Log.d(TAG, "reporterSignIn");
        if (!validateReporterForm()) {
            return;
        }

        showProgressDialog();
        String email = mReporterEmailField.getText().toString();
        String password = mReporterPasswordField.getText().toString();

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInUser:onComplete: " + task.isSuccessful());
                hideProgressDialog();

                if (task.isSuccessful()) {
                    // Go to ReporterMainActivity
                    startActivity(new Intent(SignInActivity.this, ReporterMainActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void reporterSignUp() {
        Log.d(TAG, "reporterSignUp");
        if (!validateReporterForm()) {
            return;
        }

        showProgressDialog();
        String email = mReporterEmailField.getText().toString();
        String password = mReporterPasswordField.getText().toString();

        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUser:onComplete: " + task.isSuccessful());
                hideProgressDialog();

                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast.makeText(SignInActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void orgSignIn() {
        Log.d(TAG, "orgSignIn");
        if (!validateOrgForm()) {
            return;
        }

        showProgressDialog();
        String email = mOrgEmailField.getText().toString();
        String password = mOrgPasswordField.getText().toString();

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInOrg:onComplete: " + task.isSuccessful());
                hideProgressDialog();

                if (task.isSuccessful()) {
                    // Go to OrgMainActivity
                    startActivity(new Intent(SignInActivity.this, OrgMainActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String domain = domainFromEmail(user.getEmail());
        if (domain.equals("scoop.com")) {
            // Go to OrgMainActivity
            startActivity(new Intent(SignInActivity.this, OrgMainActivity.class));
            finish();
        }

        String username = usernameFromEmail(user.getEmail());

        // Write new reporter
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to ReporterMainActivity
        startActivity(new Intent(SignInActivity.this, ReporterMainActivity.class));
        finish();
    }

    private String domainFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[1];
        } else {
            return email;
        }
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        Reporter reporter = new Reporter(0, name, email, 0);

        mDatabaseReference.child("reporters").child(userId).setValue(reporter);
    }

    private boolean validateReporterForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mReporterEmailField.getText().toString())) {
            mReporterEmailField.setError("Required");
            result = false;
        } else {
            mReporterEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mReporterPasswordField.getText().toString())) {
            mReporterPasswordField.setError("Required");
            result = false;
        } else {
            mReporterPasswordField.setError(null);
        }

        return result;
    }

    private boolean validateOrgForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mOrgEmailField.getText().toString())) {
            mOrgEmailField.setError("Required");
            result = false;
        } else {
            mOrgEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mOrgPasswordField.getText().toString())) {
            mOrgPasswordField.setError("Required");
            result = false;
        } else {
            mOrgPasswordField.setError(null);
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.reporter_button_sign_in) {
            reporterSignIn();
        } else if (i == R.id.reporter_button_sign_up) {
            reporterSignUp();
        } else if (i == R.id.org_button_sign_in) {
            orgSignIn();
        }
    }
}
