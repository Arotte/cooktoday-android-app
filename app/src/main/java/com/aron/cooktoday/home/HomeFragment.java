package com.aron.cooktoday.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aron.cooktoday.R;
import com.aron.cooktoday.recipedetails.RecipeDetailsActivity;
import com.aron.cooktoday.home.rvadapters.HotRecipesRVAdapter;
import com.aron.cooktoday.home.rvadapters.RecommedationCirclesRVAdapter;
import com.aron.cooktoday.home.rvadapters.RecommendedRVAdapter;
import com.aron.cooktoday.models.Recipe;
import com.aron.cooktoday.search.SearchFragment;
import com.aron.cooktoday.util.MockServer;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment
        implements RecommendedRVAdapter.ItemClickListener, RecommedationCirclesRVAdapter.ItemClickListener, HotRecipesRVAdapter.ItemClickListener {

    RecommendedRVAdapter recommendedRVAdapter;
    RecommedationCirclesRVAdapter circlesRVAdapter;
    HotRecipesRVAdapter hotRecipesRVAdapter;

    public HomeFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        // recommended recipes rv
        RecyclerView recommendedRecipes = layout.findViewById(R.id.rvHomeFragmentRecommendedRecipes);
        recommendedRecipes.setNestedScrollingEnabled(false);
        recommendedRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recommendedRVAdapter = new RecommendedRVAdapter(getContext(), getRecommendedRecipesFromServer());
        recommendedRVAdapter.setClickListener(this);
        recommendedRecipes.setAdapter(recommendedRVAdapter);

        // hot recipes rv
        RecyclerView rvHotRecipes = layout.findViewById(R.id.rvHomeFragmentHotRecipes);
        rvHotRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hotRecipesRVAdapter = new HotRecipesRVAdapter(getContext(), getHotRecipesFromServer());
        hotRecipesRVAdapter.setClickListener(this);
        rvHotRecipes.setAdapter(hotRecipesRVAdapter);

        // TOP SEARCH BAR
        EditText search = layout.findViewById(R.id.etHomeFragmentSearchBar);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment nextFragment = new SearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    /**
     * Get a list of hot recipes from server
     * and convert them to List<Recipe>
     *
     * @return a list of "hot" recipes from the server
     */
    private List<Recipe> getHotRecipesFromServer() {
        // TODO: actually get the recipes from the server

        return MockServer.server().getRecipes("hot");
    }

    /**
     * Get a list of recommended recipes from server
     * and convert them to List<Recipe>
     *
     * @return a list of "recommended" recipes from the server
     */
    private List<Recipe> getRecommendedRecipesFromServer() {
        // TODO: actually get the recipes from the server

        return MockServer.server().getRecipes("recommend");
    }



}