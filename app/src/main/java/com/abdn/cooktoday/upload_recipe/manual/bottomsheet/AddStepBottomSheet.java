package com.abdn.cooktoday.upload_recipe.manual.bottomsheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.upload_recipe.from_url.UploadFromUrlActivity;
import com.abdn.cooktoday.upload_recipe.manual.UploadActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class AddStepBottomSheet extends BottomSheetDialogFragment {

    private EditText et;
    private MaterialButton btn;
    private OnStepAddedCallback callback;

    public AddStepBottomSheet(OnStepAddedCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_upload_add_step, container, false);

        setupViews(v);

        // return inflated view
        return v;
    }

    private void setupViews(View v) {
        et = (EditText) v.findViewById(R.id.etStepDesc);
        btn = (MaterialButton) v.findViewById(R.id.btnAddStep);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stepDesc = et.getText().toString();
                if (!stepDesc.isEmpty()) {
                    callback.success(stepDesc);
                    AddStepBottomSheet.this.dismiss();
                }
            }
        });
    }
}