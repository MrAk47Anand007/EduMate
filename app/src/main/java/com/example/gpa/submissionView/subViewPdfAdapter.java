package com.example.gpa.submissionView;

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

import com.example.gpa.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class subViewPdfAdapter extends RecyclerView.Adapter<subViewPdfAdapter.subViewPdfAdapterView> {

    private Context context;
    private List<subViewPdfData> list;

    public subViewPdfAdapter(Context context, List<subViewPdfData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public subViewPdfAdapterView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.subpdfview, parent, false);
        return new subViewPdfAdapterView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull subViewPdfAdapterView holder, final int position) {
        holder.pdfName.setText(list.get(position).getPdfTitle());

        holder.pdfName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, pdfViewactivity.class);
                intent.putExtra("pdfUrl", list.get(position).getPdfUrl());
                context.startActivity(intent);
            }
        });

        holder.pdfDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPdfUrl()));
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class subViewPdfAdapterView extends RecyclerView.ViewHolder {
        private TextView pdfName;
        private Button pdfDownload;

        public subViewPdfAdapterView(@NonNull @NotNull View itemView) {
            super(itemView);

            pdfName = itemView.findViewById(R.id.pdftext);
            pdfDownload = itemView.findViewById(R.id.pdfDownload);
        }
    }
}
