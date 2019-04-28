package com.raymond.udacity.bakingapp.models.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {
    public static final int NOT_FOUND_ID = -1;

    public String servings;

    public String name;

    public String image;

    @PrimaryKey
    @NonNull
    public int id;

    public Step[] steps;

    public Ingredient[] ingredients;

    @NonNull
    @Override
    public String toString() {
        return "name={" + name + "}";
    }
}
