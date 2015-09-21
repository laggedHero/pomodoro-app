package net.laggedhero.pomodoroapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.laggedhero.pomodoroapp.R;
import net.laggedhero.pomodoroapp.TimerActivity;

/**
 * Created by laggedhero on 9/21/15.
 */
public class StartFragment extends Fragment {
    private static final String TASK_ID = "taskId";

    private ImageView imageView;

    public static StartFragment newInstance(int taskId) {
        StartFragment startFragment = new StartFragment();

        Bundle arguments = new Bundle();
        arguments.putInt(TASK_ID, taskId);

        startFragment.setArguments(arguments);

        return startFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        final WatchViewStub stub = (WatchViewStub) view.findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                imageView = (ImageView) stub.findViewById(R.id.start_button);

                setUpView();
            }
        });

        return view;
    }

    private void setUpView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TimerActivity) getActivity()).onTaskStart();
            }
        });
    }
}
