package com.scheffer.erik.bakingapp.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.scheffer.erik.bakingapp.R
import com.scheffer.erik.bakingapp.recyclerviewadapters.INGREDIENTS_PREFERENCES_KEY
import com.scheffer.erik.bakingapp.recyclerviewadapters.SHARED_PREFERENCES_NAME
import java.util.*

class GridRemoteViewsFactory internal constructor(private val context: Context)
    : RemoteViewsService.RemoteViewsFactory {
    override fun onCreate() { }

    private var ingredients: List<String> = ArrayList()


    override fun onDataSetChanged() {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                                                      Context.MODE_PRIVATE)
        val widgetText = sharedPref.getString(INGREDIENTS_PREFERENCES_KEY, "")
        if (!widgetText.isEmpty()) {
            ingredients = widgetText.split(";").filter { !it.isBlank() }
        }
    }

    override fun onDestroy() {}

    override fun getCount() = ingredients.size

    override fun getViewAt(position: Int): RemoteViews =
            RemoteViews(context.packageName,
                        R.layout.recipe_ingredients_provider).apply {
                setTextViewText(R.id.appwidget_text, ingredients[position])
            }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true
}