package net.laggedhero.pomodoroapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by laggedhero on 9/15/15.
 */
public class TaskListAdapter extends WearableListView.Adapter {

    private Cursor cursor;
    private final Context context;
    private final LayoutInflater inflater;

    public TaskListAdapter(Context context) {
        cursor = null;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }
}
