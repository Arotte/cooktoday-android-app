package com.abdn.cooktoday.onboarding.survey.steps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.abdn.cooktoday.R;
import com.google.android.material.card.MaterialCardView;


public class SurveyFragment5Skills extends Fragment {

    public enum CookingSkill {
        BEGINNER("beginner"),
        INTERMEDIATE("intermediate"),
        ADVANCED("advanced"),
        _NONE("_none");

        public final String label;
        private CookingSkill(String label) {
            this.label = label;
        }
        @Override
        public String toString() {
            return this.label;
        }
    }

    CookingSkill selected;

    public SurveyFragment5Skills() {
    }

    public static SurveyFragment5Skills newInstance() {
        SurveyFragment5Skills fragment = new SurveyFragment5Skills();
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

        View layout = inflater.inflate(R.layout.fragment_survey_step5_cooking_skills, container, false);

        selected = CookingSkill._NONE;
        setOnClickListeners(layout);

        return layout;
    }

    private void setOnClickListeners(View layout) {
        // beginner button
        ((MaterialCardView) layout.findViewById(R.id.cvSurveyStep5SkillsBEGINNER)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAll(layout);
                select(layout, R.id.flSurveyStep5SkillsBEGINNEROverlay, R.id.cvSurveyStep5SkillsBEGINNER);
                selected = CookingSkill.BEGINNER;
            }
        });

        // intermediate button
        ((MaterialCardView) layout.findViewById(R.id.cvSurveyStep5SkillsINTERMEDIATE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAll(layout);
                select(layout, R.id.flSurveyStep5SkillsINTERMEDIATEOverlay, R.id.cvSurveyStep5SkillsINTERMEDIATE);
                selected = CookingSkill.INTERMEDIATE;
            }
        });

        // advanced button
        // beginner button
        ((MaterialCardView) layout.findViewById(R.id.cvSurveyStep5SkillsADVANCED)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAll(layout);
                select(layout, R.id.flSurveyStep5SkillsADVANCEDOverlay, R.id.cvSurveyStep5SkillsADVANCED);
                selected = CookingSkill.ADVANCED;
            }
        });
    }

    private void unselectAll(View view) {
        FrameLayout beginner = view.findViewById(R.id.flSurveyStep5SkillsBEGINNEROverlay);
        FrameLayout interm   = view.findViewById(R.id.flSurveyStep5SkillsINTERMEDIATEOverlay);
        FrameLayout advanced = view.findViewById(R.id.flSurveyStep5SkillsADVANCEDOverlay);

        beginner.setBackgroundColor(getResources().getColor(R.color.textSecondary));
        interm.setBackgroundColor(getResources().getColor(R.color.textSecondary));
        advanced.setBackgroundColor(getResources().getColor(R.color.textSecondary));

    }

    private void select(View view, int buttonOverlay, int cvID) {
        ((FrameLayout) view.findViewById(buttonOverlay)).setBackgroundColor(
                getResources().getColor(R.color.imageOverlayGreen));
    }

    public CookingSkill getSelected() {
        return selected;
    }
    public String getSelectedStr() { return selected.toString(); }
}