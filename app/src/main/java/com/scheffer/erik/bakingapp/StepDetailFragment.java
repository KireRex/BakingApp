package com.scheffer.erik.bakingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scheffer.erik.bakingapp.models.Step;

public class StepDetailFragment extends Fragment {
    public static final String STEP_EXTRA_KEY = "step";

    private Step step;

    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        step = getArguments().getParcelable(STEP_EXTRA_KEY);
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(step.getShortDescription());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        ((TextView) rootView.findViewById(R.id.step_detail)).setText(step.getDescription());

        return rootView;
    }
}
