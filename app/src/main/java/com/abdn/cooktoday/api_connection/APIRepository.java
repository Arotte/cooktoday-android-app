package com.abdn.cooktoday.api_connection;

import com.abdn.cooktoday.api_connection.services.ExtractedRecipeService;
import com.abdn.cooktoday.api_connection.services.FeedService;
import com.abdn.cooktoday.api_connection.services.IngredientService;
import com.abdn.cooktoday.api_connection.services.MediaService;
import com.abdn.cooktoday.api_connection.services.NerService;
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
    private final FeedService feedService;
    private final NerService nerService;
    private final MediaService mediaService;
    private final IngredientService ingredientService;

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
                .baseUrl(APIEndpoints.getBase())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
        extractedRecipeService = retrofit.create(ExtractedRecipeService.class);
        searchService = retrofit.create(SearchService.class);
        recipeService = retrofit.create(RecipeService.class);
        feedService = retrofit.create(FeedService.class);
        nerService = retrofit.create(NerService.class);
        mediaService = retrofit.create(MediaService.class);
        ingredientService = retrofit.create(IngredientService.class);
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
    public FeedService getFeedService() { return feedService; }
    public NerService getNerService() { return nerService; }
    public MediaService getMediaService() { return mediaService; }
    public IngredientService getIngredientService() { return ingredientService; }
}