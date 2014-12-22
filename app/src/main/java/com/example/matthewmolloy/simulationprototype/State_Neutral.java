package com.example.matthewmolloy.simulationprototype;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by matthewmolloy on 10/23/14.
 */
public class State_Neutral implements State {

	public View view;
	public Activity activity;

	public State_Neutral(View v, Activity a) {
		this.view = v;
		this.activity = a;
	}

	public void execute(Player player) {
		// play neutrally, not too conservative and not too generous
		int currentRes = player.resources;
		int currentInfo = player.information;

		int ResToInvest = currentRes / 10;
		int InfoToShare = currentInfo / 10;

		EditText mShareEdit = (EditText) view.findViewById(R.id.shareInput);
		EditText mInvestEdit = (EditText) view.findViewById(R.id.investInput);

		mShareEdit.setText(Integer.toString(InfoToShare));
		mInvestEdit.setText(Integer.toString(ResToInvest));

		// activity.submit(view);
	}

}
