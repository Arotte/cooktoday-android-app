package com.aron.cooktoday.onboarding.survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.aron.cooktoday.MainActivity;
import com.aron.cooktoday.R;
import com.aron.cooktoday.onboarding.OnBoardingActivity;
import com.aron.cooktoday.onboarding.registration.RegisterActivity;
import com.aron.cooktoday.onboarding.survey.steps.SurveyFragment1Cuisines;
import com.aron.cooktoday.onboarding.survey.steps.SurveyFragment2Allergies;
import com.aron.cooktoday.onboarding.survey.steps.SurveyFragment3Diets;
import com.aron.cooktoday.onboarding.survey.steps.SurveyFragment4Ingreds;
import com.aron.cooktoday.onboarding.survey.steps.SurveyFragment5Skills;
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
                            Intent intent = new Intent(SurveySlidePagerActivity.this, MainActivity.class);
                            intent.putExtra("StartedFrom", "OnBoardingSurvey");
                            // clear Activity stack
                            intent.addFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                }

                if (position != LAST_PAGE && btnNext.getText() == "Done") {
                    btnNext.setText("Next");
                    btnNext.setIcon(getResources().getDrawable(R.drawable.ic_arrow___right_2));
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() != NUM_PAGES - 1)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
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
                return new SurveyFragment1Cuisines();
            else if (position == 1)
                return new SurveyFragment2Allergies();
            else if (position == 2)
                return new SurveyFragment3Diets();
            else if (position == 3)
                return new SurveyFragment4Ingreds();
            else if (position == 4) {
                return new SurveyFragment5Skills();
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

}