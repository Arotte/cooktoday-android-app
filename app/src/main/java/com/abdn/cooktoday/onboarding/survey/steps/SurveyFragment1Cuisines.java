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
import com.abdn.cooktoday.onboarding.survey.rvadapters.CuisinesRVAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SurveyFragment1Cuisines extends Fragment implements CuisinesRVAdapter.ItemClickListener {

    CuisinesRVAdapter rvAdapterCuisines;

    List<String> cuisineNames;
    List<Integer> selected;

    public SurveyFragment1Cuisines() {
    }

    public static SurveyFragment1Cuisines newInstance() {
        SurveyFragment1Cuisines fragment = new SurveyFragment1Cuisines();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_survey_step1_cuisines, container, false);

        initCuisinesRecyclerView(layout);
        selected = new ArrayList<>();

        return layout;
    }

    @Override
    public void onCuisineItemClick(View view, int position) {

        FrameLayout overlay = view.findViewById(R.id.flSurveySelectableCircleImgOverlay);
        // change background of item
        if (selected.contains(position)) {
            overlay.setBackgroundColor(
                    getResources().getColor(R.color.imageOverlay));
            selected.remove((Integer) position);
        } else {
            overlay.setBackgroundColor(
                    getResources().getColor(R.color.imageOverlayGreen));
            selected.add(position);
        }
    }

    private void initCuisinesRecyclerView(View layout) {
        RecyclerView rvContainer = layout.findViewById(R.id.rvSurveyStep1Cuisines);

        // fill up cuisine names list
        // TODO: get it from server + get cuisine images from server
        cuisineNames = new ArrayList<>();
        cuisineNames = Arrays.asList("AMERICAN", "MEXICAN", "FRENCH", "ITALIAN", "ASIAN", "BBQ", "KID-\nFRIENDLY", "GREEK", "ENGLISH", "THAI", "GERMAN", "IRISH", "INDIAN", "CUBAN", "SWEDISH", "HUNGARIAN");

        rvContainer.setNestedScrollingEnabled(false);
        rvContainer.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAdapterCuisines = new CuisinesRVAdapter(getActivity(), cuisineNames);
        rvAdapterCuisines.setClickListener(this);
        rvContainer.setAdapter(rvAdapterCuisines);
    }

    public List<String> getSelected() {
        List<String> selectedItems = new ArrayList<>();
        for (int selectedIdx : this.selected)
            selectedItems.add(cuisineNames.get(selectedIdx));
        return selectedItems;
    }
}