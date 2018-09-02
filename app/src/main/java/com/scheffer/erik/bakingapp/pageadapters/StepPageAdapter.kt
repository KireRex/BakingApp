package com.scheffer.erik.bakingapp.pageadapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.scheffer.erik.bakingapp.R
import com.scheffer.erik.bakingapp.fragments.STEP_EXTRA_KEY
import com.scheffer.erik.bakingapp.fragments.StepDetailFragment
import com.scheffer.erik.bakingapp.models.Step


class StepPageAdapter(fm: FragmentManager,
                      private val steps: List<Step>,
                      private val context: Context) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) =
            StepDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(STEP_EXTRA_KEY, steps[position])
                }
            }

    override fun getCount() = steps.size

    override fun getPageTitle(position: Int): CharSequence = context.resources.getString(R.string.step_tab_title,
                                                                                         position)
}
