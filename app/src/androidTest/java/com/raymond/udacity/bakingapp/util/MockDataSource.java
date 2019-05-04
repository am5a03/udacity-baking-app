package com.raymond.udacity.bakingapp.util;

import com.raymond.udacity.bakingapp.models.db.Ingredient;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.models.db.Step;

import java.util.ArrayList;
import java.util.List;

public final class MockDataSource {
    private static final MockDataSource instance = new MockDataSource();

    private List<Recipe> mockList = createMockRecipes();


    private MockDataSource() {}

    public static MockDataSource getInstance() {
        return instance;
    }

    public List<Recipe> getMockList() {
        return mockList;
    }

    private List<Recipe> createMockRecipes() {
        final List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Recipe r = new Recipe();
            r.id = i;
            r.name = "Recipe " + i;
            r.servings = "999";
            r.ingredients = createIngredients();
            r.steps = createSteps();
            recipes.add(r);
        }
        return recipes;
    }

    private Step[] createSteps() {
        final Step[] steps = new Step[10];
        for (int i = 0; i < steps.length; i++) {
            Step step = new Step();
            step.id = i;

            // Simulate that some steps do not have video
            if (i % 2 == 0) {
                step.videoURL = "";
            } else {
                step.videoURL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
            }

            step.description = "Some description " + i;
            step.shortDescription = "Short description " + i;

            steps[i] = step;
        }
        return steps;
    }

    private Ingredient[] createIngredients() {
        final Ingredient[] ingredients = new Ingredient[10];
        for (int i = 0; i < ingredients.length; i++) {
            Ingredient ingredient = new Ingredient();
            ingredient.ingredient = "Ingredient " + i;
            ingredient.measure = "SPOON";
            ingredient.quantity = "1";
            ingredients[i] = ingredient;
        }
        return ingredients;
    }
}
