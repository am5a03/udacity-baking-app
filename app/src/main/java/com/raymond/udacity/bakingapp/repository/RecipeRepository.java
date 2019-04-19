package com.raymond.udacity.bakingapp.repository;

import com.raymond.udacity.bakingapp.api.ApiService;
import com.raymond.udacity.bakingapp.models.api.ApiRecipe;
import com.raymond.udacity.bakingapp.models.db.Ingredient;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.models.db.Step;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RecipeRepository {

    private ApiService apiService;
    private AppDatabase database;

    @Inject
    public RecipeRepository(ApiService apiService,
                            AppDatabase database) {
        this.apiService = apiService;
        this.database = database;
    }

    public Single<List<Recipe>> getAllRecipes() {
        return Single.just(database)
                .map(database -> database.getRecipeDao().getAll())
                .flatMap(recipes -> {
                    if (recipes.size() == 0) {
                        return apiService.getReceipes().map(apiRecipes -> {
                            final List<Recipe> recipeList = new ArrayList<>();
                            for (int i = 0; i < apiRecipes.size(); i++) {
                                Recipe recipe = createRecipeFromApi(apiRecipes.get(i));
                                database.getRecipeDao().insertAll(recipe);
                                recipeList.add(recipe);
                            }
                            return recipeList;
                        });
                    } else {
                        return Single.just(recipes);
                    }
                });
    }

    private Recipe createRecipeFromApi(ApiRecipe apiRecipe) {
        final Recipe recipe = new Recipe();
        recipe.id = apiRecipe.id;
        recipe.name = apiRecipe.name;
        recipe.servings = apiRecipe.servings;

        if (apiRecipe.ingredients != null && apiRecipe.ingredients.length != 0) {
            final Ingredient[] ingredients = new Ingredient[apiRecipe.ingredients.length];
            for (int i = 0; i < apiRecipe.ingredients.length; i++) {
                final Ingredient ingredient = new Ingredient();
                ingredient.ingredient = apiRecipe.ingredients[i].ingredient;
                ingredient.measure = apiRecipe.ingredients[i].measure;
                ingredient.quantity = apiRecipe.ingredients[i].quantity;
                ingredients[i] = ingredient;
            }
            recipe.ingredients = ingredients;
        }

        if (apiRecipe.steps != null && apiRecipe.steps.length != 0) {
            final Step[] steps = new Step[apiRecipe.steps.length];
            for (int i = 0; i < apiRecipe.steps.length; i++) {
                final Step step = new Step();
                step.description = apiRecipe.steps[i].description;
                step.id = apiRecipe.steps[i].id;
                step.shortDescription = apiRecipe.steps[i].shortDescription;
                step.videoURL = apiRecipe.steps[i].videoURL;
                step.thumbnailURL = apiRecipe.steps[i].thumbnailURL;
            }
            recipe.steps = steps;
        }

        return recipe;
    }
}
