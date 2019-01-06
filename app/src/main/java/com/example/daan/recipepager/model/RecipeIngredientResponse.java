package com.example.daan.recipepager.model;

import com.google.gson.annotations.SerializedName;

public class RecipeIngredientResponse {
    @SerializedName("recipe")
    private RecipeIngredient recipe;

    public RecipeIngredient getRecipe() {
        return recipe;
    }
}
