package com.scheffer.erik.bakingapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

import com.scheffer.erik.bakingapp.R

class RecipeIngredientsProvider : AppWidgetProvider() {

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                appWidgetId: Int) =
            appWidgetManager.updateAppWidget(appWidgetId, getRemoveView(context))

    override fun onUpdate(context: Context,
                          appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {}

    override fun onDisabled(context: Context) {}

    private fun getRemoveView(context: Context): RemoteViews =
            RemoteViews(context.packageName,
                        R.layout.widget_grid_view).apply {
                setRemoteAdapter(R.id.widget_grid_view,
                                 Intent(context, GridWidgetService::class.java))
                setEmptyView(R.id.widget_grid_view, R.id.empty_view)
            }
}