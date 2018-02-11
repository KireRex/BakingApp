package com.scheffer.erik.bakingapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.scheffer.erik.bakingapp.R;
import com.scheffer.erik.bakingapp.fragments.StepDetailFragment;
import com.scheffer.erik.bakingapp.models.Step;

import java.util.List;


public class StepPageAdapter extends FragmentStatePagerAdapter {

    private List<Step> steps;
    private Context context;

    public StepPageAdapter(FragmentManager fm, List<Step> steps, Context context) {
        super(fm);
        this.steps = steps;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(StepDetailFragment.STEP_EXTRA_KEY, steps.get(position));
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(R.string.step_tab_title, position);
    }
}
