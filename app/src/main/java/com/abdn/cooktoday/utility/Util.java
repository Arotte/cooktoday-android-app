package com.abdn.cooktoday.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utility class
 *
 * This class is essentially a collection of
 * static methods that are used throughout the app.
 */
public class Util {

    /**
     * Returns a string that can be used as
     * a unique identifier.
     *
     * This string is based on the current time.
     *
     * @return a unique identifier
     */
    public static String getUniqueIdFromDate() {
        String dateOfCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return dateOfCreation.replace("-", "_").replace(" ", "_").replace(":", "_");
    }

    /**
     * Hides the keyboard
     * @param ctx the context
     * @param et the edit text
     */
    public static void hideKeyboardIfVisible(Context ctx, EditText et) {
        // hide keyboard if visible
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }

    /**
     * This methods sets a click listener on an
     * EdiText. When the user clicks the X
     * icon on the right of the edit text,
     * the text is cleared.
     *
     * @param searchField the edit text
     */
    @SuppressLint("ClickableViewAccessibility")
    public static void setEditTextXIconListener(EditText searchField) {
        searchField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchField.getRight() - searchField.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        searchField.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * This methods renders the html text
     * in a TextView.
     *
     * @param html the html text
     * @param tv the text view
     */
    public static void renderHtmlInTextView(String html, TextView tv) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv.setText(Html.fromHtml(html));
        }
    }
}
