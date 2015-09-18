package net.laggedhero.pomodoroapp.holders;

import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import net.laggedhero.pomodoroapp.R;

/**
 * Created by laggedhero on 9/18/15.
 */
public class TaskListItemHolder extends WearableListView.ViewHolder {
    public TextView textView;

    public TaskListItemHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.name);
    }
}
