package com.aron.cooktoday.onboarding.survey;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.aron.cooktoday.R;
import com.aron.cooktoday.onboarding.survey.steps.SurveyFragment1;
import com.aron.cooktoday.onboarding.survey.steps.SurveyFragment2;
import com.aron.cooktoday.onboarding.survey.steps.SurveyFragment3;
import com.aron.cooktoday.onboarding.survey.top_progressbar.TopStepProgressHandler;

import java.util.ArrayList;
import java.util.List;

public class SurveySlidePagerActivity extends FragmentActivity {

    private static final int NUM_PAGES = 3;
    private int currentPage = 0;

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    // private TopStepProgressHandler topProgressBar; // for icon pb
    private ProgressBar topProgressBar;
    private Button btnNext;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        btnNext = findViewById(R.id.btnNextSurvey);
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

    // for icon-based top progress bar
//    private void initStepProgressHandler() {
//        List<Integer>    iconIDs   = new ArrayList<>();
//        ConstraintLayout container = findViewById(R.id.topStepProgressContainer);
//        int              pbID      = R.id.pbStepProgress;
//
//        iconIDs.add(R.id.ivSurveyStep1);
//        iconIDs.add(R.id.ivSurveyStep2);
//        iconIDs.add(R.id.ivSurveyStep3);
//        // iconIDs.add(R.id.ivSurveyStep4);
//        // iconIDs.add(R.id.ivSurveyStep5);
//
//        this.topProgressBar = new TopStepProgressHandler(
//            NUM_PAGES,
//            iconIDs,
//            container,
//            pbID
//        );
//    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(@NonNull FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0)
                return new SurveyFragment1();
            else if (position == 1)
                return new SurveyFragment2();
            else if (position == 2)
                return new SurveyFragment3();
            else
                return null;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    // will be useful in future, todo: arrange it somewhere
    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                1f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(500);
        v.startAnimation(anim);
    }

    private int norm(int position) {
        return (int) Math.round(((double) (position + 1) / (double) NUM_PAGES) * 100);
    }

}