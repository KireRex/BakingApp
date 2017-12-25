package com.scheffer.erik.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scheffer.erik.bakingapp.R;
import com.scheffer.erik.bakingapp.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private Context context;

    public IngredientAdapter(List<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.ingredientText.setText(context.getResources()
                                             .getString(R.string.ingredient_description,
                                                        ingredient.getQuantity(),
                                                        ingredient.getMeasure(),
                                                        ingredient.getIngredient()));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name_text)
        TextView ingredientText;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
