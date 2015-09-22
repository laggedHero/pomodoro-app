package net.laggedhero.pomodoroapp.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by laggedhero on 9/22/15.
 */
public class StringFormatting {
    public static String milliSecondsToTimer(long millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
}
