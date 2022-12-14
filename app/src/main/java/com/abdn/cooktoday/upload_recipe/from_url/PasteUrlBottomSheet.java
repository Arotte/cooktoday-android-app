package com.abdn.cooktoday.upload_recipe.from_url;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.utility.ToastMaker;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * PasteUrlBottomSheet
 *
 * Bottom sheet and features an edit text to paste a
 * url and a button to submit the url.
 *
 * When the user submits the url, the url is checked
 * for validity and if valid, the url is passed to
 * to server for processing.
 */
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

        // add fade
//        etURL.measure(0, 0);
//        Shader textShader=new LinearGradient(0, 0, etURL.getMeasuredWidth(), 0,
//                new int[]{Color.BLACK, Color.WHITE},
//                new float[]{0, 1}, Shader.TileMode.CLAMP);
//        etURL.getPaint().setShader(textShader);


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

        // cancel button
        v.findViewById(R.id.ivAddUrlBottomSheetCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        // return inflated view
        return v;
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
        // getActivity().finish();
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
        ToastMaker.make(msg, ToastMaker.Type.ERROR, getActivity());
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