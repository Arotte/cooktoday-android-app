package com.abdn.cooktoday.upload_recipe.from_url;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.local_data.LocalRecipes;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.NerredIngred;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.recipedetails.rvadapters.RecipeStepRVAdapter;
import com.abdn.cooktoday.utility.ProgressButtonHandler;
import com.abdn.cooktoday.utility.ToastMaker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Upload recipe from URL activity
 *
 * @author Team Alpha
 *
 * Upon receiving the URL of the recipe, this
 * activity will make a request to the server
 * to get the recipe details. When the recipe
 * details are received and displayed, this
 * activity will also make a request to the
 * server to parse the ingredients of the
 * recipe (using Named Entity Recognition).
 *
 * The user cannot add the recipe until
 * the parsing of the ingredients is complete.
 */
public class UploadFromUrlActivity extends AppCompatActivity
        implements PreviewIngredientItemRVAdapter.ItemClickListener, RecipeStepRVAdapter.ItemClickListener {
    private static final String TAG = "UploadFromUrlActivity";

    private int nIngreds;
    private Recipe recipe;
    private PreviewIngredientItemRVAdapter ingredientsRVAdapter;
    private RecipeStepRVAdapter recipeStepRVAdapter;
    private PasteUrlBottomSheet bottomSheet;
    private ScrollView outerScrollView;
    private ExtendedFloatingActionButton saveBtn;
    private ExtendedFloatingActionButton discardBtn;
    private RecyclerView rvIngredients;
    private ProgressButtonHandler progressButtonHandler;

    private boolean ingredientNerFinished;
    private int ingredNerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_from_url);

        ingredientNerFinished = false;
        ingredNerProgress = 0;

        outerScrollView = findViewById(R.id.scrollViewRecipePreview);
        outerScrollView.setVisibility(View.INVISIBLE);
        bottomSheet = new PasteUrlBottomSheet();
        // bottomSheet.setCancelable(false);
        showBottomSheet();
        initActionButtons();
        setActionButtonVisibility(View.INVISIBLE);
    }

    protected void getRecipeFromServer(String url, OnServerConnectionError errorCallback) {
        Server.extractRecipe(url, new ServerCallbacks.RecipeExtractionResult() {
            @Override
            public void success(Recipe recipe) {
                Log.i(TAG, "Successfully extracted recipe from url");
                Log.i(TAG, "Recipe ingredients: " + recipe.getIngredients().size());

                setRecipe(recipe);
                loadViews();
                hideBottomSheet();
                startIngredientNer();
            }

            @Override
            public void error(int errorCode) {
                if (errorCode == 400)
                    ToastMaker.make("No recipe found on the website", ToastMaker.Type.ERROR, UploadFromUrlActivity.this);
                else
                    ToastMaker.make("Oops! Something went wrong", ToastMaker.Type.ERROR, UploadFromUrlActivity.this);
                errorCallback.error();
            }
        });
    }

    private void startIngredientNer() {
        if (recipe != null) {
            rec_IngredientNer();
        }
    }

    private void rec_IngredientNer() {
        Log.i(TAG, "** RECURSIVE DEPTH: " + ingredNerProgress + " ** Performing NER on ingredient '" + recipe.getIngredients().get(ingredNerProgress).getName() + "'");
        Server.performNerOnIngred(
                LoggedInUser.user().getSessionID(),
                ingredientsRVAdapter.getItem(ingredNerProgress).getName(),
                new ServerCallbacks.IngredientNerResult() {
                    @Override
                    public void success(NerredIngred ingredientNer) {
                        recipe.getIngredients().get(ingredNerProgress).update(ingredientNer);
                        ingredientsRVAdapter.setNerDone(
                                ingredientNer,
                                (PreviewIngredientItemRVAdapter.ViewHolder)
                                        rvIngredients.findViewHolderForAdapterPosition(ingredNerProgress),
                                getResources().getColor(R.color.primaryGreen_tp),
                                getResources().getColor(R.color.google_tp),
                                getResources().getColor(R.color.facebook_tp));
                        ingredNerProgress++;
                        if (ingredNerProgress == nIngreds)
                            ingredientNerFinished = true;
                        else
                            rec_IngredientNer();
                    }
                    @Override
                    public void error(int errorCode) {
                        ingredNerProgress++;
                        rec_IngredientNer();
                    }
                }
        );
    }

    private void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    private void loadViews() {
        outerScrollView.setVisibility(View.VISIBLE);
        initRecipeDetailsViews();
        initIngredientsView();
        initStepsView();
        setActionButtonVisibility(View.VISIBLE);
    }

    private void initIngredientsView() {
        rvIngredients = findViewById(R.id.rvRecipePreviewIngreds);

        List<Ingredient> ingredients = recipe.getIngredients();
        nIngreds = ingredients.size();
        Log.i(TAG, "nIngreds: " + nIngreds);

        rvIngredients.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIngredients.setNestedScrollingEnabled(false);
        ingredientsRVAdapter = new PreviewIngredientItemRVAdapter(this, ingredients);
        ingredientsRVAdapter.setClickListener(this);
        rvIngredients.setAdapter(ingredientsRVAdapter);

        Log.i(TAG, "nIngreds: " + ingredientsRVAdapter.getItemCount());
    }


    private void initStepsView() {
        RecyclerView rvSteps = findViewById(R.id.rvPreviewRecipeSteps);

        List<String> steps = recipe.getStepDescriptions();

        rvSteps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSteps.setNestedScrollingEnabled(false);
        recipeStepRVAdapter = new RecipeStepRVAdapter(this, steps);
        recipeStepRVAdapter.setClickListener(this);
        rvSteps.setAdapter(recipeStepRVAdapter);

        ((TextView) findViewById(R.id.tvRecipePreviewNSteps)).setText(steps.size() + " steps");
    }

    private void initRecipeDetailsViews() {
        // recipe image
        Picasso.get()
                .load(this.recipe.getImgUrl())
                .into((ImageView) findViewById(R.id.ivRecipePreview));

        // recipe name
        ((TextView) findViewById(R.id.tvRecipePreviewName)).setText(
                this.recipe.getName());

        // servings
        ((TextView) findViewById(R.id.tvRecipePreviewServings)).setText(
                "serves " + this.recipe.getServings());

        // calories
        ((TextView) findViewById(R.id.tvRecipePreviewCals)).setText(
                this.recipe.getCalories() + " kcal");

        // prep, cook and full cook time
        ((TextView) findViewById(R.id.tvRecipePreviewPrepT)).setText(
                this.recipe.getTimePretty(Recipe.TimeType.PREPARATION_TIME));
        ((TextView) findViewById(R.id.tvRecipePreviewCookT)).setText(
                this.recipe.getTimePretty(Recipe.TimeType.COOKING_TIME));
        ((TextView) findViewById(R.id.tvRecipePreviewTotalT)).setText(
                this.recipe.getTimePretty(Recipe.TimeType.FULL_COOKING_TIME));

        // short & long description
        ((TextView) findViewById(R.id.tvRecipePreviewShortDescr)).setText(
                this.recipe.getShortDescription());
        ((TextView) findViewById(R.id.tvRecipePreviewLongDesc)).setText(
                this.recipe.getLongDescription());
    }

    private void initActionButtons() {
        discardBtn = findViewById(R.id.btnRecipePreviewDiscardRecipe);
        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show bottom sheet and hide created recipe
                outerScrollView.setVisibility(View.INVISIBLE);
                showBottomSheet();
            }
        });

        progressButtonHandler = new ProgressButtonHandler(
                findViewById(R.id.pbAddRecipeUrl),
                findViewById(R.id.ivAddRecipeUrlPlus),
                findViewById(R.id.ivAddRecipeUrlDone)
        );
        progressButtonHandler.setState(ProgressButtonHandler.State.DEFAULT);

        saveBtn = findViewById(R.id.btnRecipePreviewAddRecipe);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create new recipe on server
                if (ingredientNerFinished) {
                    Log.i(TAG, "Recipe that is being created: " + recipe.toString());
                    progressButtonHandler.setState(ProgressButtonHandler.State.LOADING);

                    Server.createRecipe(LoggedInUser.user().getSessionID(), LoggedInUser.user().getServerID(), recipe, new ServerCallbacks.CreateRecipeResult() {
                        @Override
                        public void success(Recipe recipe) {
                            // recipe was successfully created on the server
                            progressButtonHandler.setState(ProgressButtonHandler.State.SUCCESS);
                            Log.i(TAG, "Recipe creation successful!");
                            ToastMaker.make("Recipe added!", ToastMaker.Type.SUCCESS, UploadFromUrlActivity.this);
                            LocalRecipes.i().addRecipe(recipe, LocalRecipes.Type.ADDED_BY_USER);
                            finish();
                        }

                        @Override
                        public void error(int errorCode) {
                            // error during the creation of the recipe on the server
                            progressButtonHandler.setState(ProgressButtonHandler.State.DEFAULT);
                            Log.i(TAG, "Error during creation of recipe! Error code: " + errorCode);
                            ToastMaker.make("Oops! Something went wrong", ToastMaker.Type.ERROR, UploadFromUrlActivity.this);
                        }
                    });
                } else {
                    ToastMaker.make("Please wait until the ingredients are recognized", ToastMaker.Type.ERROR, UploadFromUrlActivity.this);
                }
            }
        });
    }

    private void setActionButtonVisibility(int vis) {
        saveBtn.setVisibility(vis);
        discardBtn.setVisibility(vis);
    }

    @Override
    public void onIngredItemClick(View view, int position) {
    }

    @Override
    public void onStepItemClick(View view, int position) {
    }

    private void showBottomSheet() {
        bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
    }

    private void hideBottomSheet() {
        bottomSheet.dismiss();
    }

    protected interface OnServerConnectionError {
        void error();
    }
}