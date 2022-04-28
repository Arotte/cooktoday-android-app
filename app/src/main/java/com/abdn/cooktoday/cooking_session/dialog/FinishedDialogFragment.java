package com.abdn.cooktoday.cooking_session.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.DialogFragment;

import com.abdn.cooktoday.R;

/**
 * FinishedDialogFragment
 *
 * Simple popup dialog to show the user that the cooking session has finished.
 *
 * @author Team Alpha, University of Aberdeen
 */
public class FinishedDialogFragment extends DialogFragment {
    public static final String TAG = "IngredientsDialog";

    private final FinishedCookingCallback callback;

    public FinishedDialogFragment(FinishedCookingCallback callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cooking_sess_dialog_done_panel, container, false);
        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        // finished button
        view.findViewById(R.id.btnFinishedCooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.finished();
                dismiss();
            }
        }); // end finished button

        // not yet finished button
        view.findViewById(R.id.btnNotFinishedCooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.notFinished();
                dismiss();
            }
        }); // end not yet finished button

        return view;
    }
}
