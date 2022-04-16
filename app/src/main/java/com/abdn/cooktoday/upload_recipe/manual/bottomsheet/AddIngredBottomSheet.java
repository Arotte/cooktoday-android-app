package com.abdn.cooktoday.upload_recipe.manual.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.NerredIngred;
import com.abdn.cooktoday.utility.ProgressButtonHandler;
import com.abdn.cooktoday.utility.ToastMaker;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddIngredBottomSheet extends BottomSheetDialogFragment {
    private static final String TAG = "AddIngredBottomSheet";

    private EditText etIngred;
    private NerredIngred nerredIngred;

    // action button
    private ProgressButtonHandler.State btnState;
    private ProgressButtonHandler btnHandler;
    private Button btnAction;
    private ProgressBar pbBtn;
    private ImageView ivBtnAction;
    private ImageView ivBtnSuccess;

    private boolean editAfterResult;

    private AddIngredFinishedCallback callback;
    private int quantityColor;
    private int unitColor;
    private int nameColor;

    public AddIngredBottomSheet(AddIngredFinishedCallback cb,
                                int quantityColor,
                                int unitColor,
                                int nameColor) {
        this.callback = cb;
        this.quantityColor = quantityColor;
        this.unitColor = unitColor;
        this.nameColor = nameColor;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);

        // show keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_upload_add_ingred, container, false);

        initButton(v);
        initEtIngred(v);
        focusEditText();
        editAfterResult = false;

        // return inflated view
        return v;
    }

    private void initButton(View v) {
        btnAction = (Button) v.findViewById(R.id.btnAction);
        pbBtn = (ProgressBar) v.findViewById(R.id.pb);
        ivBtnAction = (ImageView) v.findViewById(R.id.icAction);
        ivBtnSuccess = (ImageView) v.findViewById(R.id.icSuccess);

        btnHandler = new ProgressButtonHandler(pbBtn, ivBtnAction, ivBtnSuccess);
        setButtonStateDefault();

        // set onclick listener
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnState == ProgressButtonHandler.State.DEFAULT) {
                    // ingredient NER not initiated

                    setButtonStateLoading();
                    lockEditText();
                    Server.performNerOnIngred(LoggedInUser.user().getSessionID(),
                        getIngredStr(), new Server.IngredientNerResult() {
                            @Override
                            public void success(NerredIngred ingredient) {
                                // NER successfully performed on ingred string
                                Log.i(TAG, "Ingredient successfully NERred!");
                                setButtonStateDone();
                                unlockEditText();
                                nerredIngred = ingredient;
                                colorizeResult();
                                editAfterResult = false;
                            }
                            @Override
                            public void error(int errorCode) {
                                // error while NERring ingred
                                Log.i(TAG, "Error NERring ingredient!");
                                makeErrorToast();
                                setButtonStateDefault();
                                editAfterResult = false;
                                unlockEditText();
                            }
                        });

                } else if (btnState == ProgressButtonHandler.State.SUCCESS) {
                    // ingredient NER has finished
                    callback.success(nerredIngred);
                    AddIngredBottomSheet.this.dismiss();
                } else {
                    // still loading, do nothing
                }
            }
        });
    }

    private void setButtonStateDefault() {
        btnHandler.setStateChangeTxt(ProgressButtonHandler.State.DEFAULT, "Extract details", btnAction);
        btnState = ProgressButtonHandler.State.DEFAULT;
    }

    private void setButtonStateLoading() {
        btnHandler.setStateChangeTxt(ProgressButtonHandler.State.LOADING, "Extracting details...", btnAction);
        btnState = ProgressButtonHandler.State.LOADING;
    }

    private void setButtonStateDone() {
        btnHandler.setStateChangeTxt(ProgressButtonHandler.State.SUCCESS, "Done", btnAction);
        btnState = ProgressButtonHandler.State.SUCCESS;
    }

    private void initEtIngred(View v) {
        etIngred = (EditText) v.findViewById(R.id.etIngred);
        etIngred.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!editAfterResult
                        && btnState == ProgressButtonHandler.State.SUCCESS
                        && s.toString().length() < nerredIngred.getOriginal().length()) {
                    setButtonStateDefault();
                    editAfterResult = true;
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private String getIngredStr() {
        return etIngred.getText().toString();
    }

    private void makeErrorToast() {
        ToastMaker.make("Oops, something went wrong!", ToastMaker.Type.ERROR, getActivity());
    }

    private void lockEditText() {
        etIngred.setEnabled(false);
    }

    private void unlockEditText() {
        etIngred.setEnabled(true);
    }

    private void focusEditText() {
        etIngred.requestFocus();
    }

    private void colorizeResult() {
        nerredIngred.colorize(
                etIngred,
                quantityColor,
                unitColor,
                nameColor
        );

        Log.i(TAG, nerredIngred.toString());
    }
}