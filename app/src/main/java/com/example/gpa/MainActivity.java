package com.example.gpa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gpa.Notice.DeleteNotice;
import com.example.gpa.Notice.upload_notice;
import com.example.gpa.Submission.Submission_activity;
import com.example.gpa.Submission.delete_submission;
import com.example.gpa.submissionView.Submission_View;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView UploadNotice;
    CardView UploadImage;
    CardView UploadBook;
    CardView UploadCurriculum;
    CardView UploadOldPaper;
    CardView UploadFaculty;
    CardView DeleteFaculty;
    CardView SubmissionPage;
    CardView DeleteSubmission;
    CardView SubmissionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UploadNotice = findViewById(R.id.addNotice);
        UploadNotice.setOnClickListener(this);

        UploadImage = findViewById(R.id.addImage);
        UploadImage.setOnClickListener(this);

        UploadBook = findViewById(R.id.addBook);
        UploadBook.setOnClickListener(this);

        UploadCurriculum = findViewById(R.id.addcarriculum);
        UploadCurriculum.setOnClickListener(this);

        UploadOldPaper = findViewById(R.id.addoldpaper);
        UploadOldPaper.setOnClickListener(this);

        UploadFaculty = findViewById(R.id.addfaculty);
        UploadFaculty.setOnClickListener(this);

        SubmissionPage=findViewById(R.id.Submissionbtn);
        SubmissionPage.setOnClickListener(this);

        DeleteFaculty =findViewById(R.id.DeleteNoticebtn);
        DeleteFaculty.setOnClickListener(this);

        DeleteSubmission = findViewById(R.id.DeleteSubmissionbtn);
        DeleteSubmission.setOnClickListener(this);

        SubmissionView = findViewById(R.id.SubmissionView);
        SubmissionView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.addNotice:
                 intent = new Intent(MainActivity.this, upload_notice.class);
                startActivity(intent);
                break;
            case R.id.addImage:
                 intent = new Intent(MainActivity.this,UploadImage.class);
                startActivity(intent);
                break;
            case R.id.addBook:
                intent  = new Intent(MainActivity.this,UploadBook.class);
                startActivity(intent);
                break;
            case R.id.addcarriculum:
                intent = new Intent(MainActivity.this,UploadCarriculum.class);
                startActivity(intent);
                break;
            case R.id.addoldpaper:
                intent = new Intent(MainActivity.this,UploadOldPaper.class);
                startActivity(intent);
                break;
            case R.id.addfaculty:
                intent = new Intent(MainActivity.this, com.example.gpa.teachers.UploadFaculty.class);
                startActivity(intent);
                break;
            case R.id.DeleteNoticebtn:
                intent = new Intent(MainActivity.this, DeleteNotice.class);
                startActivity(intent);
                break;
            case R.id.Submissionbtn:
                intent = new Intent(MainActivity.this, Submission_activity.class);
                startActivity(intent);
                break;
            case R.id.DeleteSubmissionbtn:
                intent = new Intent(MainActivity.this, delete_submission.class);
                startActivity(intent);
                break;
            case R.id.SubmissionView:
                intent = new Intent(MainActivity.this, Submission_View.class);
                startActivity(intent);
                break;
        }
    }
}