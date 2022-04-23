package com.abdn.cooktoday.upload_recipe.manual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.abdn.cooktoday.R;

import android.widget.EditText;

import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredSearchResultItemJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.media.AwsUploadedFilesJson;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.NerredIngred;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.upload_recipe.manual.bottomsheet.AddIngredBottomSheet;
import com.abdn.cooktoday.upload_recipe.manual.bottomsheet.OnIngredientAddedCallback;
import com.abdn.cooktoday.upload_recipe.manual.bottomsheet.AddStepBottomSheet;
import com.abdn.cooktoday.upload_recipe.manual.bottomsheet.OnStepAddedCallback;
import com.abdn.cooktoday.upload_recipe.manual.dialog.AddIngredDialogCallback;
import com.abdn.cooktoday.upload_recipe.manual.dialog.AddIngredDialogFragment;
import com.abdn.cooktoday.upload_recipe.manual.rvadapters.IngredSearchResultArrayAdapter;
import com.abdn.cooktoday.upload_recipe.manual.rvadapters.IngredientRVAdapter;
import com.abdn.cooktoday.upload_recipe.manual.rvadapters.StepRVAdapter;
import com.abdn.cooktoday.utility.ProgressButtonHandler;
import com.abdn.cooktoday.utility.ToastMaker;
import com.abdn.cooktoday.utility.Util;
import com.google.android.material.button.MaterialButton;

