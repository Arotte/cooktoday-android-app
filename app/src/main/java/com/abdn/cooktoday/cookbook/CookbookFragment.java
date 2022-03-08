package com.abdn.cooktoday.cookbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.cookbook.bottomsheet.UploadTypeBottomSheet;
import com.abdn.cooktoday.cookbook.rvadapters.CookBookRVAdapter;
import com.abdn.cooktoday.recipedetails.RecipeDetailsActivity;
import com.abdn.cooktoday.search.bottomsheet.SearchFilterBottomSheet;
import com.abdn.cooktoday.upload_recipe.manual.UploadActivity;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.utility.MockServer;

import java.util.List;


public class CookbookFragment extends Fragment
    implements CookBookRVAdapter.ItemClickListener{
    CardView cvUploadNew;
    CookBookRVAdapter cookbookRVAdapter;

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

        cvUploadNew = (CardView) view.findViewById(R.id.cvUploadNewRecipe);
        cvUploadNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show upload type bottom sheet
                UploadTypeBottomSheet bottomSheet = new UploadTypeBottomSheet();
                bottomSheet.show(getActivity().getSupportFragmentManager(), "ModalBottomSheet");

                // startActivity(new Intent(getActivity(), UploadActivity.class));
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

        // cookbook recipes rv
        RecyclerView cookbookRecipes = layout.findViewById(R.id.rvCookBookFragmentCookBookRecipes);
        cookbookRecipes.setNestedScrollingEnabled(false);
        cookbookRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cookbookRVAdapter = new CookBookRVAdapter(getContext(), getCookBookRecipesFromServer());//don't know if work
        cookbookRVAdapter.setClickListener(this);
        cookbookRecipes.setAdapter(cookbookRVAdapter);


    }



/**
 * Get a list of cookbook recipes from server
 * and convert them to List<Recipe>
 *
 * @return a list of "cookbook" recipes from the server
 */
private List<Recipe> getCookBookRecipesFromServer() {
    // TODO: actually get the recipes from the server

    return MockServer.server().getRecipes("cookbook");
}

}