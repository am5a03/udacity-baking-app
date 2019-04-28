package com.raymond.udacity.bakingapp.di;

import com.raymond.udacity.bakingapp.ui.widget.RecipeStepListUpdateService;
import com.raymond.udacity.bakingapp.ui.widget.RecipeStepListWidgetService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBindingModule {

    @ServiceScoped
    @ContributesAndroidInjector
    abstract RecipeStepListWidgetService recipeStepListWidgetService();
}
