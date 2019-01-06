package com.example.daan.recipepager.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResponse {

    @SerializedName("recipes")
    public List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
