package com.scheffer.erik.bakingapp.recyclerviewadapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.scheffer.erik.bakingapp.R
import com.scheffer.erik.bakingapp.activities.StepListActivity
import com.scheffer.erik.bakingapp.activities.StepListActivity.Companion.RECIPE_EXTRA_KEY
import com.scheffer.erik.bakingapp.models.Recipe
import com.squareup.picasso.Picasso
import org.jetbrains.anko.startActivity

class RecipeAdapter(private val recipes: List<Recipe>,
                    private val context: Context) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                               .inflate(R.layout.recipe_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeNameText.text = recipe.name
        holder.recipe = recipe

        if (!recipe.image.isEmpty()) {
            holder.recipeImage.visibility = View.VISIBLE
            Picasso.with(context)
                    .load(recipe.image)
                    .into(holder.recipeImage)
        } else {
            holder.recipeImage.visibility = View.GONE
        }
    }

    override fun getItemCount() = recipes.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeNameText: TextView = itemView.findViewById(R.id.recipe_name_text)
        val recipeImage: ImageView = itemView.findViewById(R.id.recipe_image)
        lateinit var recipe: Recipe

        init {
            itemView.setOnClickListener {
                saveIngredientsOnSharedPreferences(itemView.context, recipe)
                itemView.context.startActivity<StepListActivity>(RECIPE_EXTRA_KEY to recipe)
            }
        }

        private fun saveIngredientsOnSharedPreferences(context: Context, recipe: Recipe) =
                context.getSharedPreferences(
                        SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE).edit().apply {
                    val builder = StringBuilder()
                    for (ingredient in recipe.ingredients) {
                        builder.append(context.resources
                                               .getString(R.string.ingredient_description,
                                                          ingredient.quantity,
                                                          ingredient.measure,
                                                          ingredient.ingredient)).append(";")
                    }
                    putString(INGREDIENTS_PREFERENCES_KEY, builder.toString())
                }.apply()
    }

    companion object {
        const val SHARED_PREFERENCES_NAME = "sharedpref"
        const val INGREDIENTS_PREFERENCES_KEY = "ingredients-pref-key"
    }
}
