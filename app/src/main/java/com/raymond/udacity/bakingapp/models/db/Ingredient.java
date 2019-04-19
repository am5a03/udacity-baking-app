package com.raymond.udacity.bakingapp.models.db;

import androidx.annotation.NonNull;

public class Ingredient {
    public String quantity;

    public String measure;

    public String ingredient;

    @NonNull
    @Override
    public String toString() {
        return "ingredient={" + ingredient + "}";
    }
}
