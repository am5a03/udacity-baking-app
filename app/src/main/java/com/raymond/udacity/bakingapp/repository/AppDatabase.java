package com.raymond.udacity.bakingapp.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.raymond.udacity.bakingapp.models.DbTypeConverter;
import com.raymond.udacity.bakingapp.models.db.Recipe;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
@TypeConverters({DbTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    abstract public RecipeDao getRecipeDao();
}
