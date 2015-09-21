package net.laggedhero.pomodoroapp.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.wearable.view.FragmentGridPagerAdapter;

import net.laggedhero.pomodoroapp.fragments.StartFragment;
import net.laggedhero.pomodoroapp.fragments.TimerFragment;

/**
 * Created by laggedhero on 9/21/15.
 */
public class TimerFragmentGridPagerAdapter extends FragmentGridPagerAdapter {

    private final int taskId;

    private TimerFragment timerFragment;
    private StartFragment startFragment;

    private int columnCount;

    public TimerFragmentGridPagerAdapter(FragmentManager fm, int taskId) {
        super(fm);
        this.taskId = taskId;
        columnCount = 2;
    }

    @Override
    public Fragment getFragment(int row, int column) {
        if (column == 0) {
            if (timerFragment == null) {
                timerFragment = TimerFragment.newInstance(taskId);
            }
            return timerFragment;
        }

        if  (startFragment == null) {
            startFragment = StartFragment.newInstance(taskId);
        }
        return startFragment;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return columnCount;
    }

    public void startTask() {
        timerFragment.startTaskTimer();
        columnCount = 1;
        notifyDataSetChanged();
    }
}
