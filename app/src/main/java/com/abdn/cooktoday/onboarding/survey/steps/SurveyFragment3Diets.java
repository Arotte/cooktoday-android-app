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
import com.abdn.cooktoday.onboarding.survey.rvadapters.DietsRVAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SurveyFragment3Diets extends Fragment implements DietsRVAdapter.ItemClickListener {

    DietsRVAdapter dietsRVAdapter;

    List<String> diets;
    List<Integer> selected;

    public SurveyFragment3Diets() {
        // required empty public constructor
    }

    public static SurveyFragment3Diets newInstance() {
        SurveyFragment3Diets fragment = new SurveyFragment3Diets();
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
        View layout = inflater.inflate(R.layout.fragment_survey_step3_diets, container, false);

        initAllergiesRV(layout);
        selected = new ArrayList<>();

        return layout;
    }


    private void initAllergiesRV(View layout) {
        diets = new ArrayList<>(Arrays.asList(
                "Vegetarian\n(no meat,\nno eggs)",
                "Vegetarian\n(no meat,\nno dairy)",
                "Vegetarian",
                "Vegan",
                "Low Fodmap",
                "Paleo",
                "Pescetarian",
                "Ketogenic"
        ));

        RecyclerView rvContainer = layout.findViewById(R.id.rvSurveyStep3FollowedDiets);
        rvContainer.setNestedScrollingEnabled(false);
        rvContainer.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        dietsRVAdapter = new DietsRVAdapter(getActivity(), diets);
        dietsRVAdapter.setClickListener(this);
        rvContainer.setAdapter(dietsRVAdapter);
    }

    @Override
    public void onDietItemClick(View view, int position) {
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
            selectedItems.add(diets.get(selectedIdx));
        return selectedItems;
    }
}