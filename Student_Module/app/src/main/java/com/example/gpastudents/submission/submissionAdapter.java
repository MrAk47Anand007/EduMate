package com.example.gpastudents.submission;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpastudents.R;
import com.example.gpastudents.book.BookActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class submissionAdapter extends RecyclerView.Adapter<submissionAdapter.submissionViewAdapter> {
    private Context context;
    private ArrayList<submissionData> list;

    public submissionAdapter(Context context, ArrayList<submissionData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public submissionViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_submission,parent,false);

        return new submissionViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull submissionViewAdapter holder, int position) {
        submissionData currentItem = list.get(position);

        holder.title.setText(currentItem.getTitle());
        holder.title1.setText(currentItem.getTitle1());
        holder.lastdate.setText(currentItem.getLastdate());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());

        holder.submissionUploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,Upload_Submission.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class submissionViewAdapter extends RecyclerView.ViewHolder {
        private Button submissionUploadbtn;
        private TextView title,title1,lastdate,date,time;

        public submissionViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);
            title1 = itemView.findViewById(R.id.practicalTitle);
            title =itemView.findViewById(R.id.subjectTitle);
            lastdate = itemView.findViewById(R.id.LastDate);
            date = itemView.findViewById(R.id.DateShow);
            time = itemView.findViewById(R.id.TimeShow);
            submissionUploadbtn =itemView.findViewById(R.id.SubmissionUpload);

        }
    }

}
