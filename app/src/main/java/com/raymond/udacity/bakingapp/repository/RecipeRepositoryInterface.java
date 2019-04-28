package com.raymond.udacity.bakingapp.repository;

import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.models.db.Step;

import java.util.List;

import io.reactivex.Single;

public interface RecipeRepositoryInterface {
    Single<List<Recipe>> getAllRecipes();
    Single<Recipe> getRecipeById(int id);
    Single<Step> getStepByRecipe(int recipeId, int stepId);
    Single<Step> getStepByRecipe(final Recipe recipe, final int stepId);
}
