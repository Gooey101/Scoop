package com.example.cleeg.scoopreporter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cleeg.scoopreporter.models.ImageUpload;
import com.example.cleeg.scoopreporter.models.Report;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateReportActivity extends BaseActivity {

    private static final String TAG = "CreateReportActivity";
    private static final int REQUEST_CODE = 1234;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private String title;
    private String info;
    private Long milliseconds;
    private ImageUpload imageUpload;
    private String reporterKey;
    private String organization;

    private Uri imgUri;

    private static final String STORAGE_PATH = "images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        // Initialize Firebase variables
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("reporters").child(getUid())
                .child("reports");

        // Initialize views & buttons
        final EditText titleField = (EditText) findViewById(R.id.field_title);
        final EditText infoField = (EditText) findViewById(R.id.field_info);
        Button media = (Button) findViewById(R.id.button_upload);
        final RadioGroup orgRadioGroup = (RadioGroup) findViewById(R.id.radio_group_org);
        Button submitButton = (Button) findViewById(R.id.button_submit);

        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/* video/*");
                startActivityForResult(Intent.createChooser(intent, "Select media"), REQUEST_CODE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOrg = orgRadioGroup.getCheckedRadioButtonId();
                final RadioButton orgRadioButton = (RadioButton) findViewById(selectedOrg);
                organization = orgRadioButton.getText().toString();
                title = titleField.getText().toString();
                info = infoField.getText().toString();
                reporterKey = getUid();
                milliseconds = System.currentTimeMillis();
                String decision = "undecided";
                Report report = new Report(title, info, milliseconds, imageUpload, reporterKey,
                        organization, decision);
                mDatabaseRef.push().setValue(report);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null &&
                data.getData() != null) {
            imgUri = data.getData();
            if (imgUri != null) {
                final ProgressDialog dialog = new ProgressDialog(CreateReportActivity.this);
                dialog.setTitle("Uploading image");
                dialog.show();

                // Get the storage reference
                StorageReference ref = mStorageRef.child(STORAGE_PATH).child(getUid())
                        .child(System.currentTimeMillis() + "." + getImageExt(imgUri));

                // Add file to reference
                ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Dismiss dialog when success
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                        imageUpload = new ImageUpload(imgUri.getPath(), taskSnapshot.getDownloadUrl().toString());
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Dismiss dialog when success
                                dialog.dismiss();
                                // Display success toast message
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        // Show upload progress
                        double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        dialog.setMessage("Uploaded " + (int)progress);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
