package com.scheffer.erik.bakingapp.services;

import com.scheffer.erik.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("android-baking-app-json")
    Call<List<Recipe>> getRecipes();
}
