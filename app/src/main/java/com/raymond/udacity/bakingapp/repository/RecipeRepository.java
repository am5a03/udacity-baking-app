package com.raymond.udacity.bakingapp.repository;

import android.content.SharedPreferences;

import androidx.collection.ArrayMap;

import com.raymond.udacity.bakingapp.api.ApiService;
import com.raymond.udacity.bakingapp.models.api.ApiRecipe;
import com.raymond.udacity.bakingapp.models.db.Ingredient;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.models.db.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;

public class RecipeRepository {

    private ApiService apiService;
    private AppDatabase database;
    private SharedPreferences sharedPref;

    private static final String KEY_LAST_UPDATE_TS = "last_update_ts";
    private static final long DATA_TIMEOUT = 60 * 1000;

    private final Recipe dummy = new Recipe();

    private Map<Recipe, Map<Integer, Step>> recipeStepMap = new HashMap<>();

    @Inject
    public RecipeRepository(ApiService apiService,
                            AppDatabase database,
                            SharedPreferences defaultSharedPref) {
        this.apiService = apiService;
        this.database = database;
        this.sharedPref = defaultSharedPref;

        dummy.id = Recipe.NOT_FOUND_ID;
    }

    /**
     *
     * @return
     */
    public Single<List<Recipe>> getAllRecipes() {
        return Single.just(database)
                .map(database -> database.getRecipeDao().getAll())
                .flatMap(recipes -> {
                    long now = System.currentTimeMillis();
                    // Simple logic to refresh from API again
                    if (recipes.size() == 0 || now - sharedPref.getLong(KEY_LAST_UPDATE_TS, 0) > DATA_TIMEOUT) {
                        return createRecipeListFromApi();
                    } else {
                        return Single.just(recipes);
                    }
                });
    }

    /**
     *
     * @param id
     * @return
     */
    public Single<Recipe> getRecipeById(int id) {
        return Single.just(database)
                .map(database1 -> {
                    final Recipe recipe = database1.getRecipeDao().getRecipeById(id);
                    return recipe == null ? dummy : recipe;
                })
                .flatMap(recipe -> (recipe.id == Recipe.NOT_FOUND_ID) ? createRecipeListFromApi().map(recipes -> {
                    final Recipe recipe1 = database.getRecipeDao().getRecipeById(id);
                    return recipe1 == null ? dummy : recipe1;
                }) : Single.just(recipe));
    }

    /**
     *
     * @param recipe
     * @param stepId
     * @return
     */
    public Single<Step> getStepByRecipe(Recipe recipe, int stepId) {
        if (recipeStepMap.containsKey(recipe) &&
                recipeStepMap.get(recipe) != null && recipeStepMap.get(recipe).containsKey(stepId)) {
            return Single.just(recipeStepMap.get(recipe).get(stepId));
        } else {
            return Single.just(recipe.id)
                    .flatMap(integer -> getRecipeById(recipe.id))
                    .map(recipe1 -> {
                        if (!recipeStepMap.containsKey(recipe1)) {
                            final ArrayMap<Integer, Step> stepMap = new ArrayMap<>();

                            for (int i = 0; i < recipe1.steps.length; i++) {
                                stepMap.put(i, recipe1.steps[i]);
                            }

                            recipeStepMap.put(recipe1, stepMap);
                        }
                        final Map<Integer, Step> steps = recipeStepMap.get(recipe1);
                        return steps.get(stepId);
                    });
        }

    }

    /**
     *
     * @return
     */
    private Single<List<Recipe>> createRecipeListFromApi() {
        return apiService.getReceipes().map(apiRecipes -> {
            final List<Recipe> recipeList = new ArrayList<>();
            for (int i = 0; i < apiRecipes.size(); i++) {
                Recipe recipe = createRecipeFromApi(apiRecipes.get(i));
                database.getRecipeDao().insertAll(recipe);
                recipeList.add(recipe);
            }
            sharedPref.edit().putLong(KEY_LAST_UPDATE_TS, System.currentTimeMillis()).apply();
            return recipeList;
        }).onErrorReturnItem(new ArrayList<>());
    }

    /**
     *
     * @param apiRecipe
     * @return
     */
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
                ingredient.ingredient = ingredient.ingredient.substring(0, 1).toUpperCase() + ingredient.ingredient.substring(1);
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
                steps[i] = step;
            }
            recipe.steps = steps;
        }

        return recipe;
    }
}
