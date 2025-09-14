package com.team.jobs;

public class DropLocation {

	 private boolean isBeingUsed;
	  private final int X_VALUE;
	  private final int Y_VALUE;

	  public DropLocation(int xValue, int yValue) {
	    this.isBeingUsed = false;
	    this.X_VALUE = xValue;
	    this.Y_VALUE = yValue;
	  }

	  public void setUsage(boolean usage) {
	    this.isBeingUsed = usage;
	  }

	  public boolean isBeingUsed() {
	    return this.isBeingUsed;
	  }

	  public int getXValue() {
	    return this.X_VALUE;
	  }

	  public int getYValue() {
	    return this.Y_VALUE;
	  }

	  public String toString() {
	    return "X: " + this.X_VALUE + "\nY: " + this.Y_VALUE;
	  }


}
