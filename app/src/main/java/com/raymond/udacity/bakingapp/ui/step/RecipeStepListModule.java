package com.raymond.udacity.bakingapp.ui.step;

import androidx.lifecycle.ViewModel;

import com.raymond.udacity.bakingapp.di.FragmentScoped;
import com.raymond.udacity.bakingapp.di.ViewModelKey;
import com.raymond.udacity.bakingapp.ui.main.RecipeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class RecipeStepListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RecipeStepListFragment contributeRecipeStepListFragment();

    @Binds
    @IntoMap
    @ViewModelKey(RecipeStepListViewModel.class)
    abstract ViewModel recipeStepListViewModel(RecipeStepListViewModel viewModel);
}
