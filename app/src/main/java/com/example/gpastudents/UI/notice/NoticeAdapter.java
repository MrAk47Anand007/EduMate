package com.example.gpastudents.UI.notice;


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



import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {
    private Context context;
    private ArrayList<noticeData> list;

    public NoticeAdapter(Context context, ArrayList<noticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_notice, parent, false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {
        noticeData currentItem = list.get(position);

        holder.showNoticeTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());

        try {
            if (currentItem.image != null)
                Glide.with(context).load(currentItem.getImage()).into(holder.displayNoticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {


        private TextView showNoticeTitle, date, time;
        private ImageView displayNoticeImage;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);


            showNoticeTitle = itemView.findViewById(R.id.NoticeShowTitle);
            displayNoticeImage = itemView.findViewById(R.id.NoticeDisplayImage);
            date = itemView.findViewById(R.id.DateShow);
            time = itemView.findViewById(R.id.TimeShow);
        }
    }
}
