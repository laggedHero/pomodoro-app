package net.laggedhero.pomodoroapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import net.laggedhero.pomodoroapp.adapters.TimerFragmentGridPagerAdapter;
import net.laggedhero.pomodoroapp.persistence.PomodoroAppContract;

public class TimerActivity extends Activity {
    public static final String TASK_ID = "taskId";

    private GridViewPager gridViewPager;

    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(TASK_ID)) {
            // finish this
            return;
        }

        taskId = extras.getInt(TASK_ID, -1);

        if (taskId == -1) {
            // finish this
            return;
        }

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                gridViewPager = (GridViewPager) stub.findViewById(R.id.pager);

                setUpViewData();
            }
        });
    }

    private void setUpViewData() {
        gridViewPager.setAdapter(
                new TimerFragmentGridPagerAdapter(getFragmentManager(), taskId)
        );
    }
}
