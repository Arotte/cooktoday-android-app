package com.abdn.cooktoday.cookbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.cookbook.bottomsheet.UploadTypeBottomSheet;
import com.abdn.cooktoday.cookbook.rvadapters.CookBookRVAdapter;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.recipedetails.RecipeDetailsActivity;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;


public class CookbookFragment extends Fragment
    implements CookBookRVAdapter.ItemClickListener {
    private static final String TAG = "CookbookFragment";

    private ExtendedFloatingActionButton btnNewRecipe;
    private CookBookRVAdapter cookbookRVAdapter;
    private UploadTypeBottomSheet bottomSheet;

    private int nSavedRecipesDisplayed;

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
        // Log.i(TAG, "TEST - onCreate called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "TEST - onCreateView called");

        View view = inflater.inflate(R.layout.fragment_cookbook, container, false);

        bottomSheet = new UploadTypeBottomSheet();
        if (bottomSheet.isVisible())
            bottomSheet.dismiss();

        btnNewRecipe = (ExtendedFloatingActionButton) view.findViewById(R.id.btnCookbookNewRecipe);
        btnNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show upload type bottom sheet
                bottomSheet.show(getActivity().getSupportFragmentManager(), "ModalBottomSheet");
            }
        });

        setup(view);
        return view;
    }

    /*
    When user clicks a cookbook recipe
     */
    @Override
    public void onRecItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
        intent.putExtra("RecipeObject", cookbookRVAdapter.getItem(position));
        startActivity(intent);
    }

    private void setup(View layout) {
        List<Recipe> savedRecipes = LoggedInUser.user().getSavedRecipes();
        this.nSavedRecipesDisplayed = LoggedInUser.user().nSavedRecipes();

        // cookbook recipes rv
        RecyclerView cookbookRecipes = layout.findViewById(R.id.rvCookBookFragmentCookBookRecipes);
        cookbookRecipes.setNestedScrollingEnabled(false);
        cookbookRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cookbookRVAdapter = new CookBookRVAdapter(getContext(), savedRecipes);
        cookbookRVAdapter.setClickListener(this);
        cookbookRecipes.setAdapter(cookbookRVAdapter);
    }


}