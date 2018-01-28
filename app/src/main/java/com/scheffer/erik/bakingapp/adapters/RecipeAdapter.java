package com.scheffer.erik.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scheffer.erik.bakingapp.R;
import com.scheffer.erik.bakingapp.StepListActivity;
import com.scheffer.erik.bakingapp.models.Ingredient;
import com.scheffer.erik.bakingapp.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.scheffer.erik.bakingapp.StepListActivity.RECIPE_EXTRA_KEY;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public static final String SHARED_PREFERENCES_NAME = "sharedpref";
    public static final String INGREDIENTS_PREFERENCES_KEY = "ingredients-pref-key";

    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeNameText.setText(recipe.getName());
        holder.recipe = recipe;
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name_text)
        TextView recipeNameText;

        Recipe recipe;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveIngredientsOnSharedPreferences(itemView.getContext(), recipe);

                    Intent intent = new Intent(itemView.getContext(), StepListActivity.class);
                    intent.putExtra(RECIPE_EXTRA_KEY, recipe);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        private void saveIngredientsOnSharedPreferences(Context context, Recipe recipe) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    SHARED_PREFERENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            StringBuilder builder = new StringBuilder();
            for (Ingredient ingredient : recipe.getIngredients()) {
                builder.append(context.getResources()
                                      .getString(R.string.ingredient_description,
                                                 ingredient.getQuantity(),
                                                 ingredient.getMeasure(),
                                                 ingredient.getIngredient())).append(";");
            }
            editor.putString(INGREDIENTS_PREFERENCES_KEY, builder.toString());
            editor.apply();
        }
    }
}
