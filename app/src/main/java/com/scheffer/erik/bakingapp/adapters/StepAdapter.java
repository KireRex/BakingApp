package com.scheffer.erik.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scheffer.erik.bakingapp.R;
import com.scheffer.erik.bakingapp.StepDetailActivity;
import com.scheffer.erik.bakingapp.StepDetailFragment;
import com.scheffer.erik.bakingapp.StepListActivity;
import com.scheffer.erik.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private List<Step> steps;
    private final StepListActivity parentActivity;
    private final boolean twoPane;

    public StepAdapter(List<Step> steps,
                       StepListActivity parentActivity,
                       boolean twoPane) {
        this.steps = steps;
        this.parentActivity = parentActivity;
        this.twoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Step step = steps.get(position);
        holder.stepDescriptionText.setText(step.getShortDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (twoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(StepDetailFragment.STEP_EXTRA_KEY, step);
                    StepDetailFragment fragment = new StepDetailFragment();
                    fragment.setArguments(arguments);
                    parentActivity.getSupportFragmentManager().beginTransaction()
                                  .replace(R.id.step_detail_container, fragment)
                                  .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StepDetailActivity.class);
                    intent.putParcelableArrayListExtra(StepDetailFragment.STEP_EXTRA_KEY,
                                                       new ArrayList<Parcelable>(steps));
                    intent.putExtra(StepDetailActivity.SELECTED_STEP_KEY,
                                    holder.getAdapterPosition());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_description)
        TextView stepDescriptionText;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
