package com.scheffer.erik.bakingapp.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.scheffer.erik.bakingapp.R;
import com.scheffer.erik.bakingapp.models.Recipe;
import com.scheffer.erik.bakingapp.recyclerviewadapters.IngredientAdapter;
import com.scheffer.erik.bakingapp.recyclerviewadapters.StepAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity {
    public static final String RECIPE_EXTRA_KEY = "recipe";

    private boolean mTwoPane;

    @BindView(R.id.step_list)
    RecyclerView stepsRecyclerView;

    @BindView(R.id.ingredients_list)
    RecyclerView ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        ButterKnife.bind(this);

        Recipe recipe = getIntent().getParcelableExtra(RECIPE_EXTRA_KEY);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(recipe.getName());
        }

        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(new StepAdapter(recipe.getSteps(), this, mTwoPane));
        stepsRecyclerView.addItemDecoration(new DividerItemDecoration(stepsRecyclerView.getContext(),
                                                                      layoutManager.getOrientation()));

        ingredientsList.setAdapter(new IngredientAdapter(recipe.getIngredients(), this));
    }
}
