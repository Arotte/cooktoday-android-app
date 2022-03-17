package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON__Outer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RecipeService {

    @GET("recipe/{id}")
    Call<RecipeJSON__Outer> getRecipeById(
            @Header("Cookie") String userSessId,
            @Path("id") String id);
}
