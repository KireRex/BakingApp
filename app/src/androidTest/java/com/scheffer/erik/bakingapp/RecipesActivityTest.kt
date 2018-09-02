package com.scheffer.erik.bakingapp

import android.content.Intent
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.scheffer.erik.bakingapp.activities.RecipesActivity
import com.scheffer.erik.bakingapp.activities.StepListActivity.Companion.RECIPE_EXTRA_KEY
import com.scheffer.erik.bakingapp.models.Recipe
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipesActivityTest {
    @get:Rule var recipesActivityRule = IntentsTestRule(RecipesActivity::class.java)
    private var idlingResource: IdlingResource? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        idlingResource = recipesActivityRule.activity.idlingResource
        Espresso.registerIdlingResources(idlingResource)
    }

    @Test
    fun verifyStepListActivityOpened() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.refresh)).perform(click())

        onView(withId(R.id.recipes_recycle_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                                                                                             click()))

        intended(allOf<Intent>(hasExtra<Recipe>(RECIPE_EXTRA_KEY,
                                                recipesActivityRule.activity.recipes[0]),
                               hasComponent("com.scheffer.erik.bakingapp.activities.StepListActivity")))
    }

    @After
    fun unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource)
        }
    }
}
