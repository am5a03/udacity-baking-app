package com.raymond.udacity.bakingapp;

import androidx.lifecycle.ViewModelProvider;

import com.raymond.udacity.bakingapp.repository.RecipeRepository;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    RecipeRepository recipeRepository;
}
