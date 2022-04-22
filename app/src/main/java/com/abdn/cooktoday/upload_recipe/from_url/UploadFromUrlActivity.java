package com.abdn.cooktoday.upload_recipe.from_url;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.local_data.Cache;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.recipedetails.rvadapters.RecipeStepRVAdapter;
import com.abdn.cooktoday.utility.ToastMaker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_from_url);

        outerScrollView = (ScrollView) findViewById(R.id.scrollViewRecipePreview);
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
                setRecipe(recipe);
                loadViews();
                hideBottomSheet();
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

    private void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    private void loadViews() {
        outerScrollView.setVisibility(View.VISIBLE);
        initRecipeDetailsViews();
        initRecipeDetailsViews();
        initIngredientsView();
        initStepsView();
        setActionButtonVisibility(View.VISIBLE);
    }

    private void initIngredientsView() {
        RecyclerView rvIngredients = (RecyclerView) findViewById(R.id.rvRecipePreviewIngreds);

        List<Ingredient> ingredients = recipe.getIngredients();
        nIngreds = ingredients.size();

        rvIngredients.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIngredients.setNestedScrollingEnabled(false);
        ingredientsRVAdapter = new PreviewIngredientItemRVAdapter(this, ingredients);
        ingredientsRVAdapter.setClickListener(this);
        rvIngredients.setAdapter(ingredientsRVAdapter);
    }


    private void initStepsView() {
        RecyclerView rvSteps = (RecyclerView) findViewById(R.id.rvPreviewRecipeSteps);

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
        discardBtn = (ExtendedFloatingActionButton) findViewById(R.id.btnRecipePreviewDiscardRecipe);
        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show bottom sheet and hide created recipe
                outerScrollView.setVisibility(View.INVISIBLE);
                showBottomSheet();
            }
        });

        saveBtn = (ExtendedFloatingActionButton) findViewById(R.id.btnRecipePreviewAddRecipe);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create new recipe on server
                Server.createRecipe(LoggedInUser.user().getSessionID(), LoggedInUser.user().getServerID(), recipe, new ServerCallbacks.CreateRecipeResult() {
                    @Override
                    public void success(Recipe recipe) {
                        // recipe was successfully created on the server
                        Log.i(TAG, "Recipe creation successful!");
                        ToastMaker.make("Recipe added!", ToastMaker.Type.SUCCESS, UploadFromUrlActivity.this);

                        // TODO: add just created recipe to saved recipes of the user

                        finish();
                    }

                    @Override
                    public void error(int errorCode) {
                        // error during the creation of the recipe on the server
                        Log.i(TAG, "Error during creation of recipe! Error code: " + errorCode);
                        ToastMaker.make("Oops! Something went wrong", ToastMaker.Type.ERROR, UploadFromUrlActivity.this);
                    }
                });
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