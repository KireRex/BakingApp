package com.scheffer.erik.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.GsonBuilder;
import com.scheffer.erik.bakingapp.R;
import com.scheffer.erik.bakingapp.adapters.RecipeAdapter;
import com.scheffer.erik.bakingapp.iddlingrsource.SimpleIdlingResource;
import com.scheffer.erik.bakingapp.models.Recipe;
import com.scheffer.erik.bakingapp.services.RecipeService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesActivity extends AppCompatActivity {
    private List<Recipe> recipes;

    @BindView(R.id.recipes_recycle_view)
    RecyclerView recipesRecyclerView;

    @Nullable
    private SimpleIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        recipesRecyclerView.setHasFixedSize(true);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecipeAdapter recipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(), this);
        recipesRecyclerView.setAdapter(recipeAdapter);

        retrieveRecipes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.refresh) {
            retrieveRecipes();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void retrieveRecipes() {
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        new Retrofit.Builder()
                .baseUrl("http://go.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build()
                .create(RecipeService.class).getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                                   @NonNull Response<List<Recipe>> response) {
                recipes = response.body();
                RecipeAdapter recipeAdapter = new RecipeAdapter(recipes, RecipesActivity.this);
                recipesRecyclerView.setAdapter(recipeAdapter);
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }
            }
        });
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }

    @VisibleForTesting
    public List<Recipe> getRecipes() {
        return recipes;
    }
}
