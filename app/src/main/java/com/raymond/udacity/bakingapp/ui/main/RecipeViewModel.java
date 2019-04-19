package com.raymond.udacity.bakingapp.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeViewModel extends ViewModel {

    @Inject
    RecipeRepository recipeRepository;
    final CompositeDisposable disposable = new CompositeDisposable();
    final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();


    @Inject
    public RecipeViewModel() {

    }

    void loadRecipe() {
        disposable.add(
                recipeRepository.getAllRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipeLiveData::postValue)
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
