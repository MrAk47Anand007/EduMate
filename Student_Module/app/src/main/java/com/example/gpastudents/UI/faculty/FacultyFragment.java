package com.example.gpastudents.UI.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gpastudents.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {

    private RecyclerView itDept, csDept;
    private LinearLayout itNoData, csNoData;
    private List<facultyData> list1, list2;
    private facultyAdapter adapter;

    private DatabaseReference reference, dbref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_faculty, container, false);
        itDept = view.findViewById(R.id.itDepartment);
        csDept = view.findViewById(R.id.csDepartment);

        itNoData = view.findViewById(R.id.itNoData);
        csNoData = view.findViewById(R.id.csNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");


        itDepartment();
        csDepartment();

        return view;

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
                    csDept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new facultyAdapter(list2, getContext());
                    csDept.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

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
                    itDept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new facultyAdapter(list1, getContext());
                    itDept.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}