package com.scheffer.erik.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private List<Step> steps;
    private final StepListActivity mParentActivity;
    private final boolean mTwoPane;

    public StepAdapter(List<Step> steps,
                       StepListActivity mParentActivity,
                       boolean mTwoPane) {
        this.steps = steps;
        this.mParentActivity = mParentActivity;
        this.mTwoPane = mTwoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Step step = steps.get(position);
        holder.stepDescriptionText.setText(step.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(StepDetailFragment.STEP_EXTRA_KEY, step);
                    StepDetailFragment fragment = new StepDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.step_detail_container, fragment)
                                   .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StepDetailActivity.class);
                    intent.putExtra(StepDetailFragment.STEP_EXTRA_KEY, step);
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
