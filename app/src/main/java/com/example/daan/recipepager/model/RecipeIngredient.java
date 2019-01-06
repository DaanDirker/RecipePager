package com.example.daan.recipepager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeIngredient {

    @SerializedName("recipe_id")
    String recipeId;

    @SerializedName("ingredients")
    List<String> ingredients;

    public RecipeIngredient(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
