package com.example.gpa.teachers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpa.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class facultyAdapter extends RecyclerView.Adapter<facultyAdapter.facultyViewAdapter> {

    private List<facultyData> list;
    private Context context;
    private String category;

    public facultyAdapter(List<facultyData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public facultyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.faculty_layout,parent,false);


        return new facultyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull facultyViewAdapter holder, int position) {

        facultyData item = list.get(position);

        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());

        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,UpdateFaculty.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class facultyViewAdapter extends RecyclerView.ViewHolder {

        private TextView name,email,post;
        private Button update;
        private ImageView imageView;

        public facultyViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.facultyName);
            email = itemView.findViewById(R.id.facultyEmail);
            post = itemView.findViewById(R.id.facultyPost);
            imageView = itemView.findViewById(R.id.facultyImage);
            update = itemView.findViewById(R.id.facultyUpdate);




        }
    }


}
