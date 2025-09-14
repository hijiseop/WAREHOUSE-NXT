package com.team.jobs;

import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Map;
import java.util.HashMap;
public class TestClass {

  private static JobSelector JOB_SELECTOR;
  private static final int NUM_OF_ROBOTS = 2; 
  private static ArrayList<JobAssignment> assigners = new ArrayList<JobAssignment>();
  private static HashMap<String, Item> items = new HashMap<String, Item>();
  private static ArrayList<Job> jobs = new ArrayList<Job>();
  private static ArrayList<DropLocation> drops = new ArrayList<DropLocation>();
  private static ArrayList<Robot> robots = new ArrayList<Robot>();
  private static final String JOB_FILE = "resources/jobs.csv";
  private static final String DROP_FILE = "resources/drops.csv";
  private static final String ITEM_FILE = "resources/items.csv";
  private static final String ITEM_LOC_FILE = "resources/locations.csv";


  public static void main(String[] args) {
    loadFiles();
    JOB_SELECTOR = new JobSelector(items, jobs, drops);
    for (int i = 1; i <= NUM_OF_ROBOTS; i++) {
      Robot nextRobot = new Robot(i);
      //nextRobot.start();
      assigners.add(new JobAssignment(nextRobot, i, items, drops, jobs, JOB_SELECTOR));
      assigners.get(i - 1).start();
      /*try {
        assigners.get(i - 1).join();
      } catch (InterruptedException e) {
        System.out.println("Couldn't join");
      }*/  
      nextRobot.start();
      //System.out.println("Robot " + i + " started.");
      
    }

    //for (JobAssignment assigner : assigners) {
      //assigner.start();
    //}
  }

  public static void loadFiles() {
    BufferedReader itemReader = null;
    BufferedReader itemLocReader = null;
    BufferedReader jobReader = null;
    BufferedReader dropReader = null;
    
    
    try {
      itemReader = new BufferedReader(new FileReader(ITEM_FILE));
      itemLocReader = new BufferedReader(new FileReader(ITEM_LOC_FILE));
      jobReader = new BufferedReader(new FileReader(JOB_FILE));
      dropReader = new BufferedReader(new FileReader(DROP_FILE));
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }

    String itemName;
    int xValue;
    int yValue;
    float reward;
    float weight;
    String itemLine;
    String locLine;
    String[] currentLine;
  
    String jobName;
    try {
      int countDrop = 0;
      String dropLine;
      while ((dropLine = dropReader.readLine()) != null) { //create job objects from file
        ArrayList<Item> dropList = new ArrayList<Item>();
        currentLine = dropLine.split(",");
        xValue = Integer.parseInt(currentLine[0]);
        yValue = Integer.parseInt(currentLine[1]);
        DropLocation newDrop = new DropLocation(xValue, yValue);
        drops.add(newDrop);
      }
      while ((itemLine = itemReader.readLine()) != null) {//create item objects from file
        currentLine = itemLine.split(",");
        itemName = currentLine[0];
        reward = Float.parseFloat(currentLine[1]);
        weight = Float.parseFloat(currentLine[2]);
        locLine = itemLocReader.readLine();
        currentLine = locLine.split(",");
        xValue = Integer.parseInt(currentLine[0]);
        yValue = Integer.parseInt(currentLine[1]);
        Item newItem = new Item(itemName, reward, weight, xValue, yValue);
        //System.out.println(newItem);
        items.put(itemName, newItem);
      } 
      /*for (Map.Entry<String, Item> entry : items.entrySet()) {
        System.out.println(entry.getValue());
      }*/
      while ((itemLine = jobReader.readLine()) != null) { //create job objects from file
        ArrayList<Item> itemList = new ArrayList<Item>();
        currentLine = itemLine.split(",");
        jobName = currentLine[0];
        for(int i=1; i < currentLine.length-1; i+=2){
          itemName = currentLine[i];
          int itemNumber = Integer.parseInt(currentLine[i+1]);
          for(int j=0;j < itemNumber; j++){
            itemList.add(items.get(itemName));
          }
        }
        /*System.out.println(jobName);
        for (Item item : itemList) {
          System.out.println(item);
        }*/
        Job newJob = new Job(jobName,itemList,drops);
        //System.out.println(newJob);
        jobs.add(newJob);        
        /*for (int i = 0; i < drops.size(); i++) {
          jobs.get(i).put(jobName, newJob);
        }*/
      } 

    } catch (IOException e) {
      System.out.println("Couldn't read file");
    }
  }
}

