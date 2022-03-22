package com.abdn.cooktoday.upload_recipe.manual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.abdn.cooktoday.R;

import android.widget.EditText;

import com.abdn.cooktoday.utility.ProgressButtonHandler;
import com.google.android.material.button.MaterialButton;

import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UploadActivity extends AppCompatActivity {

    int PICK_IMAGE = 100;
    ImageView imageView;

    EditText input;
    ImageView enter;
    ListView listView;
    ArrayList<String> items;
    ArrayAdapter<String> adapter;

    EditText input_step;
    ImageView enter_step;
    ListView listViewSteps;
    ArrayList<String> steps;
    ArrayAdapter<String> adapter_steps;
    Toast t;
    ProgressButtonHandler btnUploadHandler;

    int total_hrs = 0;
    int total_min = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        imageView = findViewById(R.id.imageViewUpload);

        enter = (ImageView) findViewById(R.id.add);
        listView = (ListView)findViewById(R.id.listView);
        input = (EditText)findViewById(R.id.input);
        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,items);

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
                String name = items.get(i);
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

        MaterialButton uploadButton = (MaterialButton) findViewById(R.id.btn_mycookbook_upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call recipe creation endpoint on server
                // TODO
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
                // TODO Auto-generated method stub
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

    private void makeToast(String s){
        if(t!=null) t.cancel();
        t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
        t.show();
    }

    public void addItem(String item){
        items.add(item);
        listView.setAdapter(adapter);
    }

    public void addStep(String item){
        steps.add(item);
        listViewSteps.setAdapter(adapter_steps);
    }

    public void removeItem(int i){
        items.remove(i);
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