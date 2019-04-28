package com.raymond.udacity.bakingapp.models.api;

public class ApiRecipe {
    public String image;

    public String servings;

    public String name;

    public ApiIngredient[] ingredients;

    public int id;

    public ApiStep[] steps;

    @Override
    public String toString() {
        return "ApiRecipe [image = "+image+", servings = "+servings+", name = "+name+", ingredients = "+ingredients+", id = "+id+", steps = "+steps+"]";
    }
}
