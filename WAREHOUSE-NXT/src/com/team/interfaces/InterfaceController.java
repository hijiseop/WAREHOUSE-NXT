package com.team.interfaces;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;

import java.util.ArrayList;
import lejos.util.Delay;


public class InterfaceController {
	private static final int TIMEOUT_DELAY = 15000;
	private static final int READ_DELAY = 2500;
	
	private static String item = "a, 500";
	private static String itemDropoffLocation = "4, 2, a";
	private static String currentJob = "1001, a, 1, b, 2, z, 20";
	
	private static int numItems;
	private static int maxItems;
	private static int numItemsPicked;
	private static int numItemsDropped;
	

	public InterfaceController () {
		Sound.setVolume(Sound.VOL_MAX/3);
		Sound.twoBeeps();
	}
	
	
	// Getters and setters
	public String getItem () {
		return item;
	}
	public void setItem (String newItem) {
		item = newItem;
	}
	
	public String getItemDropOffLocation () {
		return itemDropoffLocation;
	}
	public void setItemDropOffLocation (String newItemDropoffLocation) {
		itemDropoffLocation = newItemDropoffLocation;
	}
	
	public String currentJob () {
		return currentJob;
	}
	public void setCurrentJob (String newCurrentJob) {
		currentJob = newCurrentJob;
	}

	
	public int getNumItems () {
		return numItems;
	}
	public void setNumItems (int newNumItems) {
		numItems = newNumItems;
	}
	
	public int getMaxItems () {
		return maxItems;
	}
	public void setMaxItems (int newMaxItems) {
		maxItems = newMaxItems;
	}
	
	public int getNumItemsPicked () {
		return numItemsPicked;
	}
	public void setNumItemsPicked (int newNumItemsPicked) {
		numItemsPicked = newNumItemsPicked;
	}
	
	public int getNumItemsDropped () {
		return numItemsDropped;
	}
	public void setNumItemsDropped (int newNumItemsDropped) {
		numItemsDropped = newNumItemsDropped;
	}
	
	
	
	// Helper functions
	private static void msg (String text, boolean clearScreen, int delay) {	
		if (clearScreen) {
			LCD.clear();
		}
		
		//String[] words = text.split(" "); // Works on eclipse oxygen
		
		ArrayList<String> words = new ArrayList<String>();
		char[] textChars = text.toCharArray();
		
		String element = "";
		for (int i=0; i<textChars.length; i++) {
			int asciiForSpace = 32;
			
			if (i == textChars.length - 1) {
				element += textChars[i];
				words.add(element);
			} else {
				if (!(textChars[i] == asciiForSpace)) {
					element += textChars[i];
				} else {
					words.add(element);
					element = "";
				}
			}	
		}
		
		int spaceAvailable = 16;
		int spaceRequired = 0;
		
		for (int i=0; i<words.size(); i++) {
			String word = words.get(i);
			spaceRequired = word.length();
			
			if (spaceRequired == spaceAvailable) { 
				System.out.println(word);
				spaceAvailable = 16;
			} else if (spaceRequired < spaceAvailable) {
				System.out.print(word + " ");
				spaceAvailable -= spaceRequired + 1; // +1 For the space
			} else if (spaceRequired > 16) {
				
				while (true) {
					System.out.println();
					System.out.println(word.substring(0, 15) + "-");
					
					spaceAvailable = 16;
					word = word.substring(15, word.length());				
					spaceRequired = word.length();
					
					if (spaceRequired == spaceAvailable) { 
						System.out.println(word);
						spaceAvailable = 16;
						break;
					} else if (spaceRequired < spaceAvailable) {
						System.out.print(word + " ");
						spaceAvailable -= spaceRequired + 1; // +1 For the space
						break;
					}
				}			
			} else {
				System.out.println();
				spaceAvailable = 16;
				i--;
			}
		}
		
		System.out.println();
		Delay.msDelay(delay);
	}
		
