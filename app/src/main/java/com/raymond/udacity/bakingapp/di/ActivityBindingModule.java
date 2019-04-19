package com.raymond.udacity.bakingapp.di;

import com.raymond.udacity.bakingapp.MainActivity;
import com.raymond.udacity.bakingapp.ui.main.RecipeModule;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = {
                    RecipeModule.class,
                    RecipeStepListModule.class
            }
    )
    abstract MainActivity mainActivity();
}
