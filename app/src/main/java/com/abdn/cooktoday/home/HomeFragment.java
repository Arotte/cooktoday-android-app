package com.abdn.cooktoday.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abdn.cooktoday.MainActivity;
import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.home.rvadapters.LoadingRecommendedRVAdapter;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.recipedetails.RecipeDetailsActivity;
import com.abdn.cooktoday.home.rvadapters.HotRecipesRVAdapter;
import com.abdn.cooktoday.home.rvadapters.RecommedationCirclesRVAdapter;
import com.abdn.cooktoday.home.rvadapters.RecommendedRVAdapter;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.search.SearchFragment;
import com.abdn.cooktoday.utility.MockServer;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment
        implements RecommendedRVAdapter.ItemClickListener, RecommedationCirclesRVAdapter.ItemClickListener, HotRecipesRVAdapter.ItemClickListener {

    private RecommedationCirclesRVAdapter circlesRVAdapter;
    private HotRecipesRVAdapter hotRecipesRVAdapter;
    private RecommendedRVAdapter recommendedRVAdapter;
    private RecyclerView rvRecommendedRecipes;

    private Context ctx;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_home, container, false);

        setup(layout);
        ctx = getContext();

        // get recommended recipes from server
        Server.getRecommendedRecipes(LoggedInUser.user().getSessionID(), new Server.GetRecommendedRecipesResult() {
            @Override
            public void success(List<Recipe> recommendedRecipes) {
                displayRecommendedRecipes(recommendedRecipes);
            }

            @Override
            public void error(int errorCode) {
            }
        });

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

        rvRecommendedRecipes = layout.findViewById(R.id.rvHomeFragmentRecommendedRecipes);
        rvRecommendedRecipes.setNestedScrollingEnabled(false);
        rvRecommendedRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvRecommendedRecipes.setAdapter(new LoadingRecommendedRVAdapter(getContext()));

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
                ((MainActivity) getActivity()).setSelected(R.id.bottomNavbarSearch);
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


    private void displayRecommendedRecipes(List<Recipe> recipes) {
        // recommended recipes rv
        recommendedRVAdapter = new RecommendedRVAdapter(ctx, recipes);
        recommendedRVAdapter.setClickListener(this);
        rvRecommendedRecipes.setAdapter(recommendedRVAdapter);
    }
}