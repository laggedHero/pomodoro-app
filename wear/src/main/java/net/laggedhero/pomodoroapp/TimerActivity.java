package net.laggedhero.pomodoroapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import net.laggedhero.pomodoroapp.persistence.PomodoroAppContract;

public class TimerActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TASK_ID = "taskId";

    private static final int LOADER_ID = 1;

    private TextView taskName;

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
                taskName = (TextView) stub.findViewById(R.id.task_name);

                setUpViewData();
            }
        });
    }

    private void setUpViewData() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    private void setTask(Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            taskName.setText(null);
            return;
        }

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
                this,
                PomodoroAppContract.Tasks.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
    }
}
