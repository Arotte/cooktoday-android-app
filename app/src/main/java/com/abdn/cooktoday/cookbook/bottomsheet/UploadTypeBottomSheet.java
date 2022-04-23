package com.abdn.cooktoday.cookbook.bottomsheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.upload_recipe.from_url.UploadFromUrlActivity;
import com.abdn.cooktoday.upload_recipe.manual.UploadActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UploadTypeBottomSheet extends BottomSheetDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_mycookbook_upload_type, container, false);

        CardView manual = (CardView) v.findViewById(R.id.cvCookbookAddRecipeManually);
        CardView fromUrl = (CardView) v.findViewById(R.id.cvCookbookAddRecipeFromURL);

        // start manual upload flow
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                getActivity().startActivity(intent);
            }
        });

        // start upload from URL flow
        fromUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), UploadFromUrlActivity.class));
            }
        });

        // return inflated view
        return v;
    }

}