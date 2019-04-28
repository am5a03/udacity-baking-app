package com.raymond.udacity.bakingapp.ui.detail;

import androidx.lifecycle.ViewModel;

import com.raymond.udacity.bakingapp.di.FragmentScoped;
import com.raymond.udacity.bakingapp.di.ViewModelKey;
import com.raymond.udacity.bakingapp.ui.main.RecipeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class RecipeDetailModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RecipeAllDetailFragment contributeRecipeAllDetailFragment();

    @Binds
    @IntoMap
    @ViewModelKey(RecipeAllDetailViewModel.class)
    abstract ViewModel recipeAllDetailViewModel(RecipeAllDetailViewModel viewModel);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RecipeDetailFragment contributeRecipeDetailFragment();

    @Binds
    @IntoMap
    @ViewModelKey(RecipeDetailViewModel.class)
    abstract ViewModel recipeDetailViewModel(RecipeDetailViewModel viewModel);
}
