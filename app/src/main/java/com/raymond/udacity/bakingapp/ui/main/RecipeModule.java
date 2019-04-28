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

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ChooseRecipeToAddFragment contributeChooseRecipeToAddFragment();

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel.class)
    abstract ViewModel recipeViewModel(RecipeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChooseRecipeToAddViewModel.class)
    abstract ViewModel chooseRecipeToAddViewModel(ChooseRecipeToAddViewModel viewModel);

}
