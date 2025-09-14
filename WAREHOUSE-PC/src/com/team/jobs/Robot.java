package com.team.jobs;

import java.util.Random;
public class Robot extends Thread {
  private Job currentJob;
  private DropLocation currentDropLocation;
  private boolean isAssigned;
  private final int ROBOT_NUMBER;

  public Robot(int robotNumber) {
    this.isAssigned = false;
    this.ROBOT_NUMBER = robotNumber;
  }

  public boolean requiresJob() {
    return !this.isAssigned;
  }

  @Override
  public void run() {
    while (true) {
      if (isAssigned) {
        //This is where the robot would carry out the job
        //I have just added a random delay to simulate this
        Random rand = new Random();
        int n = rand.nextInt(5000) + 1;
        try {
          Thread.sleep(n);
        } catch (InterruptedException e) {
          System.out.println("Couldn't join");
        }

        currentDropLocation.setUsage(false);
        this.isAssigned = false;
        Thread.yield();
      }
    }
  }

  public void setJob(Job job, DropLocation dropLocation) {
    this.currentJob = job;
    this.currentDropLocation = dropLocation;
    isAssigned = true;
  }

  @Override
  public String toString() {
    return ("Number: " + ROBOT_NUMBER + "\nJob: " + currentJob + "\nDrop Location: " + currentDropLocation);
  }
}


