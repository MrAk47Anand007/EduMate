package com.example.gpastudents.curriculum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gpastudents.R;
import com.example.gpastudents.oldPaper.oldPaperActivity;
import com.example.gpastudents.oldPaper.oldPaperAdapter;
import com.example.gpastudents.oldPaper.oldPaperData;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class curriculumActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<curriculumdata> list;
    private curriculumadapter adapter;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.curriculumRecycle);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container2);
        linearLayout = findViewById(R.id.shimmer_layoutcurriculum);
        reference = FirebaseDatabase.getInstance().getReference().child("Curriculum");

        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    curriculumdata data = snapshot1.getValue(curriculumdata.class);
                    list.add(data);
                }

                adapter = new curriculumadapter(curriculumActivity.this,list);
                recyclerView.setLayoutManager(new LinearLayoutManager(curriculumActivity.this));
                recyclerView.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                linearLayout.setVisibility(View.GONE);
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(curriculumActivity.this, "DatabaseError", Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.stopShimmer();
                linearLayout.setVisibility(View.GONE);

            }
        });
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }

}