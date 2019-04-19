package com.raymond.udacity.bakingapp.di;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.raymond.udacity.bakingapp.MainApplication;
import com.raymond.udacity.bakingapp.repository.AppDatabase;
import com.raymond.udacity.bakingapp.repository.RecipeDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Provides
    @Singleton
    @NonNull
    AppDatabase provideDatabase(MainApplication application) {
        return Room.databaseBuilder(application, AppDatabase.class, "recipe.db").build();
    }

    @Provides
    @Singleton
    @NonNull
    RecipeDao provideRecipeDao(AppDatabase database) {
        return database.getRecipeDao();
    }
}
