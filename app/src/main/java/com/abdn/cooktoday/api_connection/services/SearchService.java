package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredSearchJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SearchService {
    @GET("search/recipe/")
    Call<RecipeSearchJSON> searchRecipes(
            @Header("Cookie") String userSessionId,
            @Query("q") String query);

    @GET("search/ingred/")
    Call<IngredSearchJson> searchIngreds(
            @Header("Cookie") String userSessionId,
            @Query("q") String query);
}
