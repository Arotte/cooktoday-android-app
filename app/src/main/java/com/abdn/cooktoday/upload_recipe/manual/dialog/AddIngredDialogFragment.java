package com.abdn.cooktoday.upload_recipe.manual.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.local_data.model.Ingredient;

import java.util.ArrayList;

public class AddIngredDialogFragment extends DialogFragment {
    public static final String TAG = "AddIngredientDialog";

    private final AddIngredDialogCallback callback;
    private final Ingredient ingredient;

    // ingredient fields
    private EditText name;
    private EditText quantity;
    private EditText unit;
    private EditText comment;
    private EditText processing;

    public AddIngredDialogFragment(Ingredient ingredient, AddIngredDialogCallback callback) {
        this.callback = callback;
        if (ingredient != null)
            this.ingredient = ingredient;
        else
            this.ingredient = new Ingredient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_upload_dialog_addingred_panel, container, false);
        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        // init ingredient fields
        name = view.findViewById(R.id.etNewIngredName);
        quantity = view.findViewById(R.id.etNewIngredQuantity);
        unit = view.findViewById(R.id.etNewIngredUnit);
        comment = view.findViewById(R.id.etNewIngredComment);
        processing = view.findViewById(R.id.etNewIngredProcessing);

        setFieldsIfPresent();

        // ingredient added button
        view.findViewById(R.id.btnFinishedCooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checks
                if (validateIngredient()) {
                    addIngred();
                    callback.added(ingredient);
                    dismiss();
                }
            }
        }); // end ingredient added button

        // cancel button
        view.findViewById(R.id.btnNotFinishedCooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.cancelled();
                dismiss();
            }
        }); // end cancel button

        return view;
    }

    private void setFieldsIfPresent() {
        if (ingredient != null) {
            if (ingredient.getName() != null)
                name.setText(ingredient.getName());
            if (ingredient.getQuantity() != null)
                quantity.setText(ingredient.getQuantity());
            if (ingredient.getDefaultUnit() != null)
                unit.setText(ingredient.getDefaultUnit());
            if (ingredient.getComment() != null)
                comment.setText(ingredient.getComment());
            if (ingredient.getProcessingMethod() != null)
                processing.setText(ingredient.getProcessingMethod());
        }
    }

    private void addIngred() {
        ingredient.setName(name.getText().toString());
        ingredient.setQuantity(quantity.getText().toString());
        ingredient.setUnit(unit.getText().toString());
        ingredient.setComment(comment.getText().toString());
        ingredient.setProcessingMethod(processing.getText().toString());
        ingredient.setDefaultUnit(unit.getText().toString());

        // set dummy values for the rest
        ingredient.setDescription("");
        ingredient.setDiet(new ArrayList<String>());
        ingredient.setProtein(0);
        ingredient.setCarbs(0);
        ingredient.setFats(0);
    }

    private boolean validateIngredient() {
        // check if the currently added ingredient
        // has all the fields filled in

        boolean nameOk = !name.getText().toString().isEmpty();
        boolean quantityOk = !quantity.getText().toString().isEmpty();
        boolean unitOk = !unit.getText().toString().isEmpty();
        boolean commentOk = !comment.getText().toString().isEmpty();
        boolean processingOk = !processing.getText().toString().isEmpty();

        if (!nameOk)
            name.setError("Name is required");
        if (!quantityOk)
            quantity.setError("Quantity is required");
        if (!unitOk)
            unit.setError("Unit is required");
        if (!commentOk)
            comment.setError("Comment is required");
        if (!processingOk)
            processing.setError("Processing method is required");

        return nameOk && quantityOk && unitOk && commentOk && processingOk;
    }
}
