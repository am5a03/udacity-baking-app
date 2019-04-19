package com.raymond.udacity.bakingapp.ui.step;

import androidx.lifecycle.MutableLiveData;

import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.ui.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RecipeStepListViewModel extends BaseViewModel {
    final MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();

    @Inject
    public RecipeStepListViewModel() {}

    public void loadRecipeSteps(int id) {
        disposable.add(
                recipeRepository.getRecipeById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipeLiveData::postValue)
        );
    }
}
