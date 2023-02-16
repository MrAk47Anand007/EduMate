package com.example.gpastudents.oldPaper;

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
import com.example.gpastudents.book.PdfViewerActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class oldPaperAdapter extends RecyclerView.Adapter<oldPaperAdapter.oldPaperAdapterView> {
    private Context context;
    private List<oldPaperData> list;

    public oldPaperAdapter(Context context, List<oldPaperData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public oldPaperAdapterView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.oldpaperview,parent,false);
        return new oldPaperAdapterView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull oldPaperAdapterView holder, int position) {

        holder.textView.setText(list.get(position).getPaperTitle());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,oldPaperViewActivity.class);
                intent.putExtra("paperUrl",list.get(position).getPaperUrl());
                context.startActivity(intent);
            }
        });



        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPaperUrl()));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class oldPaperAdapterView extends RecyclerView.ViewHolder {

        private TextView textView;
        private Button button;

        public oldPaperAdapterView(@NonNull @NotNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.pdftextoldPaper);
            button = itemView.findViewById(R.id.pdfDownloadoldPaper);
        }
    }
}
