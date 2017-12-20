package com.scheffer.erik.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.GsonBuilder;
import com.scheffer.erik.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesActivity extends AppCompatActivity {
    List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        getRecepies();
    }

    private void getRecepies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://go.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        retrofit.create(RecipeService.class).getRecepies().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                                   @NonNull Response<List<Recipe>> response) {
                recipes = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
