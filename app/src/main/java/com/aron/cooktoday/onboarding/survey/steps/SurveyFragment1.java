package com.aron.cooktoday.onboarding.survey.steps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aron.cooktoday.R;
import com.aron.cooktoday.onboarding.survey.rvadapters.CuisinesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SurveyFragment1 extends Fragment implements CuisinesRecyclerViewAdapter.ItemClickListener {

    CuisinesRecyclerViewAdapter rvAdapterCuisines;
    List<String> cuisineNames;

    public SurveyFragment1() {
    }

    public static SurveyFragment1 newInstance() {
        SurveyFragment1 fragment = new SurveyFragment1();
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
        View layout = inflater.inflate(R.layout.fragment_survey_step1, container, false);

        initCuisinesRecyclerView(layout);
        return layout;
    }

    @Override
    public void onCuisineItemClick(View view, int position) {
        // TODO
    }

    private void initCuisinesRecyclerView(View layout) {
        RecyclerView rvContainer = layout.findViewById(R.id.rvSurveyStep1Cuisines);

        // fill up cuisine names list
        // TODO: get it from server + get cuisine images from server
        cuisineNames = new ArrayList<>();
        cuisineNames = Arrays.asList("AMERICAN", "MEXICAN", "FRENCH", "ITALIAN", "ASIAN", "BBQ", "KID-FRIENDLY", "GREEK", "ENGLISH", "THAI", "GERMAN", "IRISH", "INDIAN", "CUBAN", "SWEDISH", "HUNGARIAN");

        rvContainer.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAdapterCuisines = new CuisinesRecyclerViewAdapter(getActivity(), cuisineNames);
        rvAdapterCuisines.setClickListener(this);
        rvContainer.setAdapter(rvAdapterCuisines);
    }
}