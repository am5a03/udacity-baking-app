package com.raymond.udacity.bakingapp.di;

import com.raymond.udacity.bakingapp.ui.widget.RecipeStepListUpdateService;
import com.raymond.udacity.bakingapp.ui.widget.RecipeStepListWidgetService;
import com.raymond.udacity.bakingapp.ui.widget.RecipeWidgetProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBindingModule {

    @ServiceScoped
    @ContributesAndroidInjector
    abstract RecipeStepListWidgetService recipeStepListWidgetService();

    @ServiceScoped
    @ContributesAndroidInjector
    abstract RecipeStepListUpdateService recipeStepListUpdateService();

    // FIXME: 4/28/2019 Should be a BroadcastReceiver btw...
    @ServiceScoped
    @ContributesAndroidInjector
    abstract RecipeWidgetProvider recipeWidgetProvider();
}
