package com.scheffer.erik.bakingapp.recyclerviewadapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.scheffer.erik.bakingapp.R
import com.scheffer.erik.bakingapp.models.Ingredient

class IngredientAdapter(private val ingredients: List<Ingredient>,
                        private val context: Context) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                               .inflate(R.layout.ingredient_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredientText.text = context.resources
                .getString(R.string.ingredient_description,
                           ingredients[position].quantity,
                           ingredients[position].measure,
                           ingredients[position].ingredient)
    }

    override fun getItemCount() = ingredients.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ingredientText: TextView = itemView.findViewById(R.id.ingredient_text)
    }
}
