package com.example.gpa.teachers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpa.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UploadFaculty extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView itDept, csDept;
    private LinearLayout itNoData, csNoData;
    private List<facultyData> list1, list2;
    private facultyAdapter adapter;

    private DatabaseReference reference, dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_faculty);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        itDept = findViewById(R.id.itDepartment);
        csDept = findViewById(R.id.csDepartment);

        itNoData = findViewById(R.id.itNoData);
        csNoData = findViewById(R.id.csNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");


        itDepartment();
        csDepartment();


        fab = findViewById(R.id.floatingActionbtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadFaculty.this, AddFaculty.class));
            }
        });

    }

    private void csDepartment() {
        dbref = reference.child("Computer Engineering");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();

                if (!snapshot.exists()) {

                    csNoData.setVisibility(View.VISIBLE);
                    csDept.setVisibility(View.GONE);

                } else {
                    csNoData.setVisibility(View.GONE);
                    csDept.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        facultyData data = snapshot1.getValue(facultyData.class);
                        list2.add(data);
                    }
                    csDept.setHasFixedSize(true);
                    csDept.setLayoutManager(new LinearLayoutManager(UploadFaculty.this));
                    adapter = new facultyAdapter(list2, UploadFaculty.this, "Computer Engineering");
                    csDept.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void itDepartment() {
        dbref = reference.child("Information Technology");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();

                if (!snapshot.exists()) {

                    itNoData.setVisibility(View.VISIBLE);
                    itDept.setVisibility(View.GONE);

                } else {
                    itNoData.setVisibility(View.GONE);
                    itDept.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        facultyData data = snapshot1.getValue(facultyData.class);
                        list1.add(data);
                    }
                    itDept.setHasFixedSize(true);
                    itDept.setLayoutManager(new LinearLayoutManager(UploadFaculty.this));
                    adapter = new facultyAdapter(list1, UploadFaculty.this, "Information Technology");
                    itDept.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UploadFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}