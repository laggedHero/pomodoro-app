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

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                setUpViewData();
            }
        });
    }

    private void setUpViewData() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
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
                // data into view
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                // data clean up
                break;
        }
    }

    public Loader<Cursor> onCreateTaskLoader() {
        String[] projection = {
                PomodoroAppContract.Tasks.COLUMN_ID,
                PomodoroAppContract.Tasks.COLUMN_TITLE
        };

        return new CursorLoader(
                this,
                PomodoroAppContract.Tasks.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }
}
