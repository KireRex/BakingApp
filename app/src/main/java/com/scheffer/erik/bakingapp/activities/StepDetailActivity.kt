package com.scheffer.erik.bakingapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.scheffer.erik.bakingapp.R
import com.scheffer.erik.bakingapp.fragments.STEP_EXTRA_KEY
import com.scheffer.erik.bakingapp.pageadapters.StepPageAdapter
import kotlinx.android.synthetic.main.activity_step_detail.*

const val SELECTED_STEP_KEY = "selected-step"

class StepDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_detail)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = StepPageAdapter(supportFragmentManager,
                                      intent.getParcelableArrayListExtra(STEP_EXTRA_KEY),
                                      this)
        step_detail_container.adapter = adapter
        step_detail_container.currentItem = intent.getIntExtra(SELECTED_STEP_KEY, 0)
        sliding_tabs.setupWithViewPager(step_detail_container)
    }
}
