package com.raymond.udacity.bakingapp.di;

import androidx.annotation.NonNull;

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
    RecipeRepository provideRecipeRepository(ApiService apiService, AppDatabase database) {
        return new RecipeRepository(apiService, database);
    }
}
