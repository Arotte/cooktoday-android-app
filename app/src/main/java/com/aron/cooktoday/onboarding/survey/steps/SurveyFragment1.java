package com.aron.cooktoday.onboarding.survey.steps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aron.cooktoday.R;

public class SurveyFragment1 extends Fragment {

    public SurveyFragment1() {
        // required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_survey_step1, container, false);

        return layout;
    }

}