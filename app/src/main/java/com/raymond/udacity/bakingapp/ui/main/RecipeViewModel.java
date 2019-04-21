package com.raymond.udacity.bakingapp.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.raymond.udacity.bakingapp.SimpleFragmentHolderActivity;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.ui.BaseViewModel;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipeViewModel extends BaseViewModel {


    final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
    final MutableLiveData<Bundle> recipeBundleClickLiveData = new MutableLiveData<>();
    final View.OnClickListener clickListener = v -> {
        final Recipe recipe = (Recipe) v.getTag();
        final Bundle args = new Bundle();
        args.putInt(RecipeStepListFragment.KEY_RECIPE_ID, recipe.id);
        args.putBoolean(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED, true);
        args.putString(SimpleFragmentHolderActivity.KEY_TITLE, recipe.name);
        recipeBundleClickLiveData.postValue(args);
    };

    @Inject
    RecipeViewModel() { }

    void loadRecipe() {
        disposable.add(
                recipeRepository.getAllRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipeLiveData::postValue)
        );
    }
}
