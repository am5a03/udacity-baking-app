package com.raymond.udacity.bakingapp.ui.main;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.ui.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChooseRecipeToAddViewModel extends BaseViewModel {
    final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
    final MutableLiveData<Recipe> recipeChosenLiveData = new MutableLiveData<>();
    final View.OnClickListener clickListener = v -> {
        final Recipe recipe = (Recipe) v.getTag();
        recipeChosenLiveData.postValue(recipe);
    };

    @Inject
    ChooseRecipeToAddViewModel() {}

    void loadRecipe() {
        disposable.add(
                recipeRepository.getAllRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipeLiveData::postValue)
        );
    }
}
