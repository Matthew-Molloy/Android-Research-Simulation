package com.example.matthewmolloy.simulationprototype;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author matthewmolloy
 */
public class Player {
	public ArrayList<String> turnList;
    public String id;
    public int resources;
    public double status;
    public int information;
    public int turnCounter = 0;
	public double reward;
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
        this.status = 100;
        this.information = 100;
		this.turnList = new ArrayList<String>();
		// this.state = new State_Neutral();
    }

	// for AI stage
/*	public void update() {
		state.execute(this);
	}

	// for AI stage
	public void changeState( State newState ) {
		this.state = newState;
	}
*/

    public String printPlayer() {
        return ("Player ID: " + id + " Resources Remaining: " + resources + " Status: " + status + "/1" );
    }

    public double calculateP(double initialStatus, int invested, int s_server) {
        // int a = 1;
        // int b = 1;

		System.out.println(Integer.toString(invested) + " s: " + (Integer.toString(s_server)));
		double a = invested + s_server;
		double b = 1 + a;

		System.out.println(a);
		System.out.println(b);

		// double result = (initialStatus - (10 / (1 + Math.exp(s_server + a * invested + b))));
		double result = a / b;
		System.out.println(Double.toString(result));
        return result;
    }

	public double calculateReward( double p, int invested, int shared, int s_server ) {
		int a0 = 100;
		int a1 = 1;
		int Vi = 20;
		double theta = 0.5;
		double zeta = 0.5;

		// return ( (1 + s_server) * a0 * Math.log( 1 + a1 * invested * (1 + shared)) - (p * Vi)
		//		- (zeta * (shared*shared)) - (theta * invested));
		return ( ( 2.5 * shared + s_server) * a0 * Math.log( 1 + a1 * invested) - (p * Vi) - (zeta * (shared*shared)) - (theta * invested) );
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