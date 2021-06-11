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

public class subViewPDf extends AppCompatActivity {

    private RecyclerView pdfRecycler;
    private DatabaseReference reference;
    private List<subViewPdfData> list;
    private subViewPdfAdapter adapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_view_pdf);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        pdfRecycler = findViewById(R.id.ShowSubpdfRecycle);
        progressBar = findViewById(R.id.progressBar3);
        reference = FirebaseDatabase.getInstance().getReference().child("SubmissionOutput");


        getData();
    }

    private void getData() {
        String subjectName = subViewAdapter.subName.toString();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (subjectName.equals(snapshot1.child("SubjectName").getValue().toString())) {
                        subViewPdfData data = snapshot1.getValue(subViewPdfData.class);
                        list.clear();
                        list.add(data);
                        //Toast.makeText(subViewPDf.this, subjectName, Toast.LENGTH_SHORT).show();
                    }
                }


                adapter = new subViewPdfAdapter(subViewPDf.this, list);
                pdfRecycler.setLayoutManager(new LinearLayoutManager(subViewPDf.this));
                pdfRecycler.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(subViewPDf.this, "DatabaseError", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}