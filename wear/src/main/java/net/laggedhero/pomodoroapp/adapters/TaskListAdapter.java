package net.laggedhero.pomodoroapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.laggedhero.pomodoroapp.R;
import net.laggedhero.pomodoroapp.holders.TaskListItemHolder;
import net.laggedhero.pomodoroapp.persistence.PomodoroAppContract;

/**
 * Created by laggedhero on 9/15/15.
 */
public class TaskListAdapter extends WearableListView.Adapter {

    private Cursor cursor;
    private final LayoutInflater inflater;

    public TaskListAdapter(Context context) {
        cursor = null;
        inflater = LayoutInflater.from(context);
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskListItemHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        TaskListItemHolder taskListItemHolder = (TaskListItemHolder) holder;

        if (position == 0) {
            taskListItemHolder.textView.setText(R.string.add_new);
            taskListItemHolder.itemView.setTag(position - 1);
        } else if (cursor.moveToPosition(position - 1)) {
            taskListItemHolder.textView.setText(
                    cursor.getString(cursor.getColumnIndex(PomodoroAppContract.Tasks.COLUMN_TITLE))
            );
            taskListItemHolder.itemView.setTag(position - 1);
        }
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 1;
        }

        return cursor.getCount() + 1;
    }
}
