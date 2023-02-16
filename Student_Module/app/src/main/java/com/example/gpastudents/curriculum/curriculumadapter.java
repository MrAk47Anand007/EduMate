package com.example.gpastudents.curriculum;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpastudents.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class curriculumadapter extends RecyclerView.Adapter<curriculumadapter.curriculumadapterView> {
    private Context context;
    private List<curriculumdata> list;

    public curriculumadapter(Context context, List<curriculumdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public curriculumadapterView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.curriculumview, parent, false);
        return new curriculumadapterView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull curriculumadapterView holder, int position) {

        holder.textView.setText(list.get(position).getCarriculumTitle());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, curriculumViewActivity.class);
                intent.putExtra("carriculumUrl", list.get(position).getCarriculumUrl());
                context.startActivity(intent);
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getCarriculumUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class curriculumadapterView extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button button;

        public curriculumadapterView(@NonNull @NotNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.curriculumText);
            button = itemView.findViewById(R.id.curriculumDownload);


        }
    }
}
