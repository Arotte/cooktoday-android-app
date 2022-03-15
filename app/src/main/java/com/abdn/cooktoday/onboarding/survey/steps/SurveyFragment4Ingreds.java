package com.abdn.cooktoday.onboarding.survey.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.onboarding.survey.rvadapters.IngredientsRVAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SurveyFragment4Ingreds extends Fragment implements IngredientsRVAdapter.ItemClickListener {

    IngredientsRVAdapter ingredsRVAdapter;

    List<Integer> selected;

    public SurveyFragment4Ingreds() {
        // required empty public constructor
    }

    public static SurveyFragment4Ingreds newInstance() {
        SurveyFragment4Ingreds fragment = new SurveyFragment4Ingreds();
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
        // inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_survey_step4_ingreds, container, false);

        initAllergiesRV(layout);
        selected = new ArrayList<>();

        return layout;
    }


    private void initAllergiesRV(View layout) {
        List<String> diets = new ArrayList<>(Arrays.asList(
                "Alcohol",
                "Avocado",
                "Bacon",
                "Bananas",
                "Beeg",
                "Brussels Sprouts",
                "Cilantro",
                "Coconut",
                "Eggplant",
                "Fish",
                "Mayonnaise",
                "Mushrooms",
                "Olives",
                "Onions",
                "Pork",
                "Potatoes",
                "Seafood",
                "Shrimp",
                "Sugar",
                "Tomatoes"
        ));

        RecyclerView rvContainer = layout.findViewById(R.id.rvSurveyStep4ExcludedIngredients);
        rvContainer.setNestedScrollingEnabled(false);
        rvContainer.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        ingredsRVAdapter = new IngredientsRVAdapter(getActivity(), diets);
        ingredsRVAdapter.setClickListener(this);
        rvContainer.setAdapter(ingredsRVAdapter);
    }

    @Override
    public void onIngredItemClick(View view, int position) {
        FrameLayout overlay = (FrameLayout) view.findViewById(R.id.flSurveySelectableCircleImgOverlay);

        // change background of item
        if (selected.contains(position)) {
            overlay.setBackgroundColor(
                    getResources().getColor(R.color.imageOverlay));
            selected.remove(selected.indexOf(position));
        } else {
            overlay.setBackgroundColor(
                    getResources().getColor(R.color.imageOverlayGreen));
            selected.add(position);
        }
    }
}