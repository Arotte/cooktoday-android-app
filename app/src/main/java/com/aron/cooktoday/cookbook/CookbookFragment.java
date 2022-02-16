package com.aron.cooktoday.cookbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aron.cooktoday.R;
import com.aron.cooktoday.upload.UploadActivity;

public class CookbookFragment extends Fragment {

    ImageView ivUploadNew;

    public CookbookFragment() {
        // required empty public constructor
    }

    public static CookbookFragment newInstance() {
        CookbookFragment fragment = new CookbookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cookbook, container, false);

        ivUploadNew = (ImageView) view.findViewById(R.id.ivUploadNewRecipe);
        ivUploadNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UploadActivity.class));
            }
        });

        return view;
    }
}