import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity
        implements IngredientRVAdapter.ItemClickListener, StepRVAdapter.ItemClickListener {

    // static fields
    private static final String TAG = "UploadActivity";
    private static final int ingredResultCount = 6;

    // image selection fields
    private int PICK_IMAGE = 100;
    private ImageView imageView;
    private String uploadedImageUrl;
    private Uri recipeImageFileUri;

    // edittext fields
    private EditText etRecipeName;
    private EditText etRecipeDesc;
    private EditText etServings;
    private EditText etCalories;

    // time selection fields
    private int total_hrs = 0;
    private int total_min = 0;
    private int cookingTime = 0;
    private int prepTime = 0;

    // ingredient addition fields
    private RecyclerView rvIngreds;
    private IngredientRVAdapter rvAdapterIngreds;
    private MaterialButton btnAddNewIngred;
    private List<NerredIngred> ingredients;
    private AddIngredBottomSheet addIngredBottomSheet;
    private int quantityColor;
    private int unitColor;
    private int nameColor;

    private Ingredient currentlyAddingIngredient;
    private AddIngredDialogFragment ingredDialog;

    // ingredient search fields
    private ListView ingredSearchList;
    private IngredSearchResultArrayAdapter searchResultAdapter;
    private EditText etIngredSearch;

    // step addition fields
    private RecyclerView rvSteps;
    private StepRVAdapter rvAdapterSteps;
    private MaterialButton btnAddNewStep;
    private List<String> steps;
    private AddStepBottomSheet addStepBottomSheet;

    // layout toggle fields
    private LinearLayout contentMainContainer;
    private LinearLayout contentRecipeIngredContainer;

    private ProgressButtonHandler btnUploadHandler;
    private Recipe recipe;

    // =============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        contentMainContainer = (LinearLayout) findViewById(R.id.add_recipe_content_main_container);
        contentRecipeIngredContainer = (LinearLayout) findViewById(R.id.add_recipe_content_search_container);
        showContentMain();

        quantityColor = getResources().getColor(R.color.facebook_tp);
        unitColor = getResources().getColor(R.color.google_tp);
        nameColor = getResources().getColor(R.color.primaryGreen_tp);

        initViews();
        ingredAdditionSetup();
        stepAdditionSetup();
    }

    private void initViews() {
        imageView = findViewById(R.id.imageViewUpload);
        etRecipeName = (EditText) findViewById(R.id.etUploadRecipeName);
        etRecipeDesc = (EditText) findViewById(R.id.etUploadRecipeDesc);
        etCalories = (EditText) findViewById(R.id.etUploadCalories);
        etServings = (EditText) findViewById(R.id.etUploadServings);

        initTimeProgressbars();
        initCancelButton();
        initIngredSearchLayout();

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
                uploadRecipe();
            }
        });
        btnUploadHandler = new ProgressButtonHandler(
                ((ProgressBar) findViewById(R.id.pbCookbook)),
                ((ImageView) findViewById(R.id.icCookbookDefaultIcon)),
                ((ImageView) findViewById(R.id.icCookbookSuccessIcon)));
    }

    private void initCancelButton() {
        Button cancel = findViewById(R.id.btnCancelUpload);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTimeProgressbars() {
        SeekBar seekBarCooking = (SeekBar) findViewById(R.id.sbCookingDuration);
        SeekBar seekBarPreparation = (SeekBar) findViewById(R.id.sbPreparation);
        TextView seekBarCookValue = (TextView)findViewById(R.id.DurationTime);
        TextView seekBarPrepValue = (TextView)findViewById(R.id.PreparationTime);
        TextView totalTime = (TextView)findViewById(R.id.TotalTime);
        seekBarCooking.setMax(3 * 6); // 3 times 10 minutes
        seekBarPreparation.setMax(3 * 6);

        // preparation time seekbar
        seekBarPreparation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int duration,
                                          boolean fromUser) {
                prepTime = duration;
                int hrs = duration / 6;
                int min = (duration % 6) * 10;
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

        // cooking time seekbar
        seekBarCooking.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int duration,
                                          boolean fromUser) {
                cookingTime = duration;
                int hrs = duration / 6;
                int min = (duration % 6) * 10;
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
    }

    // =============================================================================================
    // Recipe upload
    // =============================================================================================

    private void uploadRecipe() {
        btnUploadHandler.setState(ProgressButtonHandler.State.LOADING);
        // 1.) upload image to AWS
        if (recipeImageFileUri != null) {
            try {
                InputStream fin = getContentResolver().openInputStream(recipeImageFileUri);
                File imageFile = createFileFromIs(fin);
                Server.uploadRecipeImageToAws(LoggedInUser.user().getSessionID(), imageFile, new ServerCallbacks.AwsRecipeImgUploadResult() {
                    @Override
                    public void success(AwsUploadedFilesJson files) {
                        uploadedImageUrl = files.getFiles().get(0);
                        Log.i(TAG, "File successfully uploaded to AWS! Link: " + uploadedImageUrl);
                        // 2.) post recipe creation request to API
                        Recipe recipe = gatherRecipeDetails();
                        Server.createRecipe(
                                LoggedInUser.user().getSessionID(),
                                LoggedInUser.user().getServerID(),
                                recipe,
                                new ServerCallbacks.CreateRecipeResult() {
                                    @Override
                                    public void success(Recipe createdRecipe) {
                                        // recipe created on server
                                        btnUploadHandler.setState(ProgressButtonHandler.State.SUCCESS);
                                        Log.i(TAG, "Recipe successfully created!");
                                        ToastMaker.make("Recipe added!", ToastMaker.Type.SUCCESS, UploadActivity.this);
                                        LoggedInUser.user().newRecipeCreatedByUser(createdRecipe);
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
                    @Override
                    public void error(int errorCode) {
                        btnUploadHandler.setState(ProgressButtonHandler.State.DEFAULT);
                        Log.i(TAG, "Error while upload recipe image to AWS! Error code: " + errorCode);
                        ToastMaker.make("Oops! Something went wrong", ToastMaker.Type.ERROR, UploadActivity.this);
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // =============================================================================================
    // Image selection methods
    // =============================================================================================

    void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // called when image is selected from gallery
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageView.setImageURI(selectedImageUri);
                    recipeImageFileUri = selectedImageUri;
                    // make relative layout transparent
                    View relativeLayout = findViewById(R.id.uploadImageArea);
                    relativeLayout.setBackgroundResource(R.color.transparent);
                }
            }
        }
    }

    // =============================================================================
    // INGREDIENT ADDITION -- Ingredient Search
    // =============================================================================

    private void initIngredSearchLayout() {
        ingredSearchList = findViewById(R.id.lvIngredSearchResults);

        etIngredSearch = findViewById(R.id.ingredSearchBar);
        Util.setEditTextXIconListener(etIngredSearch);
        etIngredSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                int len = s.length();
                if (len >= 2) // at least 2 characters are present
                    searchIngredient(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // add ingred manually button
        findViewById(R.id.btnAddIngredManually).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContentMainWithIngredDialog();
            }
        });
    }

    private void searchIngredient(String query) {
        Server.searchIngredients(LoggedInUser.user().getSessionID(), query, new ServerCallbacks.IngredSearchCallback() {
            @Override
            public void success(List<IngredSearchResultItemJson> ingredients) {
                // search successfully performed
                updateSearchResults(ingredients);
            }
            @Override
            public void error(int errorCode) {
                // error while searching
                Log.i(TAG, "Error while searching!");
                // makeErrorToast();
            }
        });
    }

    private void updateSearchResults(List<IngredSearchResultItemJson> ingredients) {
        // select top items
        List<IngredSearchResultItemJson> topItems = new ArrayList<>();
        for (int i = 0; i < Math.min(ingredients.size(), ingredResultCount); i++) {
            topItems.add(ingredients.get(i));
        }

        // update search results
        searchResultAdapter = new IngredSearchResultArrayAdapter(UploadActivity.this, topItems);
        ingredSearchList.setAdapter(searchResultAdapter);

        // set onclick listeners
        if (ingredients.size() > 0) {
            ingredSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // get ingredient details from server
                    Server.getIngredientById(LoggedInUser.user().getSessionID(), searchResultAdapter.getItem(i).getId(), new ServerCallbacks.GetIngredientCallback() {
                        @Override
                        public void success(IngredientJson ingredientJson) {
                            // ingredient found
                            currentlyAddingIngredient = new Ingredient(ingredientJson);
                            // toggle views, and show ingredient addition dialog
                            showContentMainWithIngredDialog();
                        }
                        @Override
                        public void error(int errorCode) {
                            Log.i(TAG, "Could not get ingredient '" + errorCode + "'.");
                        }
                    });
                }
            });
        }
    }

    // =============================================================================
    // INGREDIENT ADDITION
    // =============================================================================

    private void showContentMainWithIngredDialog() {
        Util.hideKeyboardIfVisible(UploadActivity.this, etIngredSearch);
        showContentMain();
        showIngredAdditionDialog();
    }

    private void showIngredAdditionDialog() {
        ingredDialog = new AddIngredDialogFragment(currentlyAddingIngredient, new AddIngredDialogCallback() {
            @Override
            public void added(Ingredient ingredient) {
                // ingredient added
                addIngredientToRecipe(ingredient);
            }
            @Override
            public void cancelled() {
                // ingredient addition cancelled
            }
        });
        ingredDialog.show(getSupportFragmentManager(), "ingred_add_dialog");
    }

    private void addIngredientToRecipe(Ingredient ingredient) {
        // add ingredient to recipe

    }

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
        showContentIngredSearch();
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
        // called when bottom sheet is closed

        // insert new ingredient
        ingredients.add(ingred);
        rvAdapterIngreds.notifyItemInserted(ingredients.size() - 1);
    }

    // =============================================================================
    // STEP ADDITION
    // =============================================================================

    private void stepAdditionSetup() {
        steps = new ArrayList<>();
        rvSteps = (RecyclerView) findViewById(R.id.rvCreateRecipeSteps);
        rvSteps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSteps.setNestedScrollingEnabled(false);

        rvAdapterSteps = new StepRVAdapter(this, steps);
        rvAdapterSteps.setClickListener(this);

        rvSteps.setAdapter(rvAdapterSteps);

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

    // =============================================================================
    // Helper methods
    // =============================================================================

    private void showContentMain() {
        contentRecipeIngredContainer.setVisibility(View.GONE);
        contentMainContainer.setVisibility(View.VISIBLE);
    }

    private void showContentIngredSearch() {
        contentMainContainer.setVisibility(View.GONE);
        contentRecipeIngredContainer.setVisibility(View.VISIBLE);
    }

    private Recipe gatherRecipeDetails() {
        List<Ingredient> ingreds = new ArrayList<>();
        for (NerredIngred nerIngred : ingredients)
            ingreds.add(new Ingredient(nerIngred.getOriginal(), nerIngred.getQuantity()));

        return new Recipe(
                etRecipeName.getText().toString(),
                "",
                etRecipeDesc.getText().toString(),
                uploadedImageUrl,
                Integer.parseInt(etServings.getText().toString()),
                Integer.parseInt(etCalories.getText().toString()),
                prepTime,
                cookingTime,
                steps,
                ingreds);
    }

    private File createFileFromIs(InputStream is) {
        File file = new File("data/data/com.abdn.cooktoday/temp.jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try( OutputStream outputStream = new FileOutputStream(file) ) {
            IOUtils.copy(is, outputStream);
        } catch (FileNotFoundException e) {
            // handle exception here
        } catch (IOException e) {
            // handle exception here
        }
        return file;
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
}