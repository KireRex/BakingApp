package com.scheffer.erik.bakingapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.scheffer.erik.bakingapp.R;
import com.scheffer.erik.bakingapp.models.Step;
import com.scheffer.erik.bakingapp.pageadapters.StepPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.scheffer.erik.bakingapp.fragments.StepDetailFragment.STEP_EXTRA_KEY;

public class StepDetailActivity extends AppCompatActivity {

    public static String SELECTED_STEP_KEY = "selected-step";

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.step_detail_container)
    ViewPager viewPager;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        StepPageAdapter adapter =
                new StepPageAdapter(getSupportFragmentManager(),
                                    getIntent().<Step>getParcelableArrayListExtra(STEP_EXTRA_KEY),
                                    this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getIntent().getIntExtra(SELECTED_STEP_KEY, 0));
        tabLayout.setupWithViewPager(viewPager);
    }
}
