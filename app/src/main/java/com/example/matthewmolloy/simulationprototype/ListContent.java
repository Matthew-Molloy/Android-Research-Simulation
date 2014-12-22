package com.example.matthewmolloy.simulationprototype;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ListContent {
	static String filename = "HistoryFile.txt";
	static FileOutputStream outputStream;
	static FileInputStream inputStream;
	static OutputStreamWriter output = null;
	public static Context c;

	public ListContent(Context c) {
		this.c = c;
		try {
			outputStream = c.openFileOutput(filename, c.MODE_PRIVATE);
			inputStream = c.openFileInput(filename);
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

	static {
		ListItem i = new ListItem("turn", "   Turn");
		addNonSplitItem(i);
		i = new ListItem("invested", "Invested");
		addNonSplitItem(i);
		i = new ListItem("shared", "Shared");
		addNonSplitItem(i);
		i = new ListItem("reward", "Reward");
		addNonSplitItem(i);
/*		// retrieve saved moves
		String ret = "";

		try {
			inputStream = c.openFileInput(filename);

			if ( inputStream != null ) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ( (receiveString = bufferedReader.readLine()) != null ) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
				ListItem i = new ListItem(Integer.toString(MainActivity.player.turnCounter), ret);
				ITEMS.add(i);
			}
		}
		catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}
*/
	}

//	public static List<ListItem> readFromFile() {

//	}

	public static void addNonSplitItem(ListItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

    public static void addItem(ListItem item) {
		ListItem i;

		String delims = "[ ]+";
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

			if ( inputStream != null ) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ( (receiveString = bufferedReader.readLine()) != null ) {
					stringBuilder.append(receiveString);
				}

				// inputStream.close();
				String ret = stringBuilder.toString();
				System.out.println(ret);
			}
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
