package com.aron.cooktoday.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aron.cooktoday.R;
import com.aron.cooktoday.RecipeDetailsActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment
        implements
            RecommendedRecyclerViewAdapter.ItemClickListener,
            FavouritesRecyclerViewAdapter.ItemClickListener
    {

    RecommendedRecyclerViewAdapter   recommendedRecyclerViewAdapter;
    FavouritesRecyclerViewAdapter    favouritesRecyclerViewAdapter;

    public HomeFragment() {
        // Required empty public constructor
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

        // Top search icon
//        CardView search = layout.findViewById(R.id.homeScreenTopSearchIcon);
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), SearchActivity.class));
//                getActivity().overridePendingTransition(0, 0);
//            }
//        });

        // Inflate the layout for this fragment
        return layout;
    }

    @Override
    public void onRecItemClick(View view, int position) {
        // Toast.makeText(getContext(), "You clicked " + recommendedRecyclerViewAdapter.getItem(position) + " at " + position, Toast.LENGTH_SHORT).show();

        // image transition animation
//        ImageView recipeImg = view.findViewById(R.id.recipeImage);
//        Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
//        // create the transition animation - the images in the layouts
//        // of both activities are defined with android:transitionName="robot"
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), recipeImg, "fade");
//        // start the new activity
//        startActivity(intent, options.toBundle());

        startActivity(new Intent(getContext(), RecipeDetailsActivity.class));
    }

    @Override
    public void onFavItemClick(View view, int position) {
        // Toast.makeText(getContext(), "You clicked " + favouritesRecyclerViewAdapter.getItem(position) + " at " + position, Toast.LENGTH_SHORT).show();
    }

    private void setup(View layout) {
        // data to populate the recommendations RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Black");
        animalNames.add("Orange");
        animalNames.add("Jack Blue");
        animalNames.add("Cracker");
        animalNames.add("Black");
        animalNames.add("Orange");
        animalNames.add("Blue");
        animalNames.add("Cracker");
        // favs
        ArrayList<String> fav = new ArrayList<>();
        fav.add("Black");
        fav.add("Orange");
        fav.add("Blue");
        fav.add("Cracker");
        fav.add("Black");
        fav.add("Orange");
        fav.add("Blue");
        fav.add("Cracker");

        // set up the RecyclerView
        RecyclerView recyclerView = layout.findViewById(R.id.rvRecipeFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendedRecyclerViewAdapter = new RecommendedRecyclerViewAdapter(getContext(), animalNames);
        recommendedRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recommendedRecyclerViewAdapter);
        // favs
//        RecyclerView favRecyclerView = layout.findViewById(R.id.rvFavRecipeFeed);
//        favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        favouritesRecyclerViewAdapter = new FavouritesRecyclerViewAdapter(getContext(), fav);
//        favouritesRecyclerViewAdapter.setClickListener(this);
//        favRecyclerView.setAdapter(favouritesRecyclerViewAdapter);

        // TOP SEARCH BAR
//        EditText search = layout.findViewById(R.id.etHomeSearhBar);
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // shared element edittext transition:
////                Intent intent = new Intent(getContext(), SearchActivity.class);
////                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), search, "searchFade");
////                startActivity(intent, options.toBundle());
//                startActivity(new Intent(getActivity(), SearchActivity.class));
//                getActivity().overridePendingTransition(0, 0);
//            }
//        });
    }
}