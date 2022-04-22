package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreateRecipeJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.ListOfRecipesJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.SavedRecipesJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecipeService {
    @GET("recipe/id={id}")
    Call<RecipeJSON__Outer> getRecipeById(
            @Header("Cookie") String userSessId,
            @Path("id") String id
    );

    @POST("recipe/")
    Call<RecipeJSON__Outer> createRecipe(
            @Header("Cookie") String userSessId,
            @Body CreateRecipeJson recipe
    );

    @GET("recipe/save")
    Call<SavedRecipesJson> getSavedRecipes(
            @Header("Cookie") String userSessId
    );

    @GET("recipe/mine")
    Call<ListOfRecipesJson> getRecipesOfUser(
            @Header("Cookie") String userSessId
    );

    @GET("recipe/cooked")
    Call<ListOfRecipesJson> getRecipesCookedByUser(
            @Header("Cookie") String userSessId
    );

    @POST("recipe/save/{id}")
    Call<RecipeJSON__Outer> saveRecipe(
            @Header("Cookie") String userSessId,
            @Path("id") String recipeId
    );

    @POST("recipe/cooked/{id}")
    Call<RecipeJSON__Outer> cookRecipe(
            @Header("Cookie") String userSessId,
            @Path("id") String recipeId
    );
}
