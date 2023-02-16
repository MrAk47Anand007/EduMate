package com.example.gpa.submissionView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Submission_View extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<subViewPdfData> list;
    private DatabaseReference dbref, reference;
    private subViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        reference = FirebaseDatabase.getInstance().getReference("SubmissionOutput");
        progressBar = findViewById(R.id.ViewSubmissionprogressBar);
        recyclerView = findViewById(R.id.SubmissionViewRecycle);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        getSubInfo();


    }

    private void getSubInfo() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    subViewPdfData data = snapshot1.getValue(subViewPdfData.class);
                    list.add(data);

                }

                adapter = new subViewAdapter(Submission_View.this, list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Submission_View.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}