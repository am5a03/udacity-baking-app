package com.raymond.udacity.bakingapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.tabs.TabLayout;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.ui.BaseViewModel;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment.KEY_STEP_ID;

public class RecipeAllDetailViewModel extends BaseViewModel {
    @Inject public RecipeAllDetailViewModel() { }

    final MutableLiveData<Recipe> recipeMutableLiveData = new MutableLiveData<>();
    final MutableLiveData<Integer> selectViewPagerItemLiveData = new MutableLiveData<>();
    final TabLayout.OnTabSelectedListener tablayoutOnClickListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            currentTabPos = tab.getPosition();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
    private int currentTabPos = 0;

    void handleBroadcast(final Intent intent) {
        Timber.d("handleBroadcast" + intent);
        final Bundle bundle = intent.getBundleExtra(RecipeStepListFragment.KEY_ACTION_SELECT_STEP);
        selectViewPagerItemLiveData.postValue(bundle.getInt(KEY_STEP_ID));
    }

    void saveInstanceState(Bundle outState) {
        outState.putInt(KEY_STEP_ID, currentTabPos);
    }

    void restoreInstanecState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            selectViewPagerItemLiveData.postValue(savedInstanceState.getInt(KEY_STEP_ID));
        }
    }

    void loadRecipeSteps(int recipeId) {
        disposable.add(
                recipeRepository.getRecipeById(recipeId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipeMutableLiveData::postValue)
        );
    }

    public MutableLiveData<Integer> getSelectViewPagerItemLiveData() {
        return selectViewPagerItemLiveData;
    }

    public MutableLiveData<Recipe> getRecipeMutableLiveData() {
        return recipeMutableLiveData;
    }

    public TabLayout.OnTabSelectedListener getTablayoutOnClickListener() {
        return tablayoutOnClickListener;
    }
}
