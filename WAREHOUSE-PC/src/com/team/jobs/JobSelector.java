package com.team.jobs;

import java.util.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
//import java.math.*;

public class JobSelector {

  //private static HashMap<String, Item> items = new HashMap<String, Item>();
  //private static ArrayList<DropLocation> drops = new ArrayList<DropLocation>();
  private static ArrayList<Job> jobs = new ArrayList<Job>();

  public JobSelector(HashMap<String, Item> items, ArrayList<Job> jobs, ArrayList<DropLocation> drops) {
    //this.items = items;
    this.jobs = jobs;
    //this.drops = drops;
    jobs = sortByReward(jobs);
  }

  public synchronized Job getNext() {
    if (jobs.size() > 0) {
      Job nextJob = jobs.get(0);
      jobs.remove(0);
      return nextJob;
    } else {
      return null;
    }
    
  }

  private static ArrayList<Job> sortByReward(ArrayList<Job> jobs) {
    Collections.sort(jobs, new Comparator<Job>() {
      @Override
      public int compare(Job o1, Job o2) {
        return Float.compare(o2.getJobValue(), o1.getJobValue());
      }
    });
    
    return jobs;
  }

}

