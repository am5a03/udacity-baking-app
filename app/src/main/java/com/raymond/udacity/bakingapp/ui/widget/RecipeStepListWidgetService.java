package com.raymond.udacity.bakingapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.models.db.Step;
import com.raymond.udacity.bakingapp.repository.RecipeRepository;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

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
        return new RecipeListRemoteViewsFactory(getApplicationContext(), recipeRepository, intent);
    }
}

class RecipeListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private RecipeRepository recipeRepository;
    private Intent intent;
    private List<Step> steps;
    private CompositeDisposable disposables;

    RecipeListRemoteViewsFactory(Context context, RecipeRepository recipeRepository, Intent intent) {
        this.context = context;
        this.recipeRepository = recipeRepository;
        this.intent = intent;
    }


    @Override
    public void onCreate() {
        disposables = new CompositeDisposable();
    }

    @Override
    public void onDataSetChanged() {
        final int recipeId = this.intent.getIntExtra(RecipeStepListUpdateService.KEY_RECIPE_ID, -1);
        if (recipeId == -1) return;

        disposables.addAll(this.recipeRepository.getRecipeById(recipeId)
                .map(recipe -> Arrays.asList(recipe.steps))
                .subscribe(steps -> {
                    this.steps = steps;
                    Timber.d("Thread=" + Thread.currentThread());
                }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }

    @Override
    public int getCount() {
        if (this.steps == null) return 0;
        return steps.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, steps.get(position).shortDescription);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(context.getPackageName(), R.layout.view_progress);
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
