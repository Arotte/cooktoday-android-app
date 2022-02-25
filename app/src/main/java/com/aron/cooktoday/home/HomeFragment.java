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
import com.aron.cooktoday.RecipeDetailsActivity;
import com.aron.cooktoday.home.rvadapters.RecommedationCirclesRVAdapter;
import com.aron.cooktoday.home.rvadapters.RecommendedRVAdapter;
import com.aron.cooktoday.search.SearchFragment;

import java.util.Arrays;

public class HomeFragment extends Fragment implements RecommendedRVAdapter.ItemClickListener, RecommedationCirclesRVAdapter.ItemClickListener {

    RecommendedRVAdapter recommendedRVAdapter;
    RecommedationCirclesRVAdapter circlesRVAdapter;

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

    @Override
    public void onRecItemClick(View view, int position) {
        startActivity(new Intent(getContext(), RecipeDetailsActivity.class));
    }

    @Override
    public void onCircleItemClick(View view, int position) {

    }

    private void setup(View layout) {

        // recommended stuff arrays -- TODO: get them from server
        String[] circleRec = new String[]{"VEGAN", "FISH", "DRINKS", "BREAKFAST", "MAIN DISH", "SNACK"};
        String[] recommendedRecipeNames = new String[]{"Delicious Pizza", "Mustard Sushi", "Funny Brownie", "LS... Dish"};

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
        recommendedRVAdapter = new RecommendedRVAdapter(getContext(), Arrays.asList(recommendedRecipeNames));
        recommendedRVAdapter.setClickListener(this);
        recommendedRecipes.setAdapter(recommendedRVAdapter);

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
}