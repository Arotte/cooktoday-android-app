package com.abdn.cooktoday.upload_recipe.manual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.abdn.cooktoday.R;

import android.widget.EditText;

import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.NerredIngred;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.upload_recipe.manual.bottomsheet.AddIngredBottomSheet;
import com.abdn.cooktoday.upload_recipe.manual.bottomsheet.OnIngredientAddedCallback;
import com.abdn.cooktoday.upload_recipe.manual.bottomsheet.AddStepBottomSheet;
import com.abdn.cooktoday.upload_recipe.manual.bottomsheet.OnStepAddedCallback;
import com.abdn.cooktoday.upload_recipe.manual.rvadapters.IngredientRVAdapter;
import com.abdn.cooktoday.upload_recipe.manual.rvadapters.StepRVAdapter;
import com.abdn.cooktoday.utility.ProgressButtonHandler;
import com.abdn.cooktoday.utility.ToastMaker;
import com.google.android.material.button.MaterialButton;

import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// TODO: send image to server
public class UploadActivity extends AppCompatActivity
        implements IngredientRVAdapter.ItemClickListener, StepRVAdapter.ItemClickListener {

    private static final String TAG = "UploadActivity";

    int PICK_IMAGE = 100;
    ImageView imageView;

    EditText etRecipeName;
    EditText etRecipeDesc;
    EditText etServings;
    EditText etCalories;

    ProgressButtonHandler btnUploadHandler;

    int total_hrs = 0;
    int total_min = 0;
    int cookingTime = 0;
    int prepTime = 0;

    // adding new ingredients
    RecyclerView rvIngreds;
    IngredientRVAdapter rvAdapterIngreds;
    MaterialButton btnAddNewIngred;
    List<NerredIngred> ingredients;
    AddIngredBottomSheet addIngredBottomSheet;
    private int quantityColor;
    private int unitColor;
    private int nameColor;

    // adding new steps
    RecyclerView rvSteps;
    StepRVAdapter rvAdapterSteps;
    MaterialButton btnAddNewStep;
    List<String> steps;
    AddStepBottomSheet addStepBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        quantityColor = getResources().getColor(R.color.facebook_tp);
        unitColor = getResources().getColor(R.color.google_tp);
        nameColor = getResources().getColor(R.color.primaryGreen_tp);

        initViews();
        ingredAdditionSetup();
        stepAdditionSetup();
    }

    private Recipe gatherRecipeDetails() {
        List<Ingredient> ingreds = new ArrayList<>();
        for (NerredIngred nerIngred : ingredients)
            ingreds.add(new Ingredient(nerIngred.getOriginal(), nerIngred.getQuantity()));

        return new Recipe(
                etRecipeName.getText().toString(),
                "",
                etRecipeDesc.getText().toString(),
                "",
                Integer.parseInt(etServings.getText().toString()),
                Integer.parseInt(etCalories.getText().toString()),
                prepTime,
                cookingTime,
                steps,
                ingreds);
    }

    private void initViews() {
        imageView = findViewById(R.id.imageViewUpload);

        etRecipeName = (EditText) findViewById(R.id.etUploadRecipeName);
        etRecipeDesc = (EditText) findViewById(R.id.etUploadRecipeDesc);
        etCalories = (EditText) findViewById(R.id.etUploadCalories);
        etServings = (EditText) findViewById(R.id.etUploadServings);

        Button cancel = findViewById(R.id.btnCancelUpload);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set up image chooser area
        View imgUploadArea = (RelativeLayout) findViewById(R.id.uploadImageArea);
        imgUploadArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pickFromGalleryLauncher.launch(Pair.create("image/*", "Select Image"));
                openGallery();
            }
        });

        // UPLOAD RECIPE ACTION BUTTON
        MaterialButton uploadButton = (MaterialButton) findViewById(R.id.btn_mycookbook_upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gather recipe details entered by the user
                btnUploadHandler.setState(ProgressButtonHandler.State.LOADING);
                Recipe recipe = gatherRecipeDetails();
                // make recipe creation request to the server
                Server.createRecipe(
                        LoggedInUser.user().getSessionID(),
                        LoggedInUser.user().getServerID(),
                        recipe,
                        new Server.CreateRecipeResult() {
                            @Override
                            public void success(Recipe recipe) {
                                // recipe created on server
                                btnUploadHandler.setState(ProgressButtonHandler.State.SUCCESS);
                                Log.i(TAG, "Recipe successfully created!");
                                ToastMaker.make("Recipe added!", ToastMaker.Type.SUCCESS, UploadActivity.this);
                                finish();
                            }
                            @Override
                            public void error(int errorCode) {
                                // error while creating recipe on server
                                btnUploadHandler.setState(ProgressButtonHandler.State.DEFAULT);
                                Log.i(TAG, "Error while creating recipe on server! Error code: " + errorCode);
                                ToastMaker.make("Oops! Something went wrong", ToastMaker.Type.ERROR, UploadActivity.this);
                            }});
            }
        });

        SeekBar seekBarCooking = (SeekBar) findViewById(R.id.sbCookingDuration);
        SeekBar seekBarPreparation = (SeekBar) findViewById(R.id.sbPreparation);
        TextView seekBarCookValue = (TextView)findViewById(R.id.DurationTime);
        TextView seekBarPrepValue = (TextView)findViewById(R.id.PreparationTime);
        TextView totalTime = (TextView)findViewById(R.id.TotalTime);
        seekBarCooking.setMax(6 * 60); //24 hours and 4 step in one hour.
        seekBarPreparation.setMax(6 * 60);

        //addTime(seekBarCookValue.getText().toString(), seekBarPrepValue.getText().toString(), totalTime);

        seekBarCooking.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int duration,
                                          boolean fromUser) {
                cookingTime = duration;
                int hrs = duration / 60;
                int min = (duration % 60);
                String minutes = String.format("%02d", min);
                String newTime = hrs+"h "+minutes+"m";
                seekBarCookValue.setText(newTime);
                sumTime(total_hrs,hrs,total_min,min,totalTime);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        seekBarPreparation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int duration,
                                          boolean fromUser) {
                prepTime = duration;
                int hrs = duration / 60;
                int min = (duration % 60);
                total_hrs = hrs;
                total_min = min;
                String minutes = String.format("%02d", min);
                String newTime = hrs+"h "+minutes+"m";
                totalTime.setText(newTime);
                seekBarPrepValue.setText(newTime);
                //addTime(seekBarCookValue.getText().toString(), seekBarPrepValue.getText().toString(), totalTime);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        btnUploadHandler = new ProgressButtonHandler(
                ((ProgressBar) findViewById(R.id.pbCookbook)),
                ((ImageView) findViewById(R.id.icCookbookDefaultIcon)),
                ((ImageView) findViewById(R.id.icCookbookSuccessIcon)));
    }

    public static void sumTime(int h1, int h2, int m1, int m2, TextView totalTime){
        int minSum = m1 + m2;
        int hourSum = 0;

        if(minSum>59)
        {
            hourSum+=1;
            minSum%=60;
        }
        hourSum = hourSum + h1 + h2;
        String total = hourSum+"h "+minSum+"m";
        totalTime.setText(total);
    }


    void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        // Sets the type as image/*. This ensures only components of type image are selected
        //intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageView.setImageURI(selectedImageUri);
                    // make relative layout transparent
                    View relativeLayout = findViewById(R.id.uploadImageArea);
                    relativeLayout.setBackgroundResource(R.color.transparent);
                }
            }
        }
    }

    // =============================================================================
    // INGREDIENT ADDITIONS
    // =============================================================================

    private void ingredAdditionSetup() {
        // ingredients recyclerview
        rvIngreds = (RecyclerView) findViewById(R.id.rvCreateRecipeIngreds);
        rvIngreds.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIngreds.setNestedScrollingEnabled(false);

        ingredients = new ArrayList<>();
        rvAdapterIngreds = new IngredientRVAdapter(this, ingredients, quantityColor, unitColor, nameColor);
        rvAdapterIngreds.setClickListener(this);

        rvIngreds.setAdapter(rvAdapterIngreds);

        // bottom sheet
        addIngredBottomSheet = new AddIngredBottomSheet(new OnIngredientAddedCallback() {
            @Override
            public void success(NerredIngred ingred) {
                onAddIngredFinished(ingred);
            }
        }, quantityColor, unitColor, nameColor);

        if (addIngredBottomSheet.isVisible())
            addIngredBottomSheet.dismiss();

        // add new ingred button
        btnAddNewIngred = (MaterialButton) findViewById(R.id.btnCreateRecipeAddIngred);
        btnAddNewIngred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewIngredBtnClick();
            }
        });
    }

    private void onAddNewIngredBtnClick() {
        // show ingred addition bottom sheet
        showIngredBottomSheet();
    }

    @Override
    public void onIngredItemClick(View view, int position) {
    }

    @Override
    public void onIngredItemLongClick(View view, int position) {
        // delete item
        ingredients.remove(position);
        rvAdapterIngreds.notifyItemRemoved(position);
    }


    private void showIngredBottomSheet() {
        addIngredBottomSheet.show(UploadActivity.this.getSupportFragmentManager(), "ModalBottomSheet");
    }

    private void onAddIngredFinished(NerredIngred ingred) {
        // insert new ingredient
        ingredients.add(ingred);
        rvAdapterIngreds.notifyItemInserted(ingredients.size() - 1);
    }

    // =============================================================================
    // STEP ADDITIONS
    // =============================================================================

    private void stepAdditionSetup() {
        steps = new ArrayList<>();
        rvSteps = (RecyclerView) findViewById(R.id.rvCreateRecipeSteps);
        rvSteps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSteps.setNestedScrollingEnabled(false);

        rvAdapterSteps = new StepRVAdapter(this, steps);
        rvAdapterSteps.setClickListener(this);

        rvIngreds.setAdapter(rvAdapterIngreds);

        // bottom sheet
        addStepBottomSheet = new AddStepBottomSheet(new OnStepAddedCallback() {
            @Override
            public void success(String stepDesc) {
                // add step to recyclerview
                steps.add(stepDesc);
                rvAdapterSteps.notifyItemInserted(steps.size() - 1);
            }
        });

        if (addStepBottomSheet.isVisible())
            addStepBottomSheet.dismiss();

        // add new step button
        btnAddNewStep = (MaterialButton) findViewById(R.id.btnCreateRecipeAddStep);
        btnAddNewStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show step addition bottom sheet
                addStepBottomSheet.show(UploadActivity.this.getSupportFragmentManager(), "ModalBottomSheet");
            }
        });

    }

    @Override
    public void onStepItemClick(View view, int position) {
    }

    @Override
    public void onStepItemLongClick(View view, int position) {
    }
}