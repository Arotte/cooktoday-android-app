package com.abdn.cooktoday.cooking_session;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.cooking_session.dialog.FinishedCookingCallback;
import com.abdn.cooktoday.cooking_session.dialog.FinishedDialogFragment;
import com.abdn.cooktoday.cooking_session.dialog.IngredientsDialogFragment;
import com.abdn.cooktoday.cooking_session.rvadapters.RecipeStepRVAdapter;
import com.abdn.cooktoday.local_data.LocalRecipes;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.utility.ToastMaker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CookSessionActivity
 *
 * This activity is started when the user taps on the
 * "Cook" action button on any recipe details screen.
 *
 * It displays relevant information about the recipe.
 *
 * When the user finished with cooking the recipe,
 * it sends a cooking finished confirmation to the server.
 *
 * The `rvadapters` sub-package of this `com.abdn.cooktoday.cooking_session`
 * package contains the RecyclerView adapters used in this activity.
 *
 * The `dialog` sub-package of this `com.abdn.cooktoday.cooking_session`
 * package contains the dialogs used in this activity.
 */
public class CookingSessionActivity extends AppCompatActivity implements RecipeStepRVAdapter.ItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "CookingSessionActivity";

    RecipeStepRVAdapter stepRVAdapter;
    RecyclerView rvSteps;

    int activeStepPos;
    List<String> stepTexts;
    int nSteps;

    TextView tvRecipeStep;
    TextView tvCurrentStep;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_session_v1);

        recipe = (Recipe) getIntent().getSerializableExtra("RecipeObject");

        // init recipe steps
        initRecipeStepTexts();
        List<String> stepTypes = new ArrayList<>(Arrays.asList("Cooking", "Prep", "Prep", "OPTIONAL"));

        // set up bottom navbar
        BottomNavigationView navigation = findViewById(R.id.cookingSessionBottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setBackground(null);
        navigation.getMenu().setGroupCheckable(0, false, true);

        // fill up view
        rvSteps = findViewById(R.id.rvCookingSessSTEPS);
        rvSteps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // rvSteps.setNestedScrollingEnabled(false);
        stepRVAdapter = new RecipeStepRVAdapter(this, stepTexts, stepTypes);
        stepRVAdapter.setClickListener(this);
        rvSteps.setAdapter(stepRVAdapter);

        tvRecipeStep = findViewById(R.id.tvCookingSessStepDescription);
        tvRecipeStep.setText("");
        ((TextView) findViewById(R.id.tvCookingSessTotalStepsTitle)).setText(nSteps + " steps to complete");
        tvCurrentStep = findViewById(R.id.tvCookingSessStepCountTitle);
        tvCurrentStep.setText("Start Cooking");

        activeStepPos = -1;
    }

    @Override
    public void onStepItemClick(View view, int position) {
        activateItem(position);
    }

    private void activateItem(int position) {
        Log.i(TAG, "Activating item at position " + position);
        if (position != activeStepPos && activeStepPos != -1) {
            stepRVAdapter.deactivateItem(activeStepPos);
        } else if (activeStepPos == -1) {
            ((TextView) findViewById(R.id.tvCookingSessTotalStepsTitle)).setText("/ " + nSteps);
        }
        tvCurrentStep.setText("Step " + (position + 1));
        activeStepPos = position;
        stepRVAdapter.activateItem(position);

        tvRecipeStep.setText(Html.fromHtml(stepTexts.get(position)));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navbarCookingSessDone:
                handleDone();
                return true;
            case R.id.navbarCookingSessNext:
                Log.i("CookingSessionActivity", "Marking Step " + activeStepPos + " as done.");
                stepRVAdapter.markItemAsDone(activeStepPos);
                if (activeStepPos + 1 <= nSteps - 1) {
                    rvSteps.smoothScrollToPosition(activeStepPos + 1);
                    activateItem(activeStepPos + 1);
                }
                return true;
            case R.id.navbarCookingSessPrevious:
                if (activeStepPos - 1 >= 0) {
                    rvSteps.smoothScrollToPosition(activeStepPos - 1);
                    activateItem(activeStepPos - 1);
                }
                return true;

            case R.id.navbarCookingSessIngred:
                // Dialog dialog = new Dialog(new ContextThemeWrapper(this, R.style.DialogSlideAnim));
                List<Ingredient> dummyIngreds = new ArrayList<>(Arrays.asList(
                        new Ingredient("Eggs", "2 pieces"),
                        new Ingredient("Bacon", "200g"),
                        new Ingredient("Water", "1l"),
                        new Ingredient("Weed", "10g"),
                        new Ingredient("Sliced bacon", "100g"),
                        new Ingredient("Salt", "to taste")
                ));

                IngredientsDialogFragment dialog = new IngredientsDialogFragment(recipe.getIngredients());
                // dialog.setEnterTransition(R.anim.slide_in);
                dialog.show(getSupportFragmentManager(), "dialog");
                return true;
        }
        return false;
    }

    private void handleDone() {
        // show dialog
        FinishedDialogFragment dialog = new FinishedDialogFragment(new FinishedCookingCallback() {
            @Override
            public void finished() {
                // send "cooked" status of recipe to server
                Server.cookRecipe(LoggedInUser.user().getSessionID(), recipe.getServerId(), new ServerCallbacks.CookRecipeCallback() {
                    @Override
                    public void success() {
                        // add recipe to user's list of cooked recipes
                        LocalRecipes.i().recipeCooked(recipe.getServerId());
                        ToastMaker.success("Recipe cooked!", CookingSessionActivity.this);

                        // go back to previous activity
                        finish();
                    }
                    @Override
                    public void error(int errorCode) {
                        if (errorCode == 400) {
                            ToastMaker.success("Hooray, you cooked this recipe again!", CookingSessionActivity.this);
                            // go back to previous activity
                            finish();
                        }
                    }
                });
            }

            @Override
            public void notFinished() {
                // do nothing
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    private void initRecipeStepTexts() {
        stepTexts = recipe.getStepDescriptions();
        nSteps = stepTexts.size();
        // replace "."s with two newlines
        for (int i = 0; i < nSteps; i++) {
            stepTexts.set(i,
                    stepTexts.get(i).replaceAll("\\.", ".<br><br>"));
        }
    }
}