package com.aron.cooktoday.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.aron.cooktoday.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;

public class SearchFilterBottomSheet extends BottomSheetDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_search_filter, container, false);

        Button doneButton = v.findViewById(R.id.btnSearchFilterDone);
        Button closeButton = v.findViewById(R.id.btnSearchFilterCancel);

        // category selection
        ThemedToggleButtonGroup themedToggleButtonGroup = v.findViewById(R.id.searchFilterCategories);
        themedToggleButtonGroup.selectButton(R.id.searchFilterCategoryAll);
        themedToggleButtonGroup.setOnSelectListener((ThemedButton btn) -> {
            // handle selected button

            // Display the currently selected buttons
            List<ThemedButton> selectedButtons = themedToggleButtonGroup.getSelectedButtons();
            StringBuilder selectedBtns = new StringBuilder("Categories: ");
            for (ThemedButton cbtn : selectedButtons) {
                selectedBtns.append(cbtn.getText());
                selectedBtns.append(", ");
            }
            selectedBtns.setLength(selectedBtns.length() - 2);
            Toast.makeText(getActivity(), selectedBtns, Toast.LENGTH_SHORT).show();

            return null;
        });

        // DONE button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Search Filter Applied", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        // CANCEL button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Search Filter Not Applied", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        // return inflated view
        return v;
    }

}