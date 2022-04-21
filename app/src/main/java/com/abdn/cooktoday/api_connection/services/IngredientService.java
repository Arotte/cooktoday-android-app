package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredientJson__Outer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface IngredientService {

    @GET("ingred/{id}")
    Call<IngredientJson__Outer> getIngredientById(
            @Header("Cookie") String sessId,
            @Path("id") String ingredId
    );
}
