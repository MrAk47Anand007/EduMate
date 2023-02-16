package com.example.gpastudents.UI.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.gpastudents.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BranchAdapter extends PagerAdapter {
    private Context context;
    private List<BranchModel> list;

    public BranchAdapter(Context context, List<BranchModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.branchitemlayout,container,false);

        ImageView Branchicon;
        TextView BranchName,BranchDescription;

        Branchicon = view.findViewById(R.id.BranchIcon);
        BranchName = view.findViewById(R.id.BranchTitle);
        BranchDescription = view.findViewById(R.id.BranchDescription);

        Branchicon.setImageResource(list.get(position).getImg());
        BranchName.setText(list.get(position).getTitle());
        BranchDescription.setText(list.get(position).getDescription());

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }
}
