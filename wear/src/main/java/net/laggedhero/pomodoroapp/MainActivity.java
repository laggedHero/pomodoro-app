package net.laggedhero.pomodoroapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;

import net.laggedhero.pomodoroapp.adapters.TaskListAdapter;
import net.laggedhero.pomodoroapp.persistence.PomodoroAppContract;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private final static int LOADER_ID = 1;

    private WearableListView taskList;
    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                taskList = (WearableListView) stub.findViewById(R.id.task_list);

                setUpViewData();
            }
        });
    }

    private void setUpViewData() {
        adapter = new TaskListAdapter(this);

        taskList.setAdapter(adapter);

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
    public void onLoadFinished(Loader loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                adapter.swapCursor(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                adapter.swapCursor(null);
                break;
        }
    }

    public Loader<Cursor> onCreateTaskLoader() {
        String[] projection = {
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