	private static int selectNumItems (String jobType, String item, int max, int n) { //jobType = {pick, drop}
		while (true) {			
			n = (int) theNumOfItems();
			 
			// Limits the number of items to the appropriate range
			if (n < 0) {
				n = 0;
			} else if (n > max) {
				n = max;
			}
			
			msg("Item: " + item, true, 0);
			msg("Max " + jobType + " limit: " + max, false, 0);
			msg("Current num " + jobType + " :" + n, false, 0);
			
			int whichButton = Button.waitForAnyPress(TIMEOUT_DELAY); 
			if (whichButton == Button.ENTER.getId()) {
				break;
			} else if (whichButton == 0) {
				msg("Selecting number of items timed out", true, 0);
				n = max;
				msg("Max amount selected", false, READ_DELAY);
			}
		}
		
		return n;
	}	
	
	private static double theNumOfItems () {
		String numOfItems = "";
		Delay.msDelay(1000);
			while (true) {
				LCD.clear();
				
				if (Button.RIGHT.isDown()) {
					// Allows users to change the number of items to be picked
					numOfItems += "1";
				} else if (Button.LEFT.isDown()) {
					numOfItems += "0";
				}
				
				int whichButton = Button.waitForAnyPress(TIMEOUT_DELAY); // Also acts as a delay
				if (whichButton == Button.ENTER.getId()) {
					break;
				}
			}
			System.out.println(numOfItems);
			double itemAmount = binaryToInteger(numOfItems);
			return itemAmount;	
	}
	
	private static double binaryToInteger (String binary) {
	    char[] num = binary.toCharArray();
	    double outcome = 0.0;
	    double count = 0.0;
	    
	    for(int i = num.length-1; i >= 0; i--) {
	         if (num[i]=='1') {
	        	 outcome +=  Math.pow(2.0, count);
	         }
	         count++;
	    }
	    return outcome;
	}

		
	
	// Machine states
	public void noJobs () {
		msg("Waiting for next job", true, READ_DELAY);
	}

	public void currentJob (String job) {
		msg("Current job: " + job, true, READ_DELAY);
	}
		
	public void pickUp (int maxItems) {		
		msg("Ready to pick up: " + item, true, 0);
		msg("Press enter to pick", false, 0);
		
		int whichButton = Button.waitForAnyPress(TIMEOUT_DELAY);
		if (whichButton == Button.ENTER.getId()) {
			numItems = 1;
			numItems = selectNumItems ("pick", item, maxItems, numItems);
		} else {
			msg("1: Pick up timed out", true, READ_DELAY);
			numItems = maxItems; // Pick up max items if timeout
		}
		
		msg("Picked up " + numItems + " items: " + item, true, 0);
		msg("Go to: " + itemDropoffLocation + " ?", false, 0);
		
		whichButton = Button.waitForAnyPress(TIMEOUT_DELAY);
		if (whichButton == Button.ENTER.getId()) {
		} else {
			msg("2: Pick up timed out", true, READ_DELAY);
		}
		
		msg("Going to drop off location: " + item, true, READ_DELAY); // Go automatically if time out
		
		Sound.beep();
		setNumItemsPicked(numItems);
	}
			
	public void dropOff(int numItems) {
		msg("Ready to drop off: " + item, true, 0);
		msg("Press enter to drop", false, 0);
		int numItemsToDrop = 1;
		
		int whichButton = Button.waitForAnyPress(TIMEOUT_DELAY);
		if (whichButton == Button.ENTER.getId()) {
			numItemsToDrop = selectNumItems ("drop", item, numItems, numItemsToDrop);
		} else {
			msg("Drop off timed out", true, READ_DELAY);
			numItemsToDrop = numItems; // Drops max items in case of time out
		}
		
		msg("Dropped " + numItemsToDrop + " items: " + item, true, READ_DELAY);
		
		Sound.beep();
		setNumItemsDropped(numItemsToDrop);
	}
		
	public void jobCancelled () {
		msg("Job cancelled", false, 0);
		msg("Go to starting position?", false, 0);
		
		int whichButton = Button.waitForAnyPress(TIMEOUT_DELAY);
		if (whichButton == Button.ENTER.getId()) {
		} else {
			msg("Reset robot timed out", true, READ_DELAY);
		}
		msg("Going to starting position", true, READ_DELAY);
		Sound.beep();
	}

}
