package com.example.gpastudents.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gpastudents.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookActivity extends AppCompatActivity {

    private RecyclerView bookRecycler;
    private DatabaseReference reference;
    private List<bookData> list;
    private bookAdapter adapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



        bookRecycler = findViewById(R.id.bookRecycle);
        linearLayout = findViewById(R.id.shimmer_layout);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        reference = FirebaseDatabase.getInstance().getReference().child("Books");

        getData();


    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list = new ArrayList<>();
                for (DataSnapshot snapshot1:snapshot.getChildren()){

                    bookData data = snapshot1.getValue(bookData.class);
                    list.add(data);
                }
                adapter = new bookAdapter(BookActivity.this,list);
                bookRecycler.setLayoutManager(new LinearLayoutManager(BookActivity.this));
                bookRecycler.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                linearLayout.setVisibility(View.GONE);

                }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(BookActivity.this,"DatabaseError" , Toast.LENGTH_SHORT).show();
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
    protected void onResume()
    {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }

}