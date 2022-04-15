package com.abdn.cooktoday.onboarding.survey.steps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.onboarding.survey.rvadapters.AllergiesRVAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SurveyFragment2Allergies extends Fragment implements AllergiesRVAdapter.ItemClickListener {

    AllergiesRVAdapter allergiesRVAdapter;

    List<String> allergies;
    List<Integer> selected;

    public SurveyFragment2Allergies() {
        // required empty public constructor
    }

    public static SurveyFragment2Allergies newInstance() {
        SurveyFragment2Allergies fragment = new SurveyFragment2Allergies();
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
        View layout = inflater.inflate(R.layout.fragment_survey_step2_allergies, container, false);

        initAllergiesRV(layout);
        selected = new ArrayList<>();

        return layout;
    }


    private void initAllergiesRV(View layout) {
        allergies = new ArrayList<>(Arrays.asList(
                "Egg-free",
                "Gluten-free",
                "Seafood-free",
                "Sesame-free",
                "Soy-free",
                "Sulfite-free",
                "Tree nut-free",
                "Peanut-free",
                "Dairy-free",
                "Wheat-free"
        ));

        RecyclerView rvContainer = layout.findViewById(R.id.rvSurveyStep2Allergies);
        rvContainer.setNestedScrollingEnabled(false);
        rvContainer.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        allergiesRVAdapter = new AllergiesRVAdapter(getActivity(), allergies);
        allergiesRVAdapter.setClickListener(this);
        rvContainer.setAdapter(allergiesRVAdapter);
    }

    @Override
    public void onAllergyItemClick(View view, int position) {
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

    public List<String> getSelected() {
        List<String> selectedItems = new ArrayList<>();
        for (int selectedIdx : this.selected)
            selectedItems.add(allergies.get(selectedIdx));
        return selectedItems;
    }
}