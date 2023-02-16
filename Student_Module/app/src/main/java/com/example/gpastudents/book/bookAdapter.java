package com.example.gpastudents.book;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpastudents.R;

import java.util.List;

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.bookVievHolder> {

    public bookAdapter(Context context, List<bookData> list) {
        this.context = context;
        this.list = list;
    }

    private Context context;
    private List<bookData> list;

    @NonNull
    @Override
    public bookVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_layout_item,parent,false);

        return new bookVievHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookVievHolder holder, final int position) {

        holder.bookName.setText(list.get(position).getBookTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PdfViewerActivity.class);
                intent.putExtra("pdfUrl",list.get(position).getBookUrl());
                context.startActivity(intent);

            }
        });

        holder.bookDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getBookUrl()));
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class bookVievHolder extends RecyclerView.ViewHolder {

        private TextView bookName;
        private Button bookDownload;

        public bookVievHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.pdftext);
            bookDownload = itemView.findViewById(R.id.pdfDownload);
        }
    }
}
