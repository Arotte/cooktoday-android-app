package com.abdn.cooktoday.upload_recipe.from_url;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.abdn.cooktoday.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;

public class PasteUrlBottomSheet extends BottomSheetDialogFragment {

    private enum ButtonState {
        DEFAULT,
        LOADING,
        SUCCESS,
    }

    private ProgressBar progressBar;
    private ImageView addIcon;
    private ImageView successIcon;
    private MaterialButton actionButton;
    private MaterialButton pasteButton;
    private ImageView deleteUrlButton;
    private EditText etURL;
    private ClipboardManager clipboard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_extract_recipe_from_url, container, false);

        progressBar = v.findViewById(R.id.pbAddRecipeUrl);
        addIcon = v.findViewById(R.id.icAddRecipeUrlAddIcon);
        successIcon = v.findViewById(R.id.icAddRecipeUrlSuccess);
        actionButton = v.findViewById(R.id.btnAddRecipeUrl);
        pasteButton = v.findViewById(R.id.btnAddRecipeUrlPasteLink);
        deleteUrlButton = v.findViewById(R.id.icAddRecipeUrlRemoveUrl);
        etURL = v.findViewById(R.id.etUploadFromUrlURL);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        setButtonState(ButtonState.DEFAULT);
        pasteButton.setVisibility(View.VISIBLE);
        deleteUrlButton.setVisibility(View.INVISIBLE);
        etURL.requestFocus();

        // url edit text
        etURL.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                int lfield = s.length();
                if (lfield > 0) {
                    pasteButton.setVisibility(View.INVISIBLE);
                    deleteUrlButton.setVisibility(View.VISIBLE);
                } else if (lfield == 0) {
                    pasteButton.setVisibility(View.VISIBLE);
                    deleteUrlButton.setVisibility(View.INVISIBLE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // paste button
        pasteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etURL.setText(clipboard.getPrimaryClip().getItemAt(0).getText().toString());
                etURL.setSelection(etURL.getText().length());
            }
        });

        // remove link button
        deleteUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etURL.setText("");
            }
        });

        // "Add recipe" button clicked
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonState(ButtonState.LOADING);
                String providedUrl = etURL.getText().toString();
                if (checkUrl(providedUrl)) {
                    ((UploadFromUrlActivity) getActivity()).getRecipeFromServer(
                            providedUrl,
                            new UploadFromUrlActivity.OnServerConnectionError() {
                                @Override
                                public void error() {
                                    setButtonState(ButtonState.DEFAULT);
                                }
                            });
                } else {
                    showErrorToast("Please provide a valid URL!");
                    setButtonState(ButtonState.DEFAULT);
                }
            }
        });

        // return inflated view
        return v;
    }

    private boolean checkUrl(String urlStr) {
        try {
            URL validUrl = new URL(urlStr);
            return true;
        }
        catch (MalformedURLException e) {
            return false;
        }
    }

    private void showErrorToast(String msg) {
        ((UploadFromUrlActivity) getActivity()).showErrorToast(msg);
    }

    private void setButtonState(ButtonState state) {
        if (state == ButtonState.DEFAULT) {
            progressBar.setVisibility(View.INVISIBLE);
            addIcon.setVisibility(View.VISIBLE);
            successIcon.setVisibility(View.INVISIBLE);
        } else if (state == ButtonState.LOADING) {
            progressBar.setVisibility(View.VISIBLE);
            addIcon.setVisibility(View.INVISIBLE);
            successIcon.setVisibility(View.INVISIBLE);
        } else if (state == ButtonState.SUCCESS) {
            progressBar.setVisibility(View.INVISIBLE);
            addIcon.setVisibility(View.INVISIBLE);
            successIcon.setVisibility(View.VISIBLE);
        }
    }

}