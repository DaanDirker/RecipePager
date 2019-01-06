package com.example.daan.recipepager.service.api;

import com.example.daan.recipepager.model.RecipeIngredientResponse;
import com.example.daan.recipepager.model.RecipeResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RecipeApiService {

    String BASE_URL =  "https://www.food2fork.com/api/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("search?key=630b229d337167afb9b0541af7fd7e62&sort=t&count=3")
    Call<RecipeResponse> getTrendingRecipes();

    @GET("get?key=630b229d337167afb9b0541af7fd7e62")
    Call<RecipeIngredientResponse> getRecipeIngredients(
            @Query(value = "rId") String recipeId
    );
}
