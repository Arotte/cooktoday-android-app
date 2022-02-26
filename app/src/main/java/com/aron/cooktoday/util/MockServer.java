/*
    A singleton mock server
 */

package com.aron.cooktoday.util;

import com.aron.cooktoday.models.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockServer {

    private static MockServer server = null;

    private MockServer() {
    }

    public static MockServer server() {
        if (server == null)
            server = new MockServer();
        return server;
    }

    // ==========================================

    public List<Recipe> getRecipes(String feedType) {
        switch (feedType) {
            case "hot":
                return new ArrayList<Recipe>(Arrays.asList(
                        new Recipe("Italian Spaghetti", SampleRecipeImgURLs.i().get(0)),
                        new Recipe("Sushi", SampleRecipeImgURLs.i().get(1)),
                        new Recipe("Sushi Better", SampleRecipeImgURLs.i().get(2)),
                        new Recipe("Pizza", SampleRecipeImgURLs.i().get(3)),
                        new Recipe("Some Food", SampleRecipeImgURLs.i().get(4))
                ));
            case "recommend":
                return new ArrayList<Recipe>(Arrays.asList(
                        new Recipe("Delicious Pizza", SampleRecipeImgURLs.i().get(0)),
                        new Recipe("Mustard Sushi", SampleRecipeImgURLs.i().get(1)),
                        new Recipe("Funny Brownie", SampleRecipeImgURLs.i().get(2)),
                        new Recipe("LS... Dish", SampleRecipeImgURLs.i().get(3))
                ));
            default:
                // return four default recipes
                return new ArrayList<Recipe>(Arrays.asList(
                        new Recipe(),
                        new Recipe(),
                        new Recipe(),
                        new Recipe()
                ));
        }
    }
}
