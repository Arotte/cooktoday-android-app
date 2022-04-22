package com.abdn.cooktoday.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abdn.cooktoday.MainActivity;
import com.abdn.cooktoday.R;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.recipedetails.RecipeDetailsActivity;
import com.abdn.cooktoday.home.rvadapters.HotRecipesRVAdapter;
import com.abdn.cooktoday.home.rvadapters.RecommedationCirclesRVAdapter;
import com.abdn.cooktoday.home.rvadapters.RecommendedRVAdapter;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.search.SearchFragment;
import com.abdn.cooktoday.utility.ToastMaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment
        implements RecommendedRVAdapter.ItemClickListener, RecommedationCirclesRVAdapter.ItemClickListener, HotRecipesRVAdapter.ItemClickListener {
    private static final String TAG = "HomeFragment";

    private List<Recipe> recRecipes;
    private List<Recipe> personalizedRecipes;
    private RecommedationCirclesRVAdapter circlesRVAdapter;
    private HotRecipesRVAdapter hotRecipesRVAdapter;
    private RecommendedRVAdapter recommendedRVAdapter;
    private RecyclerView rvRecommendedRecipes;

    public HomeFragment() {
        // required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "TEST - onCreate called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "TEST - onCreateView called");

        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        setup(layout);
        return layout;
    }

    /*
    When user clicks a recommended recipe
     */
    @Override
    public void onRecItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
        intent.putExtra("RecipeObject", recommendedRVAdapter.getItem(position));
        startActivity(intent);
    }

    private void reloadRecommendedRecipe(int pos) {
        recommendedRVAdapter.notifyItemChanged(pos);
    }

    /*
    When user clicks one of the top insta story-like circles
     */
    @Override
    public void onCircleItemClick(View view, int position) {

    }

    /*
    When user clicks a "hot" recommended recipe
     */
    @Override
    public void onHotItemClick(View view, int position) {

    }

    private void setup(View layout) {

        String[] circleRec = new String[]{"VEGAN", "FISH", "DRINKS", "BREAKFAST", "MAIN DISH", "SNACK"};

        // top circle category selection rv
        RecyclerView recyclerView = layout.findViewById(R.id.rvHomeFragmentRecommendationCircles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        circlesRVAdapter = new RecommedationCirclesRVAdapter(getContext(), Arrays.asList(circleRec));
        circlesRVAdapter.setClickListener(this);
        recyclerView.setAdapter(circlesRVAdapter);

        recRecipes = LoggedInUser.user().getRecommendedRecipes();
        rvRecommendedRecipes = layout.findViewById(R.id.rvHomeFragmentRecommendedRecipes);
        rvRecommendedRecipes.setNestedScrollingEnabled(false);
        rvRecommendedRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // this line displays a loading animation of the recommended recipes cards:
        // rvRecommendedRecipes.setAdapter(new LoadingRecommendedRVAdapter(getContext()));
        recommendedRVAdapter = new RecommendedRVAdapter(getContext(), recRecipes);
        recommendedRVAdapter.setClickListener(this);
        rvRecommendedRecipes.setAdapter(recommendedRVAdapter);

        // hot recipes rv
        personalizedRecipes = LoggedInUser.user().getPersonalizedRecipes();
        RecyclerView rvHotRecipes = layout.findViewById(R.id.rvHomeFragmentHotRecipes);
        rvHotRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hotRecipesRVAdapter = new HotRecipesRVAdapter(getContext(), personalizedRecipes);
        hotRecipesRVAdapter.setClickListener(this);
        rvHotRecipes.setAdapter(hotRecipesRVAdapter);

        // TOP SEARCH BAR
        EditText search = layout.findViewById(R.id.etHomeFragmentSearchBar);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setSelected(R.id.bottomNavbarSearch);
                SearchFragment nextFragment = new SearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, nextFragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            }
        });
        
        // if no recipes in either recommended or hot recipes, hide title textviews
        if (recRecipes.size() == 0) {
            layout.findViewById(R.id.tvHomeRecommended).setVisibility(View.GONE);
        } else if (personalizedRecipes.size() == 0) {
            layout.findViewById(R.id.tvHomeForYou).setVisibility(View.GONE);
        }
    }
}