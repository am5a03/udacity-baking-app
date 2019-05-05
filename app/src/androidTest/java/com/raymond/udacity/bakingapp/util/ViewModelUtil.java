package com.raymond.udacity.bakingapp.util;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Copied from
 * @link https://github.com/googlesamples/android-architecture-components/blob/17c315a050745c61ae8e79000bc0251305bd148b/GithubBrowserSample/app/src/androidTest/java/com/android/example/github/util/ViewModelUtil.kt
 */
public final class ViewModelUtil {
    private ViewModelUtil() {}
    public static <T extends ViewModel> ViewModelProvider.Factory createFor(final T model) {
        return new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(model.getClass())) {
                    return (T) model;
                }
                throw new IllegalArgumentException("unexpected model class " + modelClass);
            }
        };
    }
}
