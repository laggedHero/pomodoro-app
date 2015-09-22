package net.laggedhero.pomodoroapp.providers;

import net.laggedhero.pomodoroapp.R;

/**
 * Created by laggedhero on 9/22/15.
 */
public class CircleDrawableProvider {
    public static int getCircleResIdByPosition(int pos) {
        switch (pos) {
            case 1:
                return R.drawable.circles_1;
            case 2:
                return R.drawable.circles_2;
            case 3:
                return R.drawable.circles_3;
            case 4:
                return R.drawable.circles_4;
            case 5:
                return R.drawable.circles_5;
            case 6:
                return R.drawable.circles_6;
            case 7:
                return R.drawable.circles_7;
            case 8:
                return R.drawable.circles_8;
            case 9:
                return R.drawable.circles_9;
            case 10:
                return R.drawable.circles_10;
            case 11:
                return R.drawable.circles_11;
            case 12:
                return R.drawable.circles_12;
            default:
                return R.drawable.circles_0;
        }
    }
}
