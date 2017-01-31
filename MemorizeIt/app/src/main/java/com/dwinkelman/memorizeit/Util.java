package com.dwinkelman.memorizeit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Daniel on 1/20/2017.
 */

public class Util {
    private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
    public static String formatDate(Date date){
        return dateFormat.format(date);
    }
}
