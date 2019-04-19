package com.raymond.udacity.bakingapp.ui.main;

import androidx.lifecycle.ViewModel;

import com.raymond.udacity.bakingapp.di.FragmentScoped;
import com.raymond.udacity.bakingapp.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class RecipeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RecipeFragment contributeRecipeFragment();

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel.class)
    abstract ViewModel recipeViewModel(RecipeViewModel viewModel);
}
