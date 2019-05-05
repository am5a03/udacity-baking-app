package com.raymond.udacity.bakingapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.SimpleFragmentHolderActivity;
import com.raymond.udacity.bakingapp.models.db.Ingredient;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.repository.RecipeRepository;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class RecipeIngredientListWidgetService extends RemoteViewsService {
    @Inject
    RecipeRepository recipeRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeIngredientListRemoteViewsFactory(getApplicationContext(), recipeRepository, intent);
    }
}

class RecipeIngredientListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private RecipeRepository recipeRepository;
    private Intent intent;
    private Recipe recipe;
    private List<Ingredient> ingredients;
    private CompositeDisposable disposables;

    RecipeIngredientListRemoteViewsFactory(Context context, RecipeRepository recipeRepository, Intent intent) {
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
        final int recipeId = this.intent.getIntExtra(RecipeUpdateService.KEY_RECIPE_ID, -1);
        if (recipeId == -1) return;

        disposables.addAll(this.recipeRepository.getRecipeById(recipeId)
                .map(recipe -> {
                    this.recipe = recipe;
                    return Arrays.asList(recipe.ingredients);
                })
                .subscribe(ingredients -> {
                    this.ingredients = ingredients;
                    Timber.d("Thread=" + Thread.currentThread());
                }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }

    @Override
    public int getCount() {
        if (this.ingredients == null) return 0;
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, ingredients.get(position).ingredient);
        final Intent fillIntent = new Intent();

        final Bundle masterBundle = new Bundle();
        masterBundle.putInt(RecipeStepListFragment.KEY_RECIPE_ID, recipe.id);
        masterBundle.putBoolean(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED, true);
        masterBundle.putString(SimpleFragmentHolderActivity.KEY_TITLE, recipe.name);

        final Bundle detailBundle = new Bundle();
        detailBundle.putInt(RecipeAllDetailFragment.KEY_RECIPE_ID, recipe.id);
        detailBundle.putInt(RecipeAllDetailFragment.KEY_STEP_ID, 0);

        fillIntent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_ARGS, masterBundle);
        fillIntent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_DETAIL_ARGS, detailBundle);

        fillIntent.putExtra(SimpleFragmentHolderActivity.KEY_TITLE, masterBundle.getString(SimpleFragmentHolderActivity.KEY_TITLE));
        fillIntent.putExtra(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED, masterBundle.getBoolean(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED));
        fillIntent.putExtra(SimpleFragmentHolderActivity.KEY_SUPPORT_LANDSCAPE_FULL_SCREEN_MODE, masterBundle.getBoolean(SimpleFragmentHolderActivity.KEY_SUPPORT_LANDSCAPE_FULL_SCREEN_MODE));

        remoteViews.setOnClickFillInIntent(android.R.id.text1, fillIntent);
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
