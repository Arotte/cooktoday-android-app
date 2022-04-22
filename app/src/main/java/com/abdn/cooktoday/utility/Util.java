package com.abdn.cooktoday.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
    public static String getUniqueIdFromDate() {
        String dateOfCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return dateOfCreation.replace("-", "_").replace(" ", "_").replace(":", "_");
    }
}
