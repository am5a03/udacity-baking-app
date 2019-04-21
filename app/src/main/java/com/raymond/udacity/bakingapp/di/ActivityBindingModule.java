package com.raymond.udacity.bakingapp.di;

import com.raymond.udacity.bakingapp.MainActivity;
import com.raymond.udacity.bakingapp.SimpleFragmentHolderActivity;
import com.raymond.udacity.bakingapp.ui.detail.RecipeDetailModule;
import com.raymond.udacity.bakingapp.ui.main.RecipeModule;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = {
                    RecipeModule.class
            }
    )
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = {
                    RecipeModule.class,
                    RecipeStepListModule.class,
                    RecipeDetailModule.class
            }
    )
    abstract SimpleFragmentHolderActivity simpleFragmentHolderActivity();
}
