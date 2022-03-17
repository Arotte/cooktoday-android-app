package com.abdn.cooktoday.api_connection;

import com.abdn.cooktoday.api_connection.services.ExtractedRecipeService;
import com.abdn.cooktoday.api_connection.services.RecipeService;
import com.abdn.cooktoday.api_connection.services.SearchService;
import com.abdn.cooktoday.api_connection.services.UserService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creates and returns Services for
 * the different endpoints and
 * functionalities of the system.
 */
public final class APIRepository {
    private static APIRepository instance;

    private final UserService userService;
    private final ExtractedRecipeService extractedRecipeService;
    private final SearchService searchService;
    private final RecipeService recipeService;

    public static APIRepository getInstance() {
        if (instance == null) {
            instance = new APIRepository();
        }
        return instance;
    }

    public APIRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIEndpoints.BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
        extractedRecipeService = retrofit.create(ExtractedRecipeService.class);
        searchService = retrofit.create(SearchService.class);
        recipeService = retrofit.create(RecipeService.class);
    }

    public UserService getUserService() {
        return userService;
    }
    public ExtractedRecipeService getExtractedRecipeService() {
        return extractedRecipeService;
    }
    public SearchService getSearchService() {
        return searchService;
    }
    public RecipeService getRecipeService() {
        return recipeService;
    }
}