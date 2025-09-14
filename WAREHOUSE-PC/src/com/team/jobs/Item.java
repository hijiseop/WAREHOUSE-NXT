package com.team.jobs;

public class Item {

	private final String NAME;
	  private final int X_VALUE;
	  private final int Y_VALUE;
	  private final float REWARD;
	  private final float WEIGHT;

	  public Item(String name, float reward, float weight, int xValue, int yValue) {
	    this.NAME = name;
	    this.X_VALUE = xValue;
	    this.Y_VALUE = yValue;
	    this.REWARD = reward;
	    this.WEIGHT = weight;
	  }
	  
	  public int getX(){
	    return this.X_VALUE;
	  }

	  public int getY(){
	    return this.Y_VALUE;
	  }

	  public float getWeight(){
	    return this.WEIGHT;
	  }
	 
	  public String getName(){
	    return this.NAME;
	  }

	  public float getReward(){
	    return this.REWARD;
	  }

	  @Override
	  public String toString() {
	    return NAME + ", X: " + X_VALUE + ", Y: " + Y_VALUE + ", Reward: " + REWARD + ", Weight: " + WEIGHT;
	  }


}
