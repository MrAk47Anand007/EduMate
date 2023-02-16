package com.example.gpastudents.UI.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gpastudents.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class galleryFragment extends Fragment {


    RecyclerView itRecycleView, compRecycleView;
    galleryAdapter adapter;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        itRecycleView = view.findViewById(R.id.itRecycleView);
        compRecycleView = view.findViewById(R.id.compRecycleView);

        reference = FirebaseDatabase.getInstance().getReference().child("Gallery");

        getitImage();
        getcompImage();

        return view;
    }

    private void getitImage() {

        reference.child("Information Technology").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imageList.add(data);
                }
                adapter = new galleryAdapter(getContext(), imageList);
                itRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                itRecycleView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getcompImage() {
        reference.child("Computer Engineering").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String data = (String) snapshot1.getValue();
                    imageList.add(data);
                }
                adapter = new galleryAdapter(getContext(), imageList);
                compRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                compRecycleView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}