package com.abdn.cooktoday.utility;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * ProgressButtonHandler
 *
 * Small class handling the state of a
 * progess-enabled button.
 *
 */
public class ProgressButtonHandler {
    public enum State {
        DEFAULT,
        LOADING,
        SUCCESS
    }

    private final ProgressBar pb;
    private final ImageView ivDefault;
    private final ImageView ivSuccess;

    public ProgressButtonHandler(ProgressBar pb, ImageView ivDefault, ImageView ivSuccess) {
        this.pb = pb;
        this.ivDefault = ivDefault;
        this.ivSuccess = ivSuccess;
        setState(State.DEFAULT);
    }

    public void setState(State state) {
        switch (state) {
            case DEFAULT:
                pb.setVisibility(View.INVISIBLE);
                ivSuccess.setVisibility(View.INVISIBLE);
                ivDefault.setVisibility(View.VISIBLE);
                break;
            case LOADING:
                pb.setVisibility(View.VISIBLE);
                ivSuccess.setVisibility(View.INVISIBLE);
                ivDefault.setVisibility(View.INVISIBLE);
                break;
            case SUCCESS:
                pb.setVisibility(View.INVISIBLE);
                ivSuccess.setVisibility(View.VISIBLE);
                ivDefault.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    public void setStateChangeTxt(State state, String newBtnLabel, Button btn) {
        setState(state);
        btn.setText(newBtnLabel);
    }
}
