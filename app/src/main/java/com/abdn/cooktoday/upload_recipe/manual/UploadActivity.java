package com.abdn.cooktoday.upload_recipe.manual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.abdn.cooktoday.R;

import android.widget.EditText;

import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.utility.ProgressButtonHandler;
import com.abdn.cooktoday.utility.ToastMaker;
import com.google.android.material.button.MaterialButton;

import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// TODO: send image to server
public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "UploadActivity";

    int PICK_IMAGE = 100;
    ImageView imageView;

    EditText input;
    EditText etRecipeName;
    EditText etRecipeDesc;
    EditText etServings;
    EditText etCalories;

    ImageView enter;
    ListView listView;
    ArrayList<String> ingredients;
    ArrayAdapter<String> adapter;

    EditText input_step;
    ImageView enter_step;
    ListView listViewSteps;
    ArrayList<String> steps;
    ArrayAdapter<String> adapter_steps;
    ProgressButtonHandler btnUploadHandler;

    int total_hrs = 0;
    int total_min = 0;
    int cookingTime = 0;
    int prepTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        initViews();
    }


    private Recipe gatherRecipeDetails() {
        List<Ingredient> ingreds = new ArrayList<>();
        for (String ingredStr : ingredients)
            ingreds.add(new Ingredient(ingredStr, ""));

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

        enter = (ImageView) findViewById(R.id.add);
        listView = (ListView)findViewById(R.id.listView);
        input = (EditText)findViewById(R.id.input);
        ingredients = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, ingredients);

        enter_step = (ImageView) findViewById(R.id.add_step);
        listViewSteps = (ListView)findViewById(R.id.listViewSteps);
        input_step = (EditText)findViewById(R.id.input_step);
        steps = new ArrayList<>();
        adapter_steps = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,steps);

        ViewCompat.setNestedScrollingEnabled(listView, true);
        ViewCompat.setNestedScrollingEnabled(listViewSteps, true);

        listView.setAdapter(adapter);
        listViewSteps.setAdapter(adapter_steps);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = ingredients.get(i);
                //makeToast(name);
            }
        });

        listViewSteps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = steps.get(i);
                //makeToast(name);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //makeToast("Removed: " + items.get(i));
                removeItem(i);
                return false;
            }
        });

        listViewSteps.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //makeToast("Removed: " + steps.get(i));
                removeStep(i);
                return false;
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input.getText().toString();
                if(text == null || text.length() == 0){
                    //makeToast("Enter an item.");
                }else{
                    input.setText("");
                    addItem(text);
                    //makeToast("Added: " + text);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        enter_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input_step.getText().toString();
                if(text == null || text.length() == 0){
                    //makeToast("Enter an item.");
                }else{
                    input_step.setText("");
                    addStep(text);
                    //makeToast("Added: " + text);
                    adapter_steps.notifyDataSetChanged();
                }
            }
        });

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

    public void addItem(String item){
        ingredients.add(item);
        listView.setAdapter(adapter);
    }

    public void addStep(String item){
        steps.add(item);
        listViewSteps.setAdapter(adapter_steps);
    }

    public void removeItem(int i){
        ingredients.remove(i);
        adapter.notifyDataSetChanged();
    }

    public void removeStep(int i){
        steps.remove(i);
        adapter_steps.notifyDataSetChanged();
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
}