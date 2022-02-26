package com.aron.cooktoday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aron.cooktoday.models.Recipe;
import com.squareup.picasso.Picasso;

public class RecipeDetailsActivity extends AppCompatActivity {

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // prevent navbars from flickering on transitions
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.bottomAppBar), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        // get the recipe
        this.recipe = (Recipe) getIntent().getSerializableExtra("RecipeObject");
        setRecipeDetails();
    }

    private void setRecipeDetails() {
        // recipe image
        Picasso.get()
                .load(this.recipe.getImgUrl())
                .into((ImageView) findViewById(R.id.ivRecipeDetailsImg));

        // recipe name
        ((TextView) findViewById(R.id.tvRecipeDetailsRecipeName)).setText(
                this.recipe.getName()
        );
    }
}