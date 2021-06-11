package com.example.gpa.Submission;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

     holder.submissionDeletebtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             AlertDialog.Builder builder = new AlertDialog.Builder(context);
             builder.setMessage("Are you sure want to delete this notice ? ");
             builder.setCancelable(true);
             builder.setPositiveButton(
                     "OK",
                     new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Submission");
                             reference.child(currentItem.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull @NotNull Task<Void> task) {
                                     Toast.makeText(context,"Announcement Deleted",Toast.LENGTH_SHORT).show();
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull @NotNull Exception e) {
                                     Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                                 }
                             });
                             notifyItemRemoved(position);
                         }
                     }
             );
             builder.setNegativeButton(
                     "Cancel",
                     new DialogInterface.OnClickListener(){

                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
                 }
             }
             );
             AlertDialog dialog = null;
             try {
                 dialog = builder.create();
             } catch (Exception e) {
                 e.printStackTrace();
             }
             if (dialog!= null) {
                 dialog.show();
             }
         }
     });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class submissionViewAdapter extends RecyclerView.ViewHolder {

        private Button submissionDeletebtn;
        private TextView title,title1,lastdate,date,time;

        public submissionViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);

            submissionDeletebtn = itemView.findViewById(R.id.SubmissionDelete);
            title1 = itemView.findViewById(R.id.practicalTitle);
            title =itemView.findViewById(R.id.subjectTitle);
            lastdate = itemView.findViewById(R.id.LastDate);
            date = itemView.findViewById(R.id.DateShow);
            time = itemView.findViewById(R.id.TimeShow);

        }
    }
}