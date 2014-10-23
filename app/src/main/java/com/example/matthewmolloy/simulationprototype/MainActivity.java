package com.example.matthewmolloy.simulationprototype;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends ActionBarActivity {

    public static Player player = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SimFragment())
                    .commit();
        }
    }

    public void start(View view) {
        // wait for all other clients to have hit start, wait for server response then start timer
        SimFragment.timer.start();
        Button startButton = (Button) findViewById(R.id.startButton);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        startButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
    }

	// activated when submit button is pressed, set in xml
    public void submit(View view) {
        // set up input data
        EditText mShareEdit = (EditText)findViewById(R.id.shareInput);
        EditText mInvestEdit = (EditText)findViewById(R.id.investInput);

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
            ProgressBar statusBar = (ProgressBar) findViewById(R.id.statusBar);

			String temp;
            // take in resource values
            int investedRes = Integer.parseInt(investVal);
            temp = resourcesDisplay.getText().toString();
            int initialRes = Integer.parseInt(temp);
            int newRes = initialRes - investedRes;

			// take in status values
            temp = statusDisplay.getText().toString();
            double initialStatus = Double.parseDouble(temp);

			// take in info values
			temp = infoDisplay.getText().toString();
			int initialInfo = Integer.parseInt(temp);
			int sharedInfo = Integer.parseInt(shareVal);
			int newInfo = initialInfo - sharedInfo;


            // needs to be dynamically updated depending on other players later

            if( investedRes >= 0 && newRes >= 0 && sharedInfo >= 0 && newInfo >= 0 )
            {
                // clear fields and increment turn count
                mShareEdit.setText("");
                mInvestEdit.setText("");

				// update player
				player.status = player.calculateStatusDamage(initialStatus, investedRes);
				player.resources = newRes;
				player.turnCounter++;
				player.information = newInfo;
				player.printPlayer();

                // submit success, update display data (resources, status)
				DecimalFormat numberFormat = new DecimalFormat("#.0000000000");
				String status = numberFormat.format(player.status);
                statusDisplay.setText(status);
                statusBar.setProgress( (int) player.status);
                resourcesDisplay.setText(Integer.toString(player.resources));
				infoDisplay.setText(Integer.toString(newInfo));

				// display toast success
                String toastText = ("Invested: " + investedRes + " Old Total: " + initialRes +
                        " New Total: " + newRes + "\n" + "Shared: " + sharedInfo + " Old Total: " + initialInfo + " New Total: " + newInfo);
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();

				///////////////////////////////
				// send player info to server//
				///////////////////////////////

                // stop timer
				SimFragment.timer.start();
                // SimFragment.timer.cancel();
            }

            else
            {
                Toast.makeText(this, "Invalid input, can not deplete resources or go below 0 status", Toast.LENGTH_SHORT).show();
            }
        }

        else
        {
            Toast.makeText(this, "Invalid input in share field, either empty or not number", Toast.LENGTH_SHORT).show();
        }
    }

	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.
				INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		return true;
	}

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}
