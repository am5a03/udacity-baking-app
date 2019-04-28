package com.raymond.udacity.bakingapp.ui.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.raymond.udacity.bakingapp.repository.RecipeRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class RecipeStepListWidgetService extends RemoteViewsService {
    @Inject
    RecipeRepository recipeRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeListRemoteViewsFactory(recipeRepository);
    }
}

class RecipeListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private RecipeRepository recipeRepository;

    RecipeListRemoteViewsFactory(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
