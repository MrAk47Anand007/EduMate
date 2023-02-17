package com.example.gpa.submissionView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpa.R;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class subViewAdapter extends RecyclerView.Adapter<subViewAdapter.subViewAdapternew> {
    private Context context;
    private List<subViewPdfData> list;
    public static String subName;

    public subViewAdapter(Context context, List<subViewPdfData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public subViewAdapternew onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_submission, parent, false);
        return new subViewAdapternew(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull subViewAdapternew holder, final int position) {

        holder.textView.setText(list.get(position).getSubjectName());
        holder.studname.setText(list.get(position).getStudName());
        holder.studID.setText(list.get(position).getStudID());
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getTime());
        holder.Addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.Addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MarksActivity.class);
                context.startActivity(intent);
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subName = list.get(position).getSubjectName();
                // Toast.makeText(context,subName,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, subViewPDf.class);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class subViewAdapternew extends RecyclerView.ViewHolder {
        private TextView textView, studname, studID, date, time;
        private MaterialCardView cardView;
        private Button Addmarks,DeleteSubmission;

        public subViewAdapternew(@NonNull @NotNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.subjectNameExpand);
            studname = itemView.findViewById(R.id.Submissionname);
            studID = itemView.findViewById(R.id.Submissionid);
            date = itemView.findViewById(R.id.DateShow);
            time = itemView.findViewById(R.id.TimeShow);
            cardView = itemView.findViewById(R.id.submissioncard);
            Addmarks = itemView.findViewById(R.id.addmarks);
            DeleteSubmission = itemView.findViewById(R.id.DeleteActivity);

        }
    }
}
