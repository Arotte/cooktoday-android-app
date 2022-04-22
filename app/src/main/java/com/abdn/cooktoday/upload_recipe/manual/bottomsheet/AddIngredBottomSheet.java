package com.abdn.cooktoday.upload_recipe.manual.bottomsheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredSearchResultItemJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchResultItemJSON;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.NerredIngred;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.recipedetails.RecipeDetailsActivity;
import com.abdn.cooktoday.search.adapters.SearchResultArrayAdapter;
import com.abdn.cooktoday.upload_recipe.manual.rvadapters.IngredSearchResultArrayAdapter;
import com.abdn.cooktoday.utility.ProgressButtonHandler;
import com.abdn.cooktoday.utility.ToastMaker;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

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

    private OnIngredientAddedCallback callback;
    private int quantityColor;
    private int unitColor;
    private int nameColor;

    private ListView searchList;
    private IngredSearchResultArrayAdapter searchResultAdapter;
    private IngredientJson selectedIngredient;
    private int prevSearchFieldLen;
    private boolean resultClicked;

    public AddIngredBottomSheet(OnIngredientAddedCallback cb,
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
        searchList = v.findViewById(R.id.lvIngredSearchResults);
        resultClicked = false;

        prevSearchFieldLen = 0;
        etIngred.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                int len = s.length();
                if (len >= 2) // at least 2 characters are present
                    if (!resultClicked)
                        searchIngred(s.toString());
                else { // init result list
                    updateSearchResults(new ArrayList<IngredSearchResultItemJson>());
                }
                prevSearchFieldLen = len;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

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
                        getIngredStr(), new ServerCallbacks.IngredientNerResult() {
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

    private void searchIngred(String query) {
        Server.searchIngredients(LoggedInUser.user().getSessionID(), query, new ServerCallbacks.IngredSearchCallback() {
            @Override
            public void success(List<IngredSearchResultItemJson> ingredients) {
                // search successfully performed
                updateSearchResults(ingredients);
            }
            @Override
            public void error(int errorCode) {
                // error while searching
                Log.i(TAG, "Error while searching!");
                // makeErrorToast();
            }
        });
    }

    private void updateSearchResults(List<IngredSearchResultItemJson> newItems) {
        // select top 4 items
        List<IngredSearchResultItemJson> topItems = new ArrayList<>();
        for (int i = 0; i < Math.min(newItems.size(), 3); i++) {
            topItems.add(newItems.get(i));
        }

        // update search results
        searchResultAdapter = new IngredSearchResultArrayAdapter(getActivity(), topItems);
        searchList.setAdapter(searchResultAdapter);
        resultClicked = false;

        // set onclick listeners
        if (newItems.size() > 0) {
            searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // get ingredient details from server
                    Server.getIngredientById(LoggedInUser.user().getSessionID(), searchResultAdapter.getItem(i).getId(), new ServerCallbacks.GetIngredientCallback() {
                        @Override
                        public void success(IngredientJson ingredientJson) {
                            resultClicked = true;
                            etIngred.setText(ingredientJson.getName());
                            selectedIngredient = ingredientJson;
                            updateSearchResults(new ArrayList<IngredSearchResultItemJson>());
                        }
                        @Override
                        public void error(int errorCode) {
                            Log.i(TAG, "Could not get recipe '" + errorCode + "'.");
                        }
                    });
                }
            });
        }
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