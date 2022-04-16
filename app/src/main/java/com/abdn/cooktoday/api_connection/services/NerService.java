package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.ingred_ner.IngredientNerJson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NerService {

    @FormUrlEncoded
    @POST("ner/ingred")
    Call<IngredientNerJson> performNerOnIngred(
            @Header("Cookie") String userSessId,
            @Field("ingredient_str") String ingredientString
    );
}
