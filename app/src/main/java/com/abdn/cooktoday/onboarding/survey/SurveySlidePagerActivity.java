package com.abdn.cooktoday.onboarding.survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.abdn.cooktoday.MainActivity;
import com.abdn.cooktoday.R;
import com.abdn.cooktoday.onboarding.survey.steps.SurveyFragment1Cuisines;
import com.abdn.cooktoday.onboarding.survey.steps.SurveyFragment2Allergies;
import com.abdn.cooktoday.onboarding.survey.steps.SurveyFragment3Diets;
import com.abdn.cooktoday.onboarding.survey.steps.SurveyFragment4Ingreds;
import com.abdn.cooktoday.onboarding.survey.steps.SurveyFragment5Skills;
import com.google.android.material.button.MaterialButton;

public class SurveySlidePagerActivity extends FragmentActivity {

    private static final int NUM_PAGES = 5;
    private static final int LAST_PAGE = NUM_PAGES - 1;
    private int currentPage = 0;

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    private ProgressBar topProgressBar;
    private MaterialButton btnNext;
    private MaterialButton btnBack;

    private SurveyFragment1Cuisines surveyFragment1Cuisines;
    private SurveyFragment2Allergies surveyFragment2Allergies;
    private SurveyFragment3Diets surveyFragment3Diets;
    private SurveyFragment4Ingreds surveyFragment4Ingreds;
    private SurveyFragment5Skills surveyFragment5Skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        btnNext = findViewById(R.id.btnSurveyNext);
        btnBack = findViewById(R.id.btnBackSurvey);
        // initStepProgressHandler();
        topProgressBar = findViewById(R.id.pbSurveyTopClean);
        topProgressBar.setProgress(0);

        // instantiate a ViewPager2 and a PagerAdapter
        viewPager = findViewById(R.id.surveyViewPager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
            super.onPageSelected(position);

            int p = norm(position);
            System.out.println("PAGE SELECTED " + position + " (" + p + "%)");

            topProgressBar.setProgress(p);
            currentPage = position;

            if (position == 1) {
                // btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
            } else if (position == 0) {
                // btnBack.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);
            }

            if (position == LAST_PAGE) {
                btnNext.setText("Done");
                btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done_simple));

                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (surveyFragment5Skills.getSelected() != SurveyFragment5Skills.CookingSkill._NONE) {
                            Intent intent = new Intent(SurveySlidePagerActivity.this, MainActivity.class);
                            intent.putExtra("StartedFrom", "OnBoardingSurvey");
                            // clear Activity stack
                            intent.addFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            makeSelectCookingSkillToast();
                        }
                    }
                });
            }

            if (position != LAST_PAGE && btnNext.getText() == "Done") {
                btnNext.setText("Next");
                btnNext.setIcon(getResources().getDrawable(R.drawable.ic_arrow___right_2));
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnNextOnClick();
                    }
                });
            }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNextOnClick();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        surveyFragment1Cuisines = new SurveyFragment1Cuisines();
        surveyFragment2Allergies = new SurveyFragment2Allergies();
        surveyFragment3Diets = new SurveyFragment3Diets();
        surveyFragment4Ingreds = new SurveyFragment4Ingreds();
        surveyFragment5Skills = new SurveyFragment5Skills();
    }

    private void btnNextOnClick() {
        if (viewPager.getCurrentItem() != NUM_PAGES - 1)
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            topProgressBar.setProgress(currentPage - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(@NonNull FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0)
                return surveyFragment1Cuisines;
            else if (position == 1)
                return surveyFragment2Allergies;
            else if (position == 2)
                return surveyFragment3Diets;
            else if (position == 3)
                return surveyFragment4Ingreds;
            else if (position == 4) {
                return surveyFragment5Skills;
            } else
                return null;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    private int norm(int position) {
        return (int) Math.round(((double) (position + 1) / (double) NUM_PAGES) * 100);
    }

    private void makeSelectCookingSkillToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_cooktoday_error, (ViewGroup) findViewById(R.id.toastCookTodayError));

        ImageView image = (ImageView) layout.findViewById(R.id.ivToastCookTodayError);
        image.setImageResource(R.drawable.ic_info_circle);
        image.setColorFilter(getResources().getColor(R.color.white));
        TextView text = (TextView) layout.findViewById(R.id.tvToastCookTodayError);
        text.setText("Please select a skill level.");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 35);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}