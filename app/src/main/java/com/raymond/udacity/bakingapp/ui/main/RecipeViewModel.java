package com.raymond.udacity.bakingapp.ui.main;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.raymond.udacity.bakingapp.SimpleFragmentHolderActivity;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.ui.BaseViewModel;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RecipeViewModel extends BaseViewModel {


    final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
    final MutableLiveData<Pair<Bundle, Bundle>> recipeBundleClickLiveData = new MutableLiveData<>();
    final View.OnClickListener clickListener = new ItemClickListener(recipeBundleClickLiveData);

    @Inject
    public RecipeViewModel() { }

    void loadRecipe() {
        disposable.add(
                recipeRepository.getAllRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipeLiveData::postValue)
        );
    }

    MutableLiveData<List<Recipe>> getRecipeLiveData() {
        return recipeLiveData;
    }

    MutableLiveData<Pair<Bundle, Bundle>> getRecipeBundleClickLiveData() {
        return recipeBundleClickLiveData;
    }

    View.OnClickListener getClickListener() {
        return clickListener;
    }

    static class ItemClickListener implements View.OnClickListener {
        MutableLiveData<Pair<Bundle, Bundle>> liveData;

        public ItemClickListener(MutableLiveData<Pair<Bundle, Bundle>> liveData) {
            this.liveData = liveData;
        }


        @Override
        public void onClick(View v) {
            final Recipe recipe = (Recipe) v.getTag();
            final Bundle masterBundle = new Bundle();
            masterBundle.putInt(RecipeStepListFragment.KEY_RECIPE_ID, recipe.id);
            masterBundle.putBoolean(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED, true);
            masterBundle.putString(SimpleFragmentHolderActivity.KEY_TITLE, recipe.name);

            final Bundle detailBundle = new Bundle();
            detailBundle.putInt(RecipeAllDetailFragment.KEY_RECIPE_ID, recipe.id);
            detailBundle.putInt(RecipeAllDetailFragment.KEY_STEP_ID, 0);

            liveData.postValue(Pair.create(masterBundle, detailBundle));
        }
    }
}
