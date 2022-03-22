package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreateRecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreatedInstructionJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON__Outer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecipeService {
    @GET("recipe/{id}")
    Call<RecipeJSON__Outer> getRecipeById(
            @Header("Cookie") String userSessId,
            @Path("id") String id);

    @POST("recipe/")
    Call<RecipeJSON__Outer> createRecipe(
            @Header("Cookie") String userSessId,
            @Body CreateRecipeJSON recipe);
}