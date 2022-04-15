package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.feed.RecommendedRecipesJson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface FeedService {
    @GET("feed/recommend")
    Call<RecommendedRecipesJson> getRecommendedRecipes(@Header("Cookie") String userSessId);
}
