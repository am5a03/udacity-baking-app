package com.raymond.udacity.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.raymond.udacity.bakingapp.repository.RecipeRepository;
import com.raymond.udacity.bakingapp.ui.main.ChooseRecipeToAddViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class RecipeUpdateService extends JobIntentService {
    public static final String KEY_RECIPE_ID = "recipe_id";

    @Inject
    RecipeRepository recipeRepository;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Timber.d("intent=" + intent +
                ", thread=" + Thread.currentThread()
        );
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        for (int appWidgetId : appWidgetIds) {
            final int recipeId = sharedPreferences.getInt(ChooseRecipeToAddViewModel.KEY_WIDGET_RECIPE_PAIR + appWidgetId, -1);
            if (recipeId > 0) {
                recipeRepository.getRecipeById(recipeId)
                        .subscribe(recipe ->
                                RecipeWidgetProvider.updateViews(getApplicationContext(), recipe, appWidgetManager, appWidgetId)
                        );
            }
        }
    }

    public static void updateRecipeById(Context context) {
        final Intent intent = new Intent();
        JobIntentService.enqueueWork(context.getApplicationContext(), RecipeUpdateService.class, 0, intent);
    }
}
