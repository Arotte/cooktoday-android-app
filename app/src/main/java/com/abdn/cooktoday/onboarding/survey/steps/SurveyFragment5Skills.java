package com.abdn.cooktoday.onboarding.survey.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.abdn.cooktoday.R;


public class SurveyFragment5Skills extends Fragment {

    public enum CookingSkill {
        BEGINNER("beginner"),
        INTERMEDIATE("intermediate"),
        ADVANCED("advanced"),
        _NONE("_none");

        public final String label;
        CookingSkill(String label) {
            this.label = label;
        }
        @Override
        public String toString() {
            return this.label;
        }
    }

    CookingSkill selected;

    Button btnBeginner;
    Button btnIntermediate;
    Button btnAdvanced;

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

        btnBeginner = layout.findViewById(R.id.btnSurveyBeginner);
        btnIntermediate = layout.findViewById(R.id.btnSurveyIntermediate);
        btnAdvanced = layout.findViewById(R.id.btnSurveyAdvanced);
        setOnClickListeners(layout);

        return layout;
    }

    private void setOnClickListeners(View layout) {
        // beginner button
        btnBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAll();
                selected = CookingSkill.BEGINNER;
                select();
            }
        });

        // intermediate button
        btnIntermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAll();
                selected = CookingSkill.INTERMEDIATE;
                select();
            }
        });

        // advanced button
        // beginner button
        btnAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAll();
                selected = CookingSkill.ADVANCED;
                select();
            }
        });
    }

    private void unselectAll() {
        btnBeginner.setBackgroundColor(getResources().getColor(R.color.white));
        btnIntermediate.setBackgroundColor(getResources().getColor(R.color.white));
        btnAdvanced.setBackgroundColor(getResources().getColor(R.color.white));

        btnBeginner.setTextColor(getResources().getColor(R.color.textSecondary));
        btnIntermediate.setTextColor(getResources().getColor(R.color.textSecondary));
        btnAdvanced.setTextColor(getResources().getColor(R.color.textSecondary));
    }

    private void select() {
        Button button;
        switch (selected) {
            case BEGINNER:
                button = btnBeginner;
                break;
            case INTERMEDIATE:
                button = btnIntermediate;
                break;
            case ADVANCED:
                button = btnAdvanced;
                break;
            default:
                button = null;
                break;
        }

        if (button != null) {
            button.setBackgroundColor(getResources().getColor(R.color.imageOverlayGreen));
            button.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public CookingSkill getSelected() {
        return selected;
    }
    public String getSelectedStr() { return selected.toString(); }
}