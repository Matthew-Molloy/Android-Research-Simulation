package com.example.matthewmolloy.simulationprototype;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CDT extends CountDownTimer {

    public View view;

    public CDT(long startTime, long interval) {
        super(startTime, interval);
    }

    @Override
    public void onFinish() {

        ///////////////////////////////////
        // put data submission stuff here//
        ///////////////////////////////////

        EditText resources = (EditText) view.findViewById(R.id.shareInput);
        EditText invested = (EditText) view.findViewById(R.id.investInput);
        resources.setText("");
        invested.setText("");


        // increment turn counter and quarterly give resources
        MainActivity.player.turnCounter++;
        if( MainActivity.player.turnCounter % 5 == 0 )
        {
            TextView resourcesDisplay = (TextView) view.findViewById(R.id.resourcesLabel);

            MainActivity.player.resources += 100;
            resourcesDisplay.setText(Integer.toString(MainActivity.player.resources));
        }

        // display in console
        System.out.println(MainActivity.player.printPlayer() + " Turn: " + MainActivity.player.turnCounter);

        // reset
        start();
    }

    public void onTick(long millisUntilFinished) {
        TextView mTextField = (TextView) view.findViewById(R.id.timer);
        mTextField.setText("Seconds remaining: " + (millisUntilFinished / 1000));
    }
}
