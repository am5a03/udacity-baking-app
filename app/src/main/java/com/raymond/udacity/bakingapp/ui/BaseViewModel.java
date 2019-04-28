package com.raymond.udacity.bakingapp.ui;

import androidx.lifecycle.ViewModel;

import com.raymond.udacity.bakingapp.repository.RecipeRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {
    @Inject
    protected RecipeRepository recipeRepository;
    protected final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
