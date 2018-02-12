package com.scheffer.erik.bakingapp.recyclerviewadapters

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.scheffer.erik.bakingapp.R
import com.scheffer.erik.bakingapp.activities.SELECTED_STEP_KEY
import com.scheffer.erik.bakingapp.activities.StepDetailActivity
import com.scheffer.erik.bakingapp.activities.StepListActivity
import com.scheffer.erik.bakingapp.fragments.STEP_EXTRA_KEY
import com.scheffer.erik.bakingapp.fragments.StepDetailFragment
import com.scheffer.erik.bakingapp.models.Step
import org.jetbrains.anko.startActivity
import java.util.*

class StepAdapter(private val steps: List<Step>,
                  private val parentActivity: StepListActivity,
                  private val twoPane: Boolean) : RecyclerView.Adapter<StepAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                               .inflate(R.layout.step_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = steps[position]
        holder.stepDescriptionText.text = step.shortDescription
        holder.itemView.setOnClickListener { view ->
            if (twoPane) {
                val fragment = StepDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(STEP_EXTRA_KEY, step)
                    }
                }
                parentActivity.supportFragmentManager.beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit()
            } else {
                view.context.startActivity<StepDetailActivity>(
                        STEP_EXTRA_KEY to ArrayList<Parcelable>(steps),
                        SELECTED_STEP_KEY to holder.adapterPosition
                )
            }
        }
    }

    override fun getItemCount() = steps.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stepDescriptionText: TextView = itemView.findViewById(R.id.step_description)
    }
}
