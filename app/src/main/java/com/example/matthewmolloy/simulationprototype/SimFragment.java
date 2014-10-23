package com.example.matthewmolloy.simulationprototype;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SimFragment extends Fragment {

    public static CDT timer;

    public SimFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        // setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // create timer
        timer = new CDT(15000,1000);
        timer.view = view;

        // display player info
        TextView mTextView = (TextView) view.findViewById(R.id.idLabel);
        mTextView.setText(MainActivity.player.id);
        mTextView = (TextView) view.findViewById(R.id.statusLabel);
        mTextView.setText(Double.toString(MainActivity.player.status));
        mTextView = (TextView) view.findViewById(R.id.resourcesLabel);
        mTextView.setText(Integer.toString(MainActivity.player.resources));

        Button submitButton = (Button) view.findViewById(R.id.submitButton);
        submitButton.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // return root view
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
