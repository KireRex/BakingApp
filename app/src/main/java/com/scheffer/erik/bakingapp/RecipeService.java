package com.scheffer.erik.bakingapp;

import com.scheffer.erik.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("android-baking-app-json")
    Call<List<Recipe>> getRecepies();
}
