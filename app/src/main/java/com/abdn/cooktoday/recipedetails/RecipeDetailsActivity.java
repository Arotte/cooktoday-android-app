package com.abdn.cooktoday.recipedetails;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.cooking_session.CookingSessionActivity;
import com.abdn.cooktoday.local_data.LocalRecipes;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.recipedetails.rvadapters.IngredientItemRVAdapter;
import com.abdn.cooktoday.recipedetails.rvadapters.RecipeStepRVAdapter;
import com.abdn.cooktoday.utility.ToastMaker;
import com.abdn.cooktoday.utility.Util;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * RecipeDetailsActivity
 *
 * This activity is used to display the details of a recipe.
 * It is called whenever a user taps on a recipe anywhere in the app.
 *
 * Its main purpose is to display relevant information about a recipe.
 * It also communicates with the CookToday API when a user
 * clicks on the "Save" button.
 */
public class RecipeDetailsActivity extends AppCompatActivity
        implements IngredientItemRVAdapter.ItemClickListener, RecipeStepRVAdapter.ItemClickListener {
    private static final String TAG = "RecipeDetailsActivity";

    Recipe recipe;
    int nIngreds;
    boolean isSaved;

    MaterialButton saveBtn;
    IngredientItemRVAdapter ingredientsRVAdapter;
    RecipeStepRVAdapter recipeStepRVAdapter;

    int nIngredsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        navbarFix();

        // get the recipe
        this.recipe = (Recipe) getIntent().getSerializableExtra("RecipeObject");
        this.isSaved = recipe.isSaved();
        nIngreds = 0;
        nIngredsChecked = 0;
        initIngredientsView();
        initStepsView();

        // init view
        initRecipeDetailsView();
        initSaveRecipeButton();
        initReviewStars();

        initCookedCounter();
        findViewById(R.id.fabtnRecipeDetailsCookDish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeDetailsActivity.this, CookingSessionActivity.class);
                intent.putExtra("RecipeObject", recipe);
                startActivity(intent);
            }
        });
    }

    private void initStepsView() {
        RecyclerView rvSteps = findViewById(R.id.rvRecipeDetailsRecipeStepsContainer);

        List<String> steps = recipe.getStepDescriptions();
        if (steps == null) {
            steps = new ArrayList<>(Arrays.asList(
                "In a medium-size mixing bowl or large glass measuring cup, <b>whisk together</b> your dry ingredients.",
                "Heat olive oil in a large, oven-proof non stick pan (or a well-seasoned cast iron skillet) over medium-high heat. Sear chicken thighs for 3 minutes each side, until the skin becomes golden and crisp. Leave 2 tablespoons of chicken juices in the pan for added flavour, and drain any excess.",
                "Fry the garlic in the same pan around the chicken for 1 minute until fragrant. Add the honey, both mustards, and water to the pan, mixing well, and combine all around the chicken.",
                "OPTIONAL: Remove from the oven after 30 minutes; add in the green beans (mixing them through the sauce), and return to the oven to bake for a further 15 minutes, or until the chicken is completely cooked through and no longer pink in the middle, and the potatoes are fork tender."
            ));
        }

        rvSteps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSteps.setNestedScrollingEnabled(false);
        recipeStepRVAdapter = new RecipeStepRVAdapter(this, steps);
        recipeStepRVAdapter.setClickListener(this);
        rvSteps.setAdapter(recipeStepRVAdapter);

        ((TextView) findViewById(R.id.tvRecipeDetailsNSteps)).setText(steps.size() + " steps");
    }

    private void initIngredientsView() {
        RecyclerView rvIngredients = findViewById(R.id.rvRecipeDetailsIngredientsContainer);

        List<Ingredient> ingredients = this.recipe.getIngredients();
        if (ingredients == null) {
            ingredients = new ArrayList<>(Arrays.asList(
                new Ingredient("Eggs", "2 pieces"),
                new Ingredient("Bacon", "200g"),
                new Ingredient("Water", "1l"),
                new Ingredient("Weed", "10g"),
                new Ingredient("Sliced bacon", "100g"),
                new Ingredient("Salt", "to taste")
            ));
        }
        nIngreds = ingredients.size();

        rvIngredients.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIngredients.setNestedScrollingEnabled(false);
        ingredientsRVAdapter = new IngredientItemRVAdapter(this, ingredients);
        ingredientsRVAdapter.setClickListener(this);
        rvIngredients.setAdapter(ingredientsRVAdapter);
    }

    @Override
    public void onIngredItemClick(View view, int position) {
        CheckBox cb = view.findViewById(R.id.checkboxIngredientItem);
        TextView ingredName = view.findViewById(R.id.tvIngredientItemName);

        // change color of ingredient name textview
        if (cb.isChecked()) {
            ingredName.setTextColor(getResources().getColor(R.color.textMain));
            nIngredsChecked--;
        } else {
            ingredName.setTextColor(getResources().getColor(R.color.textSecondary));
            nIngredsChecked++;
        }
        // set checkbox to checked
        cb.setChecked(!cb.isChecked());

        // show "start cooking" prompt when all
        // ingredients are checked
        if (nIngredsChecked == nIngreds)
            ToastMaker.success("Awesome, you can start cooking!", RecipeDetailsActivity.this);

        // etc ...

    }

    @Override
    public void onStepItemClick(View view, int position) {
    }

    private void initRecipeDetailsView() {
        // recipe image
        String imgUrl = this.recipe.getImgUrl();
        ImageView img = findViewById(R.id.ivRecipeDetailsImg);
        if (!imgUrl.isEmpty())
            Picasso.get().load(imgUrl).into(img);
        else
            img.setImageDrawable(getResources().getDrawable(R.drawable.img_food2));

        // recipe name
        ((TextView) findViewById(R.id.tvRecipeDetailsRecipeName)).setText(
                this.recipe.getName());

        // servings
        ((TextView) findViewById(R.id.tvRecipeDetailsServings)).setText(
                this.recipe.getServings() + " ppl");

        // calories
        ((TextView) findViewById(R.id.tvRecipeDetailsCalories)).setText(
                this.recipe.getCalories() + " kcal");

        // prep, cook and full cook time
        ((TextView) findViewById(R.id.tvRecipeDetailsPrepTime)).setText(
                this.recipe.getTimePretty(Recipe.TimeType.PREPARATION_TIME));
        ((TextView) findViewById(R.id.tvRecipeDetailsCookTime)).setText(
                this.recipe.getTimePretty(Recipe.TimeType.COOKING_TIME));
        ((TextView) findViewById(R.id.tvRecipeDetailsTotalTime)).setText(
                this.recipe.getTimePretty(Recipe.TimeType.FULL_COOKING_TIME));

        String shortDesc = this.recipe.getShortDescription();
        String longDesc = this.recipe.getLongDescription();
        boolean shortDescOk = !(shortDesc == null) && !shortDesc.isEmpty();
        boolean longDescOk = !(longDesc == null) && !longDesc.isEmpty();
        TextView tvShortDesc = ((TextView) findViewById(R.id.tvRecipeDetailsRecipeShortDescription));
        TextView tvLongDesc = ((TextView) findViewById(R.id.tvRecipeDetailsLongDescription));
        TextView tvLongDescTitle = ((TextView) findViewById(R.id.tvRecipeDetailsDescription));

        if (!longDescOk) {
            tvLongDesc.setVisibility(View.GONE);
            tvLongDescTitle.setVisibility(View.GONE);
        } else {
            Util.renderHtmlInTextView(longDesc, tvLongDesc);
        }

        if (!shortDescOk) {
            tvShortDesc.setVisibility(View.GONE);
        } else {
            Util.renderHtmlInTextView(shortDesc, tvShortDesc);
        }

    }

    /**
     * Set up the save recipe button.
     * Add a click listener to the button, that
     * saves the recipe to the database.
     */
    private void initSaveRecipeButton() {
        saveBtn = findViewById(R.id.btnRecipeDetailsSave);
        updateSaveBtnView();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSaved) {
                    // can't "unsave" a recipe as of yet, so this
                    // is not implemented
                } else {
                    // post save recipe request to server
                    Server.saveRecipe(LoggedInUser.user().getSessionID(), recipe.getServerId(), new ServerCallbacks.SaveRecipeResult() {
                        @Override
                        public void success(Recipe recipe) {
                            Log.i(TAG, "Recipe " + recipe.getServerId() + " successfully saved to user's cookbook!");
                            ToastMaker.success("Recipe saved to Cookbook!", RecipeDetailsActivity.this);
                            recipe.setSaved(true);
                            LocalRecipes.i().recipeSaved(recipe.getServerId());
                            isSaved = true;
                            updateSaveBtnView();
                        }

                        @Override
                        public void error(int errorCode) {
                            Log.i(TAG, "Error while saving recipe " + recipe.getServerId() + " to user's cookbook!");
                        }
                    });
                }
            }
        });
    }

    /**
     * Updates the view of the save button to
     * reflect the current state of the recipe
     * (saved or not)
     */
    private void updateSaveBtnView() {
        if (isSaved) {
            saveBtn.setText("Saved");
            saveBtn.setIcon(getResources().getDrawable(R.drawable.ic_bookmark_bold));
        } else {
            saveBtn.setText("Save");
            saveBtn.setIcon(getResources().getDrawable(R.drawable.ic_bookmark));
        }
    }

    private void navbarFix() {
        // prevent navbars from flickering on transitions
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.bottomAppBar), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    private void initReviewStars() {
        // backend does not yet support reviews

        ((LinearLayout) findViewById(R.id.llRecipeDetailsStarts)).setVisibility(View.GONE);
        // StarReviewsViewHandler starHandler = new StarReviewsViewHandler(
        //     Arrays.asList(
        //         R.id.ivRecipeDetails__star1,
        //         R.id.ivRecipeDetails__star2,
        //         R.id.ivRecipeDetails__star3,
        //         R.id.ivRecipeDetails__star4,
        //         R.id.ivRecipeDetails__star5),
        //     R.id.tvRecipeDetailsReviewCount,
        //    R.color.primaryGreen,
        //    R.color.inactiveStar);

        // set average review to 3.67/5
        // and total review count to 1121
        // starHandler.set(3.67F, 1121);
    }

    private void initCookedCounter() {
        // backend does not yet support cooked count
        ((LinearLayout) findViewById(R.id.llRecipeDetailsRecipeCookedNo)).setVisibility(View.GONE);
    }

    /**
     * Helper class to handle the star reviews view.
     *
     * Use .set(rating, totalNumberOfRatings) to
     * set the star review bar.
     */
    private class StarReviewsViewHandler {
        private static final int nStars = 5;

        private final List<ImageView> stars;
        private final TextView tvTotalReviews;

        private final int activeColor;
        private final int inactiveColor;

        public StarReviewsViewHandler(
                List<Integer> starIDs,
                int tvTotalReviewsID,
                int activeColor,
                int inactiveColor) {
            // NOTE: the list of stars MUST be ordered!

            this.activeColor = activeColor;
            this.inactiveColor = inactiveColor;
            this.tvTotalReviews = findViewById(tvTotalReviewsID);

            stars = new ArrayList<>();
            for (int i = 0; i < starIDs.size(); i++)
                stars.add(
                        findViewById(starIDs.get(i)));
        }

        public void set(float rating, int nTotal) {
            this.tvTotalReviews.setText("(" + nTotal + ")");
            int ratingRounded = Math.round(rating);
            // System.out.println("RECIPE RATING ROUNDED TO " + ratingRounded + " / " + nStars);

            for (int i = 0; i < nStars; i++) {
                if (i < ratingRounded)
                    stars.get(i).setColorFilter(getResources().getColor(activeColor));
                else
                    stars.get(i).setColorFilter(getResources().getColor(inactiveColor));
            }
        }
    }
}