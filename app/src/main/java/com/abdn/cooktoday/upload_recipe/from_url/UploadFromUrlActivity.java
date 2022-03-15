package com.abdn.cooktoday.upload_recipe.from_url;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.recipedetails.rvadapters.RecipeStepRVAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UploadFromUrlActivity extends AppCompatActivity
        implements PreviewIngredientItemRVAdapter.ItemClickListener, RecipeStepRVAdapter.ItemClickListener{

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
        Server.extractRecipe(url, new Server.RecipeExtractionResult() {
            @Override
            public void success(Recipe recipe) {
                setRecipe(recipe);
                loadViews();
                hideBottomSheet();
            }

            @Override
            public void error(int errorCode) {
                if (errorCode == 400)
                    showErrorToast("No recipe found on the website");
                else
                    showErrorToast("Oops! Something went wrong");
                errorCallback.error();
            }
        });
    }

    protected void showErrorToast(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_cooktoday_error, (ViewGroup) findViewById(R.id.toastCookTodayError));

        ImageView image = (ImageView) layout.findViewById(R.id.ivToastCookTodayError);
        image.setImageResource(R.drawable.ic_info_circle);
        image.setColorFilter(getResources().getColor(R.color.white));
        TextView text = (TextView) layout.findViewById(R.id.tvToastCookTodayError);
        text.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 35);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void handleUserResponse(boolean userWantsToDiscardRecipe) {

        // TODO

        if (userWantsToDiscardRecipe) {
            // do nothing, set focus of main edittext, remove link

        } else {
            // add recipe to user's cookbook, return to previous screen
        }
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
        discardBtn = (ExtendedFloatingActionButton) findViewById(R.id.btnRecipePreviewAddRecipe);
        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                finish();
            }
        });

        saveBtn = (ExtendedFloatingActionButton) findViewById(R.id.btnRecipePreviewDiscardRecipe);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outerScrollView.setVisibility(View.INVISIBLE);
                showBottomSheet();
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