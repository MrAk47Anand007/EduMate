package com.example.gpa.Notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gpa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeleteNotice extends AppCompatActivity {

    private ProgressBar pb;
    private RecyclerView DeleteNoticeRecycle;
    private ArrayList<noticeData> list;
    private NoticeAdapter adapter;

    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

        pb = findViewById(R.id.progressBar);
        DeleteNoticeRecycle = findViewById(R.id.NoticeDeleteRecycle);

        DeleteNoticeRecycle.setLayoutManager(new LinearLayoutManager(this));
        DeleteNoticeRecycle.setHasFixedSize(true);
        getNotice();
    }

    private void getNotice() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    noticeData data = snapshot1.getValue(noticeData.class);
                    list.add(data);
                }

                adapter = new NoticeAdapter(DeleteNotice.this,list);
                adapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
                DeleteNoticeRecycle.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pb.setVisibility(View.GONE);
                Toast.makeText(DeleteNotice.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}