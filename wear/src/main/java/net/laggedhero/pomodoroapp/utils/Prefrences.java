package net.laggedhero.pomodoroapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by laggedhero on 9/14/15.
 */
public class Prefrences {
    private static final String PREFS_NAME = "net.laggedhero.pomodoroapp.prefs";

    private static final String FIRST_RUN = "firstRun";

    public static boolean isFirstRun(Context context) {
        return getPreferences(context).getBoolean(FIRST_RUN, true);
    }

    public static void markFirstRunDone(Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(FIRST_RUN, false);
        editor.apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
