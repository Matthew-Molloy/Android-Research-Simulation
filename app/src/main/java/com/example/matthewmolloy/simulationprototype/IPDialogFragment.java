package com.example.matthewmolloy.simulationprototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class IPDialogFragment extends DialogFragment {

	public Dialog onCreateDialog(Bundle savedInstanceState) {
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	// Get the layout inflater
	LayoutInflater inflater = getActivity().getLayoutInflater();

	// Inflate and set the layout for the dialog
	// Pass null as the parent view because its going in the dialog layout
	final View v = (inflater.inflate(R.layout.ip_dialog, null));
	builder.setView(v).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int id) {
			EditText mIpEdit = (EditText)v.findViewById(R.id.ipaddress);
			MainActivity.connectIP = mIpEdit.getText().toString();
		}
	})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					IPDialogFragment.this.getDialog().cancel();
				}
			});
	return builder.create();
	}
}
