package com.raymond.udacity.bakingapp.models.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    public long _id;

    public String servings;

    public String name;

    public String id;

    public Step[] steps;

    public Ingredient[] ingredients;
}
