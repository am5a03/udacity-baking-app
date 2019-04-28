package com.raymond.udacity.bakingapp.ui.detail;

import androidx.lifecycle.MutableLiveData;

import com.raymond.udacity.bakingapp.models.db.Step;
import com.raymond.udacity.bakingapp.ui.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RecipeDetailViewModel extends BaseViewModel {
    @Inject public RecipeDetailViewModel() {}

    final MutableLiveData<StepWrapper> stepLiveData = new MutableLiveData<>();

    void loadStep(int recipeId, int stepId) {
        disposable.add(
                recipeRepository.getStepByRecipe(recipeId, stepId)
                        .map(StepWrapper::new)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(stepLiveData::postValue, Timber::e)
        );
    }

    static class StepWrapper {
        final Step step;
        final boolean hasVideo;

        StepWrapper(Step step) {
            this.step = step;
            this.hasVideo = step.videoURL != null && !step.videoURL.isEmpty();
        }
    }
}
