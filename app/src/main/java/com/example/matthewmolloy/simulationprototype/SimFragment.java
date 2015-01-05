package com.example.matthewmolloy.simulationprototype;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Fragment;
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
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
		Player player = MainActivity.player;
		// create timer
        timer = new CDT(60000,1000);
        timer.view = view;

        // display player info
        TextView mTextView = (TextView) view.findViewById(R.id.idLabel);
        mTextView.setText(player.getId());
        mTextView = (TextView) view.findViewById(R.id.statusLabel);
        mTextView.setText(Double.toString(player.getStatus()));
        mTextView = (TextView) view.findViewById(R.id.resourcesLabel);
        mTextView.setText(Integer.toString(player.getResources()));
		mTextView = (TextView) view.findViewById(R.id.infoLabel);
		mTextView.setText(Integer.toString(player.getInformation()));

        Button submitButton = (Button) view.findViewById(R.id.submitButton);
        submitButton.setVisibility(View.GONE);
		Button startButton = (Button) view.findViewById(R.id.startButton);
		startButton.setVisibility(View.GONE);
		Button retrieveButton = (Button) view.findViewById(R.id.retrieveButton);
		retrieveButton.setVisibility(View.GONE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // return root view
		return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
