package com.example.gpa.Submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gpa.Notice.DeleteNotice;
import com.example.gpa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class delete_submission extends AppCompatActivity {

    private ProgressBar pb;
    private RecyclerView showSubmissionRecycle;
    private ArrayList<submissionData> list;
    private submissionAdapter adapter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_submission);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference().child("Submission");

        pb = findViewById(R.id.SubmissionprogressBar);
        showSubmissionRecycle = findViewById(R.id.SubmissionDeleteRecycle);

        showSubmissionRecycle.setLayoutManager(new LinearLayoutManager(this));
        showSubmissionRecycle.setHasFixedSize(true);
        getSubmissionInfo();


    }

    private void getSubmissionInfo() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    submissionData data = snapshot1.getValue(submissionData.class);
                    list.add(data);
                }
                adapter = new submissionAdapter(delete_submission.this,list);
                adapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
                showSubmissionRecycle.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                pb.setVisibility(View.GONE);
                Toast.makeText(delete_submission.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}