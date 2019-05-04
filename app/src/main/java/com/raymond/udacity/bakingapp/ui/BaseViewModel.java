package com.raymond.udacity.bakingapp.ui;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;

import com.raymond.udacity.bakingapp.repository.RecipeRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static java.lang.reflect.Modifier.PROTECTED;

public abstract class BaseViewModel extends ViewModel {
    @Inject
    @VisibleForTesting(otherwise = PROTECTED)
    public RecipeRepository recipeRepository;
    protected final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
