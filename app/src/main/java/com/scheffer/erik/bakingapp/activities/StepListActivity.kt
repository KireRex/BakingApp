package com.scheffer.erik.bakingapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scheffer.erik.bakingapp.R
import com.scheffer.erik.bakingapp.models.Recipe
import com.scheffer.erik.bakingapp.recyclerviewadapters.IngredientAdapter
import com.scheffer.erik.bakingapp.recyclerviewadapters.StepAdapter
import kotlinx.android.synthetic.main.activity_step_list.*
import kotlinx.android.synthetic.main.step_list.*

const val RECIPE_EXTRA_KEY = "recipe"

class StepListActivity : AppCompatActivity() {

    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_list)

        val (_, recipeName, ingredients, steps) = intent.getParcelableExtra<Recipe>(RECIPE_EXTRA_KEY)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = recipeName
        }

        mTwoPane = findViewById<View>(R.id.step_detail_container) != null

        val layoutManager = LinearLayoutManager(this)
        step_list.let {
            it.layoutManager = layoutManager
            it.adapter = StepAdapter(steps, this, mTwoPane)
            it.addItemDecoration(DividerItemDecoration(it.context, layoutManager.orientation))
        }

        ingredients_list.adapter = IngredientAdapter(ingredients, this)
    }
}
