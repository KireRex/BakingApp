package com.scheffer.erik.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.scheffer.erik.bakingapp.adapters.RecipeAdapter;
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
    List<Recipe> recipes;
    LinearLayoutManager recipesLayoutManager;

    @BindView(R.id.recipes_recycle_view)
    RecyclerView recipesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        recipesRecyclerView.setHasFixedSize(true);
        recipesLayoutManager = new LinearLayoutManager(this);
        recipesRecyclerView.setLayoutManager(recipesLayoutManager);
        recipesRecyclerView.addItemDecoration(
                new DividerItemDecoration(recipesRecyclerView.getContext(),
                                          recipesLayoutManager.getOrientation()));
        RecipeAdapter recipeAdapter = new RecipeAdapter(new ArrayList<Recipe>());
        recipesRecyclerView.setAdapter(recipeAdapter);

        getRecipes();
    }

    private void getRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://go.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        retrofit.create(RecipeService.class).getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                                   @NonNull Response<List<Recipe>> response) {
                recipes = response.body();
                RecipeAdapter recipeAdapter = new RecipeAdapter(recipes);
                recipesRecyclerView.setAdapter(recipeAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
