package com.aron.cooktoday.onboarding.survey.steps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aron.cooktoday.R;


public class SurveyFragment3 extends Fragment {

    public SurveyFragment3() {
        // required empty public constructor
    }

    public static SurveyFragment3 newInstance() {
        SurveyFragment3 fragment = new SurveyFragment3();
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
        View layout = inflater.inflate(R.layout.fragment_survey_step3, container, false);

        return layout;
    }

}