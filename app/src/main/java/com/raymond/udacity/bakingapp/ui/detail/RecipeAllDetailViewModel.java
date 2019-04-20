package com.raymond.udacity.bakingapp.ui.detail;

import androidx.lifecycle.MutableLiveData;

import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.ui.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipeAllDetailViewModel extends BaseViewModel {
    @Inject public RecipeAllDetailViewModel() { }

    final MutableLiveData<Recipe> recipeMutableLiveData = new MutableLiveData<>();

    void loadRecipeSteps(int recipeId) {
        disposable.add(
                recipeRepository.getRecipeById(recipeId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipeMutableLiveData::postValue)
        );
    }
}
