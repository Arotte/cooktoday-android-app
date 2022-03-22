package com.abdn.cooktoday.utility;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ProgressButtonHandler {
    public static enum State {
        DEFAULT,
        LOADING,
        SUCCESS
    }

    private ProgressBar pb;
    private ImageView ivDefault;
    private ImageView ivSuccess;

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
}
