package com.example.matthewmolloy.simulationprototype;
import java.util.Random;
import java.lang.Math;

/**
 *
 * @author matthewmolloy
 */
public class Player {
    public String id;
    public int resources;
    public double status;
    public int information;
    public int turnCounter = 0;
	// public State state;

    public Player() {
        // generate id
        int temp, i;
        String s = "00000";
        char c;
        for( i = 0; i < 5; i++ ) {
            temp = randInt( 0, 9 );
            c = Character.forDigit(temp, 10);
            s = replace(s, i, c);
        }

        // set data
        this.id = s;
        this.resources = 100;
        this.status = 1;
        this.information = 10;
    }

	public void update() {
		// AI stuff for later
	}

    public String printPlayer() {
        return ("Player ID: " + id + " Resources Remaining: " + resources + " Status: " + status + "/100" );
    }

    public double calculateStatusDamage(double initialStatus, int invested) {
        int server = 1;
        int a = 1;
        int b = 1;

		double result = (initialStatus - (1 / (1 + Math.exp(server + a * invested + b))));
		System.out.println(Double.toString(result));
        return result;
    }

    public static String replace(String str, int index, char replace) {
        if(str == null){
            return str;
        }

        else if(index < 0 || index >= str.length()) {
            return str;
        }

        char[] chars = str.toCharArray();
        chars[index] = replace;
        return String.valueOf(chars);
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();

        return rand.nextInt((max - min) + 1) + min;
    }
}