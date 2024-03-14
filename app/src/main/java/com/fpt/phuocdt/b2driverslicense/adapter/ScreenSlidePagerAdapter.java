package com.fpt.phuocdt.b2driverslicense.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.fpt.phuocdt.b2driverslicense.entity.Question;

import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private List<Question> questionList;

    public ScreenSlidePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Question> questions) {
        super(fragmentManager, lifecycle);
        this.questionList = questions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ExamFragment.newInstance(questionList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }
}

