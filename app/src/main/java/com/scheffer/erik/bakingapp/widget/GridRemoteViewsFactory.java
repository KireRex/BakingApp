package com.scheffer.erik.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.scheffer.erik.bakingapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.scheffer.erik.bakingapp.adapters.RecipeAdapter.INGREDIENTS_PREFERENCES_KEY;
import static com.scheffer.erik.bakingapp.adapters.RecipeAdapter.SHARED_PREFERENCES_NAME;


public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<String> ingredients = new ArrayList<>();

    GridRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                                                                    Context.MODE_PRIVATE);
        String widgetText = sharedPref.getString(INGREDIENTS_PREFERENCES_KEY, "");
        if (!widgetText.isEmpty()) {
            ingredients = new ArrayList<>(Arrays.asList(widgetText.split(";")));
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                                            R.layout.recipe_ingredients_provider);
        views.setTextViewText(R.id.appwidget_text, ingredients.get(position));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}