package com.team.jobs;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class JobAssignment extends Thread {
  
  private static JobSelector JOB_SELECTOR;
  private static HashMap<String, Item> items = new HashMap<String, Item>();
  private static ArrayList<Job> jobs = new ArrayList<Job>();
  private static ArrayList<DropLocation> drops = new ArrayList<DropLocation>();
  private final Robot ROBOT;
  

  public JobAssignment(Robot robot, int robotNumber, HashMap<String, Item> items, ArrayList<DropLocation> drops, ArrayList<Job> jobs, JobSelector jobSelector) {
    this.ROBOT = robot;
    this.items = items;
    this.drops = drops;
    this.jobs = jobs;
    this.JOB_SELECTOR = jobSelector;
    //Add logging
    System.out.println("Robot " + robotNumber + " constructor");
  }

  @Override
  public void run() {
    while(true) {
      
      if(ROBOT.requiresJob()) {
        Job nextJob = JOB_SELECTOR.getNext();
        DropLocation dropLocation = drops.get(nextJob.getClosestDrop());
        if (!dropLocation.isBeingUsed()) {
          dropLocation.setUsage(true);
          ROBOT.setJob(nextJob, dropLocation);
          //Add logging
          System.out.println(ROBOT);
        } else {
          int currentDropLocation = 0;
          while (drops.get(currentDropLocation).isBeingUsed()) {
            if (currentDropLocation < drops.size() - 1) {
              currentDropLocation++;
            } else {
              currentDropLocation = 0;
            }
          }

          dropLocation = drops.get(currentDropLocation);
          ROBOT.setJob(nextJob, dropLocation);
          //Add logging           
          System.out.println(ROBOT);
            
        }          
      }
      
      Thread.yield();
    }
  }
   
}

