package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeJSON__Outer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ExtractedRecipeService {
    @FormUrlEncoded
    @POST("extract/")
    Call<ExtractedRecipeJSON__Outer> extract(@Field("url") String url);
}