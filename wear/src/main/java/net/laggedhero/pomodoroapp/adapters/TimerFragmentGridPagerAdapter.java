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

    public TimerFragmentGridPagerAdapter(FragmentManager fm, int taskId) {
        super(fm);
        this.taskId = taskId;
    }

    @Override
    public Fragment getFragment(int row, int column) {
        if (column == 0) {
            return TimerFragment.newInstance(taskId);
        }

        return StartFragment.newInstance(taskId);
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 2;
    }
}
