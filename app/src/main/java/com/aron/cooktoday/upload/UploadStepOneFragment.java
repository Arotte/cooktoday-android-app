package com.aron.cooktoday.upload;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aron.cooktoday.R;


public class UploadStepOneFragment extends Fragment {

    private ActivityResultLauncher<Pair<String,String>> pickFromGalleryLauncher;

    public UploadStepOneFragment() {
        // required empty public constructor
    }

    public static UploadStepOneFragment newInstance() {
        UploadStepOneFragment fragment = new UploadStepOneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pickFromGalleryLauncher = registerForActivityResult(
                new GetContentWithChooser(),
                result -> {
                    if (result != null) {
                        System.out.println("IMAGE SELECTED FROM GALLERY");
                        System.out.println(result);
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_upload_step1, container, false);

        // set up image chooser area
        View imgUploadArea = (LinearLayout) layout.findViewById(R.id.uploadImageArea);
        imgUploadArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGalleryLauncher.launch(Pair.create("image/*", "Select Image"));
            }
        });

        // inflate the layout for this fragment
        return layout;
    }
}