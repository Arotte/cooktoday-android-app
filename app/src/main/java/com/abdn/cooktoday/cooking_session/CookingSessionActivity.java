package com.abdn.cooktoday.cooking_session;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.cooking_session.dialog.FinishedCookingCallback;
import com.abdn.cooktoday.cooking_session.dialog.FinishedDialogFragment;
import com.abdn.cooktoday.cooking_session.dialog.IngredientsDialogFragment;
import com.abdn.cooktoday.cooking_session.rvadapters.RecipeStepRVAdapter;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.utility.ToastMaker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CookingSessionActivity extends AppCompatActivity implements RecipeStepRVAdapter.ItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    RecipeStepRVAdapter stepRVAdapter;
    RecyclerView rvSteps;

    View activeStepView;
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

        activeStepPos = -1;
        activeStepView = null;

        tvRecipeStep = findViewById(R.id.tvCookingSessStepDescription);
        tvRecipeStep.setText("");
        ((TextView) findViewById(R.id.tvCookingSessTotalStepsTitle)).setText(nSteps + " steps to complete");
        tvCurrentStep = findViewById(R.id.tvCookingSessStepCountTitle);
        tvCurrentStep.setText("Start Cooking");
    }

    @Override
    public void onStepItemClick(View view, int position) {
        activateItem(view, position);
    }

    private void activateItem(View view, int position) {
        if (position != activeStepPos && activeStepPos != -1) {
            stepRVAdapter.deactivateItem(activeStepPos, activeStepView);
        } else if (activeStepPos == -1) {
            ((TextView) findViewById(R.id.tvCookingSessTotalStepsTitle)).setText("/ " + nSteps);
        }
        tvCurrentStep.setText("Step " + (position + 1));
        activeStepView = view;
        activeStepPos = position;
        stepRVAdapter.activateItem(position, view);

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
                stepRVAdapter.markItemAsDone(activeStepPos, activeStepView);
                if (activeStepPos + 2 <= nSteps) {
                    rvSteps.smoothScrollToPosition(activeStepPos + 1);
                    activateItem(rvSteps.findViewHolderForAdapterPosition(activeStepPos + 1).itemView, activeStepPos + 1);
                }
                return true;
            case R.id.navbarCookingSessPrevious:
                if (activeStepPos - 1 >= 0) {
                    rvSteps.smoothScrollToPosition(activeStepPos - 1);
                    activateItem(rvSteps.findViewHolderForAdapterPosition(activeStepPos - 1).itemView, activeStepPos - 1);
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
                        LoggedInUser.user().addCookedRecipe(recipe);
                        ToastMaker.make("Recipe cooked!", ToastMaker.Type.SUCCESS, CookingSessionActivity.this);
                        // go back to previous activity
                        finish();
                    }
                    @Override
                    public void error(int errorCode) {
                        if (errorCode == 400) {
                            ToastMaker.make("Hooray, you cooked this recipe again!", ToastMaker.Type.SUCCESS, CookingSessionActivity.this);
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