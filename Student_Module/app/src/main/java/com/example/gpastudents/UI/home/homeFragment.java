package com.example.gpastudents.UI.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.gpastudents.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class homeFragment extends Fragment {

    private SliderLayout sliderLayout;
    private ViewPager viewPager;
    private BranchAdapter branchAdapter;
    private List<BranchModel> list;

    private Timer timer;
    private Handler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout = view.findViewById(R.id.slider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(3);

        setSliderViews();

        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_infotech, "Information Technology", "The Diploma in Programme in Information Technology commenced from the academic year 2003 with an intake capacity of 60 students."));
        list.add(new BranchModel(R.drawable.ic_civil, "Civil Engineering", "Civil Engineering Department has been in existence since the establishment of this institute. It offers Diploma in Civil Engineering in two shifts of 60 students each."));
        list.add(new BranchModel(R.drawable.ic_mech, "Mechanical Engineering", "The Mechanical Engineering Programme was started in the year 1955 with an intake of 60.Second shift with an intake of 60 has been started from 2010"));
        list.add(new BranchModel(R.drawable.ic_electro, "Electronics & Telecommunication Engineering", "The Department of Electronics & Telecommunication Engineering started in 1988.Second shift in Department was started in the year 2010."));
        list.add(new BranchModel(R.drawable.ic_comp, "Computer Engineering", "The Department of Computer Engineering started in 2001 supported by well equipped laboratories.. The total intake is of 60 students."));
        list.add(new BranchModel(R.drawable.ic_electrical, "Electrical Engineering", "Electrical Engineering Diploma Programme has been commenced from 1955 with an intake of 60. Academic autonomy has been awarded by Govt, of Maharashtra in 1995"));


        branchAdapter = new BranchAdapter(getContext(), list);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(branchAdapter);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);


        handler = new Handler();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int i = viewPager.getCurrentItem();
                        if (i == list.size() - 1) {
                            i = 0;
                            viewPager.setCurrentItem(i, true);
                        } else {
                            i++;
                            viewPager.setCurrentItem(i, true);
                        }

                    }
                });
            }
        }, 5000, 5000);


        return view;
    }


    private void setSliderViews() {
        for (int i = 0; i < 3; i++) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-students.appspot.com/o/slider%2Fbanner-img-1_1.jpg?alt=media&token=23e8ce32-4cff-479f-a289-b0723abc2ac5");
                    break;
                case 1:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-students.appspot.com/o/slider%2Ff19-3.jpg?alt=media&token=819b6d50-ded4-48fd-8645-0a6e61a1a5c4");
                    break;
                case 2:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/gpa-students.appspot.com/o/slider%2Ff19-7.jpg?alt=media&token=82df8365-ed19-4576-9cd0-369573f6832e");
                    break;

            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            sliderLayout.addSliderView(sliderView);
        }

    }
}