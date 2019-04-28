package com.raymond.udacity.bakingapp.ui.step;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.SimpleFragmentHolderActivity;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.models.db.Step;
import com.raymond.udacity.bakingapp.ui.BaseViewModel;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment.KEY_STEP_LIST_INDEX;

public class RecipeStepListViewModel extends BaseViewModel {
    private Recipe recipe;
    final MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();
    final MutableLiveData<Bundle> stepClickLiveData = new MutableLiveData<>();

    final View.OnClickListener stepClickListener = v -> {
        final Step step = (Step) v.getTag();
        final int stepIndex = (int) v.getTag(R.id.step_list_position);
        final Bundle bundle = new Bundle();

        bundle.putInt(RecipeAllDetailFragment.KEY_RECIPE_ID, this.recipe.id);
        bundle.putInt(RecipeAllDetailFragment.KEY_STEP_ID, step.id);
        bundle.putInt(KEY_STEP_LIST_INDEX, stepIndex);
        bundle.putBoolean(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED, true);
        bundle.putString(SimpleFragmentHolderActivity.KEY_TITLE, this.recipe.name);

        stepClickLiveData.postValue(bundle);
    };

    @Inject
    RecipeStepListViewModel() {}

    void loadRecipeSteps(int id) {
        disposable.add(
                recipeRepository.getRecipeById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipe -> {
                            RecipeStepListViewModel.this.recipe = recipe;
                            recipeLiveData.postValue(recipe);
                        })
        );
    }
}
