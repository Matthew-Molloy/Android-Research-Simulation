package com.example.matthewmolloy.simulationprototype;

import java.util.Random;

public class Player {
	private String id;
    private int resources, information;
	private double status, reward;
	private int turnCounter = 0;

	public Player() {

        // generate id
        int temp, randomInt;
        String randomID = "00000";
        char convertedRandom;
        for( randomInt = 0; randomInt < 5; randomInt++ ) {
			// randomize digit
            temp = randInt( 0, 9 );
			convertedRandom = Character.forDigit(temp, 10);
            randomID = replace(randomID, randomInt, convertedRandom);
        }

        // set data
        this.id = randomID;
        this.resources = 100;
        this.status = 100;
        this.information = 100;
    }

    public String printPlayer() {
        return ("Player ID: " + id + " Resources Remaining: " + resources + " Status: " + status + "/1" );
    }

    public double calculateP(double initialStatus, int invested, int s_server) {

		System.out.println(Integer.toString(invested) + " s: " + (Integer.toString(s_server)));
		double a = invested + s_server;
		double b = 1 + a;

		System.out.println(a);
		System.out.println(b);

		// potential formula: double result = (initialStatus - (10 / (1 + Math.exp(s_server + a * invested + b))));
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

		return ( ( 2.5 * shared + s_server) * a0 * Math.log( 1 + a1 * invested) - (p * Vi) - (zeta * (shared*shared)) - (theta * invested) );
	}

    public static String replace(String str, int index, char replace) {
        if(str == null){
            return null;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}

	public double getStatus() {
		return status;
	}

	public void setStatus(double status) {
		this.status = status;
	}

	public int getInformation() {
		return information;
	}

	public void setInformation(int information) {
		this.information = information;
	}

	public int getTurnCounter() {
		return turnCounter;
	}

	public void setTurnCounter(int turnCounter) {
		this.turnCounter = turnCounter;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}
}