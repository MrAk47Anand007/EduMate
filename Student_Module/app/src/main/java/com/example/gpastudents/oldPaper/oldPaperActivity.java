package com.example.gpastudents.oldPaper;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpastudents.R;
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

public class oldPaperActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<oldPaperData> list;
    private oldPaperAdapter adapter;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_paper);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.oldPaperRecycle);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container1);
        linearLayout = findViewById(R.id.shimmer_layoutoldPaper);
        reference = FirebaseDatabase.getInstance().getReference().child("Papers");


        getData();

    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    oldPaperData data = snapshot1.getValue(oldPaperData.class);
                    list.add(data);
                }

                adapter = new oldPaperAdapter(oldPaperActivity.this, list);
                recyclerView.setLayoutManager(new LinearLayoutManager(oldPaperActivity.this));
                recyclerView.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                linearLayout.setVisibility(View.GONE);
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(oldPaperActivity.this, "DatabaseError", Toast.LENGTH_SHORT).show();
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