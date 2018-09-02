package com.scheffer.erik.bakingapp.services

import com.scheffer.erik.bakingapp.models.Recipe

import retrofit2.Call
import retrofit2.http.GET

interface RecipeService {
    @GET("android-baking-app-json")
    fun recipes(): Call<List<Recipe>>
}
