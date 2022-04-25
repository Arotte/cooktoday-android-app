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

public class Util {
    public static String getUniqueIdFromDate() {
        String dateOfCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return dateOfCreation.replace("-", "_").replace(" ", "_").replace(":", "_");
    }

    public static void hideKeyboardIfVisible(Context ctx, EditText et) {
        // hide keyboard if visible
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }

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

    public static void renderHtmlInTextView(String html, TextView tv) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv.setText(Html.fromHtml(html));
        }
    }
}
