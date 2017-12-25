package com.scheffer.erik.bakingapp.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scheffer.erik.bakingapp.R;
import com.scheffer.erik.bakingapp.StepListActivity;
import com.scheffer.erik.bakingapp.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.scheffer.erik.bakingapp.StepListActivity.RECIPE_EXTRA_KEY;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

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
                    Intent intent = new Intent(itemView.getContext(), StepListActivity.class);
                    intent.putExtra(RECIPE_EXTRA_KEY, recipe);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
