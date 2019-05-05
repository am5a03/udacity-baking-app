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
import com.raymond.udacity.bakingapp.util.Util;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment.KEY_STEP_LIST_INDEX;

public class RecipeStepListViewModel extends BaseViewModel {
    final MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();
    final MutableLiveData<Bundle> stepClickLiveData = new MutableLiveData<>();
    final ItemClickListener stepClickListener = new ItemClickListener(stepClickLiveData);

    @Inject
    RecipeStepListViewModel() {}

    void loadRecipeSteps(int id) {
        disposable.add(
                recipeRepository.getRecipeById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipe -> {
                            stepClickListener.setRecipe(recipe);
                            recipeLiveData.postValue(recipe);
                        })
        );
    }

    public MutableLiveData<Recipe> getRecipeLiveData() {
        return recipeLiveData;
    }

    public MutableLiveData<Bundle> getStepClickLiveData() {
        return stepClickLiveData;
    }

    public View.OnClickListener getStepClickListener() {
        return stepClickListener;
    }

    static class ItemClickListener implements View.OnClickListener {
        private MutableLiveData<Bundle> liveData;
        private Recipe recipe;

        public ItemClickListener(MutableLiveData<Bundle> liveData) {
            this.liveData = liveData;
        }

        public void setRecipe(Recipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public void onClick(View v) {
            final Step step = (Step) v.getTag();
            final int stepIndex = (int) v.getTag(R.id.step_list_position);
            liveData.postValue(Util.createBundleFromStep(recipe, step, stepIndex));
        }
    }
}
