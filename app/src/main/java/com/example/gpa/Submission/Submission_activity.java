package com.example.gpa.Submission;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gpa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Submission_activity extends AppCompatActivity {


    private EditText prName, subName, lastDate;
    private Button uploadBtn;
    private DatabaseReference reference, dbref;
    StorageReference storageReference;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);


        prName = findViewById(R.id.SubmissionTextName);
        subName = findViewById(R.id.SubmissionTextSubject);
        uploadBtn = findViewById(R.id.SubmissionBtnpage);
        lastDate = findViewById(R.id.SubmissionTextLastDate);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prName.getText().toString().isEmpty()) {
                    prName.setError("Empty");
                    prName.requestFocus();

                } else if (subName.getText().toString().isEmpty()) {
                    subName.setError("Empty");
                    subName.requestFocus();
                } else if (lastDate.getText().toString().isEmpty()) {
                    lastDate.setError("Empty");
                    lastDate.requestFocus();
                } else {
                    uploadData1();


                }

            }
        });
    }




    private void uploadData1() {
        pd.setMessage("Uploading...");
        pd.show();

        dbref = reference.child("Submission");
        final String uniquekey = dbref.push().getKey();
        String title = prName.getText().toString();
        String title1 = subName.getText().toString();
        String lastdate = lastDate.getText().toString();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());


        submissionData submissionData = new submissionData(title, title1, lastdate, date, time, uniquekey);

        dbref.child(uniquekey).setValue(submissionData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(Submission_activity.this, "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Submission_activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

       /* dbref = reference.child("SubmissionOutput");
        dbref.child(title1).setValue(submissionData.title1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }); */


    }

}