package com.example.gpastudents.UI.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gpastudents.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class galleryAdapter extends RecyclerView.Adapter<galleryAdapter.galleryViewAdapter> {
    private Context context;
    private List<String> imageslist;

    public galleryAdapter(Context context, List<String> imageslist) {
        this.context = context;
        this.imageslist = imageslist;
    }

    @NonNull
    @Override
    public galleryViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_images, parent, false);

        return new galleryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull galleryViewAdapter holder, int position) {
        Glide.with(context).load(imageslist.get(position)).into(holder.imageView);
        //Picasso.get().load(imageslist.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageslist.size();
    }

    public class galleryViewAdapter extends RecyclerView.ViewHolder {

        ImageView imageView;


        public galleryViewAdapter(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageGallery);
        }
    }


}
