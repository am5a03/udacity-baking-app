package com.raymond.udacity.bakingapp.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeStepListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeListRemoteViewsFactory(getApplicationContext());
    }
}
