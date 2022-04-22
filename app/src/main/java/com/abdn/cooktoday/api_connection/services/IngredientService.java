package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.CreateIngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.CreatedIngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredientJson__Outer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IngredientService {

    @GET("ingred/{id}")
    Call<IngredientJson__Outer> getIngredientById(
            @Header("Cookie") String sessId,
            @Path("id") String ingredId
    );

    @POST("ingred")
    Call<CreatedIngredientJson> createIngredient(
            @Header("Cookie") String sessId,
            @Body CreateIngredientJson ingredientJson
    );
}
