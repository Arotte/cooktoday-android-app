package com.abdn.cooktoday.utility;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdn.cooktoday.R;

/**
 * ToastMaker
 *
 * This class is used to create custom toast messages.
 */
public class ToastMaker {

    public enum Type {
        ERROR,
        SUCCESS,
        WARNING,
        INFO
    }

    public static void error(String msg, Activity activity) {
        make(msg, Type.ERROR, activity);
    }

    public static void success(String msg, Activity activity) {
        make(msg, Type.SUCCESS, activity);
    }

    public static void info(String msg, Activity activity) {
        make(msg, Type.INFO, activity);
    }

    private static void make(String msg, Type type, Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();

        View layout = null;
        TextView text = null;
        switch (type) {
            case ERROR:
                layout = inflater.inflate(R.layout.toast_cooktoday_error, activity.findViewById(R.id.toastCookTodayError));
                text = layout.findViewById(R.id.tvToastCookTodayError);
                ImageView imageError = layout.findViewById(R.id.ivToastCookTodayError);
                imageError.setImageResource(R.drawable.ic_close_square);
                imageError.setColorFilter(activity.getResources().getColor(R.color.white));
                break;
            case SUCCESS:
                layout = inflater.inflate(R.layout.toast_cooktoday_default, activity.findViewById(R.id.toastCookTodayDefault));
                text = layout.findViewById(R.id.tvToastCookTodayDefault);
                break;
            case INFO:
                layout = inflater.inflate(R.layout.toast_cooktoday_info, activity.findViewById(R.id.toastCookTodayInfo));
                text = layout.findViewById(R.id.tvToastCookTodayInfo);
                ImageView imageInfo = layout.findViewById(R.id.ivToastCookTodayInfo);
                imageInfo.setImageResource(R.drawable.ic_info_circle);
                imageInfo.setColorFilter(activity.getResources().getColor(R.color.white));
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
