package net.laggedhero.pomodoroapp.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.laggedhero.pomodoroapp.R;
import net.laggedhero.pomodoroapp.persistence.PomodoroAppContract;

/**
 * Created by laggedhero on 9/21/15.
 */
public class TimerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TASK_ID = "taskId";

    private static final int LOADER_ID = 1;

    private TextView taskName;
    private TextView taskTime;
    private ImageView taskCircle;

    private int taskId;

    private static final long TOTAL_TIME = 1500000; // 25 * 60 * 1000 = 25m

    public static TimerFragment newInstance(int taskId) {
        TimerFragment timerFragment = new TimerFragment();

        Bundle arguments = new Bundle();
        arguments.putInt(TASK_ID, taskId);

        timerFragment.setArguments(arguments);

        return timerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        taskId = savedInstanceState == null ? getArguments().getInt(TASK_ID) : savedInstanceState.getInt(TASK_ID);

        final WatchViewStub stub = (WatchViewStub) view.findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                taskName = (TextView) stub.findViewById(R.id.task_name);
                taskTime = (TextView) stub.findViewById(R.id.task_timer);
                taskCircle = (ImageView) stub.findViewById(R.id.circles);

                setUpViewData();
            }
        });

        return view;
    }

    private void setUpViewData() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    private void setTask(Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            taskName.setText(null);
            return;
        }

        taskTime.setText(milliSecondsToTimer(TOTAL_TIME));

        taskName.setText(
                cursor.getString(cursor.getColumnIndex(PomodoroAppContract.Tasks.COLUMN_TITLE))
        );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return onCreateTaskLoader();
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                setTask(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                setTask(null);
                break;
        }
    }

    public Loader<Cursor> onCreateTaskLoader() {
        String[] projection = {
                PomodoroAppContract.Tasks.COLUMN_TITLE
        };

        String selection = PomodoroAppContract.Tasks.COLUMN_ID + " = ?";

        String[] selectionArgs = {
                String.valueOf(taskId)
        };

        return new CursorLoader(
                getActivity(),
                PomodoroAppContract.Tasks.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
    }

    private String milliSecondsToTimer(long millis) {
        return "";
    }
}
