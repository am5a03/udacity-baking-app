package com.raymond.udacity.bakingapp.models;

import androidx.room.TypeConverter;

import com.raymond.udacity.bakingapp.models.db.Ingredient;
import com.raymond.udacity.bakingapp.models.db.Step;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import timber.log.Timber;

public final class DbTypeConverter {
    private static final DbTypeConverter instance = new DbTypeConverter();
    private final Moshi moshi = new Moshi.Builder().build();
    private final JsonAdapter<Step> stepJsonAdapter = moshi.adapter(Step.class);
    private final JsonAdapter<Ingredient> ingredientJsonAdapter = moshi.adapter(Ingredient.class);

    private final JsonAdapter<Step[]> stepArrayJsonAdapter = moshi.adapter(Step[].class);
    private final JsonAdapter<Ingredient[]> ingredientArrayJsonAdapter = moshi.adapter(Ingredient[].class);

    private DbTypeConverter(){}

    @TypeConverter
    public static Step fromStepJson(String value) {
        try {
            return value == null ? null : instance.stepJsonAdapter.fromJson(value);
        } catch (IOException e) {
            Timber.w(e);
            return null;
        }
    }

    @TypeConverter
    public static Step[] fromStepArrayJson(String value) {
        try {
            return value == null ? null : instance.stepArrayJsonAdapter.fromJson(value);
        } catch (IOException e) {
            Timber.w(e);
            return null;
        }
    }

    @TypeConverter
    public static String stepArrayToString(Step[] value) {
        return value == null ? null : instance.stepArrayJsonAdapter.toJson(value);
    }

    @TypeConverter
    public static String stepToString(Step value) {
        return value == null ? null : instance.stepJsonAdapter.toJson(value);
    }

    @TypeConverter
    public static Ingredient fromIngredientJson(String value) {
        try {
            return value == null ? null : instance.ingredientJsonAdapter.fromJson(value);
        } catch (IOException e) {
            Timber.w(e);
            return null;
        }
    }

    @TypeConverter
    public static String ingredientToString(Ingredient value) {
        return value == null ? null : instance.ingredientJsonAdapter.toJson(value);
    }

    @TypeConverter
    public static Ingredient[] fromIngredientArrayJson(String value) {
        try {
            return value == null ? null : instance.ingredientArrayJsonAdapter.fromJson(value);
        } catch (IOException e) {
            Timber.w(e);
            return null;
        }
    }

    @TypeConverter
    public static String ingredientArrayToString(Ingredient[] value) {
        return value == null ? null : instance.ingredientArrayJsonAdapter.toJson(value);
    }
}
