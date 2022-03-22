package com.abdn.cooktoday.utility;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdn.cooktoday.R;

public class ToastMaker {

    public static enum Type {
        ERROR,
        SUCCESS,
        WARNING,
        INFO
    }

    public static void make(String msg, Type type, Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();

        View layout = null;
        TextView text = null;
        switch (type) {
            case ERROR:
                layout = inflater.inflate(R.layout.toast_cooktoday_error, (ViewGroup) activity.findViewById(R.id.toastCookTodayError));
                text = (TextView) layout.findViewById(R.id.tvToastCookTodayError);
                ImageView image = (ImageView) layout.findViewById(R.id.ivToastCookTodayError);
                image.setImageResource(R.drawable.ic_info_circle);
                image.setColorFilter(activity.getResources().getColor(R.color.white));
                break;
            case SUCCESS:
                layout = inflater.inflate(R.layout.toast_cooktoday_default, (ViewGroup) activity.findViewById(R.id.toastCookTodayDefault));
                text = (TextView) layout.findViewById(R.id.tvToastCookTodayDefault);
                break;
            default:
                return;
        }
        text.setText(msg);

        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 35);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
