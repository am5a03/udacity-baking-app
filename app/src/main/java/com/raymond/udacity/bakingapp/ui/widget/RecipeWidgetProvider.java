package com.raymond.udacity.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.SimpleFragmentHolderActivity;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;
import com.raymond.udacity.bakingapp.ui.main.ChooseRecipeToAddViewModel;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    @Inject
    SharedPreferences sharedPreferences;

    public static void updateViews(Context context, Recipe recipe, AppWidgetManager appWidgetManager, int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        final Intent intent = new Intent(context, RecipeIngredientListWidgetService.class);
        intent.putExtra(RecipeUpdateService.KEY_RECIPE_ID, recipe.id);
        views.setTextViewText(R.id.widget_recipe_title, recipe.name);
        views.setRemoteAdapter(R.id.widget_recipe_step_list, intent);

        final Intent appIntent = new Intent(context, SimpleFragmentHolderActivity.class);
        appIntent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_CLASS, RecipeStepListFragment.class.getName());
        appIntent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_DETAIL_CLASS, RecipeAllDetailFragment.class.getName());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_recipe_step_list, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            final int recipeId =
                    sharedPreferences.getInt(ChooseRecipeToAddViewModel.KEY_WIDGET_RECIPE_PAIR + appWidgetId, -999);
            if (recipeId > 0) {
                RecipeUpdateService.updateRecipeById(context);
            }
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Timber.d("disabled=" + AppWidgetManager.getInstance(context));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            sharedPreferences.edit()
                    .remove(ChooseRecipeToAddViewModel.KEY_WIDGET_RECIPE_PAIR + appWidgetId)
                    .apply();
        }
    }
}

