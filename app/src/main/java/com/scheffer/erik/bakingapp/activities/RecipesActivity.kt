package com.scheffer.erik.bakingapp.activities

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.gson.GsonBuilder
import com.scheffer.erik.bakingapp.R
import com.scheffer.erik.bakingapp.iddlingrsource.SimpleIdlingResource
import com.scheffer.erik.bakingapp.models.Recipe
import com.scheffer.erik.bakingapp.recyclerviewadapters.RecipeAdapter
import com.scheffer.erik.bakingapp.services.RecipeService
import kotlinx.android.synthetic.main.activity_recipes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RecipesActivity : AppCompatActivity() {
    @get:VisibleForTesting
    var recipes: List<Recipe> = ArrayList()
        private set

    @get:VisibleForTesting
    var idlingResource: SimpleIdlingResource = SimpleIdlingResource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        recipes_recycle_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RecipesActivity)
            adapter = RecipeAdapter(ArrayList(), this@RecipesActivity)
        }

        retrieveRecipes()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.refresh) {
            retrieveRecipes()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun retrieveRecipes() {
        idlingResource.setIdleState(false)
        Retrofit.Builder()
                .baseUrl("http://go.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(RecipeService::class.java).recipes().enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>,
                                    response: Response<List<Recipe>>) {
                recipes = response.body() ?: ArrayList()
                recipes_recycle_view.adapter = RecipeAdapter(recipes, this@RecipesActivity)
                idlingResource.setIdleState(true)
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                t.printStackTrace()
                idlingResource.setIdleState(true)
            }
        })
    }
}
