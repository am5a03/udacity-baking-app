package com.raymond.udacity.bakingapp.ui.main;

import android.content.SharedPreferences;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.ui.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChooseRecipeToAddViewModel extends BaseViewModel {
    public static final String KEY_WIDGET_RECIPE_PAIR = "widget_recipe_pair_";
    final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
    final MutableLiveData<Recipe> recipeChosenLiveData = new MutableLiveData<>();
    final View.OnClickListener clickListener = v -> {
        final Recipe recipe = (Recipe) v.getTag();
        recipeChosenLiveData.postValue(recipe);
    };

    private final SharedPreferences sharedPreferences;

    @Inject
    ChooseRecipeToAddViewModel(SharedPreferences sharedPreferences) {
          this.sharedPreferences = sharedPreferences;
    }

    void loadRecipe() {
        disposable.add(
                recipeRepository.getAllRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipeLiveData::postValue)
        );
    }

    void saveWidgetState(int receipeId, int appWidgetId) {
        sharedPreferences.edit()
                .putInt(KEY_WIDGET_RECIPE_PAIR + appWidgetId, receipeId)
                .apply();
    }
}
