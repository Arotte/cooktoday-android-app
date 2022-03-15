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
import com.abdn.cooktoday.cooking_session.dialog.IngredientsDialogFragment;
import com.abdn.cooktoday.cooking_session.rvadapters.RecipeStepRVAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_session_v1);

        // init recipe steps
        // TODO: GET THEM FROM PREVIOUS ACTIVITY
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
                Log.i("CookingSessionActivity", "Marking Step " + activeStepPos + " as done.");
                stepRVAdapter.markItemAsDone(activeStepPos, activeStepView);
                return true;
            case R.id.navbarCookingSessNext:
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
                IngredientsDialogFragment dialog = new IngredientsDialogFragment();
                dialog.setEnterTransition(R.anim.slide_in);
                dialog.show(getSupportFragmentManager(), "dialog");

                return true;
        }
        return false;
    }

    private void initRecipeStepTexts() {
        stepTexts = new ArrayList<>(Arrays.asList(
                "In a medium-size mixing bowl or large glass measuring cup, <b>whisk together</b> your dry ingredients.",
                "Heat olive oil in a large, oven-proof non stick pan (or a well-seasoned cast iron skillet) over medium-high heat. Sear chicken thighs for 3 minutes each side, until the skin becomes golden and crisp. Leave 2 tablespoons of chicken juices in the pan for added flavour, and drain any excess.",
                "Fry the garlic in the same pan around the chicken for 1 minute until fragrant. Add the honey, both mustards, and water to the pan, mixing well, and combine all around the chicken.",
                "OPTIONAL: Remove from the oven after 30 minutes; add in the green beans (mixing them through the sauce), and return to the oven to bake for a further 15 minutes, or until the chicken is completely cooked through and no longer pink in the middle, and the potatoes are fork tender."
        ));
        nSteps = stepTexts.size();

        // replace "."s with two newlines
        for (int i = 0; i < nSteps; i++) {
            stepTexts.set(i,
                    stepTexts.get(i).replaceAll("\\.", ".<br><br>"));
        }
    }
}