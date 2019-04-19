package com.raymond.udacity.bakingapp.models.api;

public class ApiIngredient {
    public String quantity;

    public String measure;

    public String ingredient;

    @Override
    public String toString() {
        return "ApiIngredient [quantity = "+quantity+", measure = "+measure+", ingredient = "+ingredient+"]";
    }
}