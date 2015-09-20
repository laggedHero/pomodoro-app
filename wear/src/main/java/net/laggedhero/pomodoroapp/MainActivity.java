package net.laggedhero.pomodoroapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;

import net.laggedhero.pomodoroapp.adapters.TaskListAdapter;
import net.laggedhero.pomodoroapp.persistence.PomodoroAppContract;

import java.util.List;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int SPEECH_REQUEST_CODE = 0;
    private static final int LOADER_ID = 1;

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

        taskList.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                onTaskClicked((int) viewHolder.itemView.getTag());
            }

            @Override
            public void onTopEmptyRegionClick() {
            }
        });

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

    private void onTaskClicked(int taskId) {
        if (taskId == -1) {
            openSpeechActivity();
        } else {
            openTimerActivity(taskId);
        }
    }

    private void openSpeechActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );

        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            saveNewTask(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openTimerActivity(int taskId) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra(TimerActivity.TASK_ID, taskId);
        startActivity(intent);
    }

    private void saveNewTask(String task) {
        ContentValues values = new ContentValues();
        values.put(PomodoroAppContract.Tasks.COLUMN_TITLE, task);

        getContentResolver().insert(PomodoroAppContract.Tasks.CONTENT_URI, values);
    }
}
