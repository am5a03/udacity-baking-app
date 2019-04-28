package com.raymond.udacity.bakingapp.ui.widget;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import timber.log.Timber;

public class RecipeStepListUpdateService extends JobIntentService {
    public static final String KEY_RECIPE_ID = "recipe_id";

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Timber.d("intent=" + intent +
                ", recipe_id=" + intent.getIntExtra(KEY_RECIPE_ID, -1) +
                ", thread=" + Thread.currentThread()
        );
    }
}
