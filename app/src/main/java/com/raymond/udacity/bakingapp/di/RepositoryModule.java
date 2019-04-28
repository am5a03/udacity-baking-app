package com.raymond.udacity.bakingapp.di;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import com.raymond.udacity.bakingapp.MainApplication;
import com.raymond.udacity.bakingapp.api.ApiService;
import com.raymond.udacity.bakingapp.repository.AppDatabase;
import com.raymond.udacity.bakingapp.repository.RecipeRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    @NonNull
    RecipeRepository provideRecipeRepository(ApiService apiService,
                                             AppDatabase database,
                                             SharedPreferences preferences) {
        return new RecipeRepository(apiService, database, preferences);
    }

    @Provides
    @Singleton
    @NonNull
    SharedPreferences provideSharedPreference(MainApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
