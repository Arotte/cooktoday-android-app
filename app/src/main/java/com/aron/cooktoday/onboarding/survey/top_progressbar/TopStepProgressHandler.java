package com.aron.cooktoday.onboarding.survey.top_progressbar;

import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.aron.cooktoday.R;

import java.util.ArrayList;
import java.util.List;

public class TopStepProgressHandler {

    private static final int maxProgress = 100;

    private static final int icOn  = R.drawable.ic_check_2;
    private static final int icOff = R.drawable.ic_check_inactive_2;

    private ConstraintLayout container;
    private ProgressBar      pb;
    private List<ImageView>  icons;

    private List<Integer>    pbMilestones;
    private int              steps;
    private int              current;
    private int              progressAtom;


    public TopStepProgressHandler(
            int              steps,
            List<Integer>    iconIDs,
            ConstraintLayout container,
            int              pbID
    ) {
        this.steps     = steps;
        this.container = container;
        this.pb        = container.findViewById(pbID);
        this.current   = 0;

        // get icons
        this.icons = new ArrayList<>();
        for (int iconID : iconIDs) {
            this.icons.add(container.findViewById(iconID));
        }

        // set progressbar milestones
        this.pbMilestones = new ArrayList<>();
        this.progressAtom = Math.round(maxProgress / (this.steps - 1));
        for (int i = 0; i < this.steps; i++) {
            pbMilestones.add(i * this.progressAtom);
        }
        printPbMilestones(); // debug

        // initialize views
        reset();
    }


    /**
     * Set progress
     * @param position step number
     */
    public void set(int position) {
        int from = current * progressAtom;
        int to = position * progressAtom;
        int animDurationMs = 300;

        // start progressbar animation
        ProgressBarAnimation anim = new ProgressBarAnimation(pb, from, to);
        anim.setDuration(animDurationMs);
        pb.startAnimation(anim);

        // change icons
        if (current < position) { // next
            icons.get(current).setImageResource(icOn);
        } else if (current > position) { // back
            icons.get(current).setImageResource(icOff);
        }


        current = position;
    }

    /**
     * Increment progress by 1 step
     */
    public void next() {
        if (current < steps)
            set(current + 1);
    }

    /**
     * Decrement progress by 1 step
     */
    public void previous() {
        if (current != 0)
            set(current - 1);
    }

    /**
     * Set all check images inactive
     * and reset progressbar to 0
     */
    private void reset() {
        for (int i = 0; i < this.icons.size(); i++) {
            this.icons.get(i).setImageResource(icOff);
        }
        pb.setProgress(0);
    }

    private void printPbMilestones() {
        System.out.print("PROGRESSBAR MILESTONES: ");
        for (int ms : this.pbMilestones) {
            System.out.print(ms + " ");
        }
        System.out.println("");
    }
}
