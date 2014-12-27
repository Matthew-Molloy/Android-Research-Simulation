package com.example.matthewmolloy.simulationprototype;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListContent {
	static String filename = "HistoryFile.txt";
	static FileOutputStream outputStream;
	static FileInputStream inputStream;
	static OutputStreamWriter output = null;
	static String delims = "[ ]+";
	Context c;

	public ListContent(Context con) {
		c = con;
		try {
			outputStream = c.openFileOutput(filename, c.MODE_APPEND);
			inputStream = c.openFileInput(filename);
			setupItems();
		} catch (FileNotFoundException e ) {
			e.printStackTrace();
		}
	}

    /**
     * An array of items.
     */
    public static List<ListItem> ITEMS = new ArrayList<ListItem>();

    /**
     * A map of items, by ID.
     */
    public static Map<String, ListItem> ITEM_MAP = new HashMap<String, ListItem>();

	public static void setupItems() {
		// Add identifier items
		ListItem i = new ListItem("turn", "   Turn");
		addNonSplitItem(i);
		i = new ListItem("invested", "Invested");
		addNonSplitItem(i);
		i = new ListItem("shared", "  Shared");
		addNonSplitItem(i);
		i = new ListItem("reward", "Reward");
		addNonSplitItem(i);

		// retrieve saved moves
		try {
			if ( inputStream != null ) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString;
				int ctr = 10000;

				while ( (receiveString = bufferedReader.readLine()) != null ) {
					// split string
					receiveString += " ";
					String[] itemNums = receiveString.split(delims);

					// add items
					int ii = 0;
					while( ii < itemNums.length ) {
						i = new ListItem(Integer.toString(ctr), itemNums[ii]);
						addNonSplitItem(i);
						ctr++;
						ii++;
					}
				}
				inputStream.close();
			}
		}
		catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

	}

	public static void addNonSplitItem(ListItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

    public static void addItem(ListItem item) {
		ListItem i;

		String[] itemNums = (item.content).split(delims);

		for( int ii = 0; ii < 4; ii++ ) {
			i = new ListItem(item.id, itemNums[ii]);
			ITEMS.add(i);
			ITEM_MAP.put(i.id, i);
		}

		// save item
		try {
			if( output == null ) {
				output = new OutputStreamWriter(outputStream);
			}
			output.write(item.content);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static class ListItem {
        public String id;
        public String content;

        public ListItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
