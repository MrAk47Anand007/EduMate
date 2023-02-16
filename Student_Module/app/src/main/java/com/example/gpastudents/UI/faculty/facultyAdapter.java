package com.example.gpastudents.UI.faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.gpastudents.R;


import java.util.List;

public class facultyAdapter extends RecyclerView.Adapter<facultyAdapter.facultyViewAdapter> {

    private List<facultyData> list;
    private Context context;


    public facultyAdapter(List<facultyData> list, Context context) {
        this.list = list;
        this.context = context;

    }



    @NonNull
    @Override
    public facultyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_layout, parent, false);


        return new facultyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull facultyViewAdapter holder, int position) {

        facultyData item = list.get(position);

        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());

        try {
            Glide.with(context).load(item.getImage()).placeholder(R.drawable.faculty).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class facultyViewAdapter extends RecyclerView.ViewHolder {

        private TextView name, email, post;
        private ImageView imageView;

        public facultyViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.facultyName);
            email = itemView.findViewById(R.id.facultyEmail);
            post = itemView.findViewById(R.id.facultyPost);
            imageView = itemView.findViewById(R.id.facultyImage);


        }
    }


}
