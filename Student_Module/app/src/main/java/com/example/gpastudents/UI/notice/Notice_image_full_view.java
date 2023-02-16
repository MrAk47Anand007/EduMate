package com.example.gpastudents.UI.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gpastudents.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.Objects;

public class Notice_image_full_view extends AppCompatActivity {

    //private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_image_full_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //imageView = findViewById(R.id.View_image);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        String image =getIntent().getStringExtra("image");
        Glide.with(this).load(image).into(photoView);


    }
}