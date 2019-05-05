package com.raymond.udacity.bakingapp.di;

import com.raymond.udacity.bakingapp.ui.widget.RecipeUpdateService;
import com.raymond.udacity.bakingapp.ui.widget.RecipeIngredientListWidgetService;
import com.raymond.udacity.bakingapp.ui.widget.RecipeWidgetProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBindingModule {

    @ServiceScoped
    @ContributesAndroidInjector
    abstract RecipeIngredientListWidgetService recipeStepListWidgetService();

    @ServiceScoped
    @ContributesAndroidInjector
    abstract RecipeUpdateService recipeStepListUpdateService();

    // FIXME: 4/28/2019 Should be a BroadcastReceiver btw...
    @ServiceScoped
    @ContributesAndroidInjector
    abstract RecipeWidgetProvider recipeWidgetProvider();
}
