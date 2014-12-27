package com.example.matthewmolloy.simulationprototype;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DecimalFormat;


public class MainActivity extends ActionBarActivity implements HistoryFragment.OnFragmentInteractionListener {

		public void onFragmentInteraction(String position) {
		}

    public static Player player = new Player();
	static Socket sock = null;
	static BufferedWriter dataOut = null;
	static BufferedReader dataIn = null;
	static final int portNum = 9586;
	static String connectIP = "192.168.1.2";
	static int investedRes, initialRes, newRes;
	static double initialStatus;
	static int sharedInfo, initialInfo, newInfo;
	static int rowIndex;
	static HistoryFragment historyFragment = new HistoryFragment();
	static SimFragment simFragment = new SimFragment();
	static ListContent historyList;
	ActionBar.Tab tab1, tab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Context c = getApplicationContext();
		historyList = new ListContent(c);

		tab1 = actionBar.newTab().setText("Simulation");
		tab2 = actionBar.newTab().setText("History");

		tab1.setTabListener(new MyTabListener(simFragment));
		tab2.setTabListener(new MyTabListener(historyFragment));

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, simFragment)
					.add(R.id.container, historyFragment)
					.hide(historyFragment)
                    .commit();
        }

		// display dialog for IP
		IPDialogFragment dialog = new IPDialogFragment();
		dialog.show(getFragmentManager(), "dialog");

    }

    public void start(View view) {
        SimFragment.timer.start();
        Button startButton = (Button) findViewById(R.id.startButton);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        startButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
    }

	public void connect(View view) {
		try {
			sock = new Socket();

			// set timeout
			sock.connect(new InetSocketAddress(connectIP, portNum), 10000);
			dataOut = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			dataIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));

			String input = dataIn.readLine();
			rowIndex = Integer.parseInt(input);

			Button startButton = (Button) findViewById(R.id.startButton);
			Button connectButton = (Button) findViewById(R.id.connectButton);
			startButton.setVisibility(View.VISIBLE);
			connectButton.setVisibility(View.GONE);
		} catch (IOException e) {
			e.printStackTrace();
			String toastText = ("Connect failed, try again");
			Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
		}
	}

    public void submit(View view) {
        // set up input data
        EditText mShareEdit = (EditText)findViewById(R.id.shareInput);
        EditText mInvestEdit = (EditText)findViewById(R.id.investInput);
		Button submitButton = (Button) findViewById(R.id.submitButton);
		Button retrieveButton = (Button) findViewById(R.id.retrieveButton);

		int statusDamage = 0;

		// TextView mTimerView = (TextView) view.findViewById(R.id.timer);

		// grab seconds left
		/*
		String delims = "[ ]+";
		String[] timerSplit = new String[5];
		String timerText = mTimerView.getText().toString();
		timerSplit = timerText.split(delims);
		String secondsLeft = timerSplit[2];
		*/

        // grab values
        String shareVal;
        shareVal = mShareEdit.getText().toString();
        String investVal;
        investVal = mInvestEdit.getText().toString();

        if( !(shareVal.equals("")) && !(investVal.equals("")) )
        {
            // grab views
            TextView resourcesDisplay = (TextView) findViewById(R.id.resourcesLabel);
            TextView statusDisplay = (TextView) findViewById(R.id.statusLabel);
            TextView infoDisplay = (TextView) findViewById(R.id.infoLabel);

            // take in resource values
            investedRes = Integer.parseInt(investVal);
            String temp = resourcesDisplay.getText().toString();
            initialRes = Integer.parseInt(temp);
            newRes = initialRes - investedRes;

			// take in status values
            temp = statusDisplay.getText().toString();
            initialStatus = Double.parseDouble(temp);

			// take in info values
			temp = infoDisplay.getText().toString();
			initialInfo = Integer.parseInt(temp);
			sharedInfo = Integer.parseInt(shareVal);
			newInfo = initialInfo - sharedInfo;

            if( investedRes >= 0 && newRes >= 0 && sharedInfo >= 0 && newInfo >= 0 )
            {
                // clear fields
                mShareEdit.setText("");
                mInvestEdit.setText("");

				// stop timer
				SimFragment.timer.cancel();

				if (player.turnCounter % 19 == 1) {
					statusDamage = 5;
				}

				// update player
				player.status = player.status - statusDamage;
				player.resources = newRes;
				player.turnCounter++;
				player.information = newInfo;
				player.printPlayer();

				// send data to server
				String serverInfo = (player.id + " " + investedRes + " " + initialRes + " " + newRes
						+ " " + sharedInfo + " " + initialInfo + " " + newInfo + " " + rowIndex );

				try {
					dataOut.write(serverInfo, 0, serverInfo.length());
					dataOut.newLine();
					dataOut.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Toast.makeText(this, "Submission successful", Toast.LENGTH_SHORT).show();

				submitButton.setVisibility(View.GONE);
				retrieveButton.setVisibility(View.VISIBLE);
            }

            else {
                Toast.makeText(this, "Invalid input, can not deplete resources or go below 0 status", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            Toast.makeText(this, "Invalid input in share field, either empty or not number", Toast.LENGTH_SHORT).show();
        }
    }

	public void retrieve(View view) {

		// grab views
		Button submitButton = (Button) findViewById(R.id.submitButton);
		Button retrieveButton = (Button) findViewById(R.id.retrieveButton);
		TextView resourcesDisplay = (TextView) findViewById(R.id.resourcesLabel);
		TextView statusDisplay = (TextView) findViewById(R.id.statusLabel);
		TextView infoDisplay = (TextView) findViewById(R.id.infoLabel);
		TextView rewardDisplay = (TextView) findViewById(R.id.rewardLabel);
		ProgressBar statusBar = (ProgressBar) findViewById(R.id.statusBar);

		// wait on info
		int s_server = 0;
		String input = null;
		while( input == null ) {
			try {
				input = dataIn.readLine();
				System.out.println(input);
				if( input != null )
					s_server = Integer.parseInt(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// update player
		double p = player.calculateP( initialStatus, investedRes, s_server );
		player.reward = (player.calculateReward( p, investedRes, sharedInfo, s_server )) / 100;

		// submit success, update display data (resources, status)
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		String status = numberFormat.format(player.status);
		statusDisplay.setText(status);
		statusBar.setProgress( (int) player.status );
		resourcesDisplay.setText(Integer.toString(player.resources));
		infoDisplay.setText(Integer.toString(newInfo));

		String reward = numberFormat.format(player.reward);
		rewardDisplay.setText( "Reward: " + reward );

		// create history text and add
		String historyText = (player.turnCounter + " " + investedRes + " " + sharedInfo + " " + reward + " ");
		ListContent.ListItem i = new ListContent.ListItem((Integer.toString(player.turnCounter)), historyText);
		ListContent.addItem(i);

		// reset timer
		SimFragment.timer.start();
		submitButton.setVisibility(View.VISIBLE);
		retrieveButton.setVisibility(View.GONE);
	}
}
