package com.team.jobs;

import java.util.ArrayList;
import java.util.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

public class Job {
  private final String NAME;
  private ArrayList<Item> ITEMS;
  private final float TOTAL_REWARD;
  private ArrayList<Item> itemsTemp;
  private final ArrayList<DropLocation> DROPS;
  private int totalDistance;
  private ArrayList<Float> JOB_VALUE = new ArrayList<Float>();//worth of item
  private int closestDrop;

  public Job(String name, ArrayList<Item> items, ArrayList<DropLocation> drops){
    this.NAME = name;
    this.ITEMS = items;
    this.TOTAL_REWARD = sumReward();
    this.DROPS = drops;
    for(DropLocation i: DROPS){
      Item base = new Item("Base", 0.0f, 0.0f, i.getXValue(), i.getYValue());
      float totalDistance = getOrder(base);
      float dropValue = TOTAL_REWARD / totalDistance;
      JOB_VALUE.add(dropValue);
    }
    this.closestDrop = 0; 
  }

  public void setClosestDrop(int dropIndex) {
    this.closestDrop = dropIndex;
  }

  public int getClosestDrop() {
    return this.closestDrop;
  }

  public float getJobValue() {
    float smallestDistance = JOB_VALUE.get(0);
    this.setClosestDrop(0);    
    for (int i = 1; i < JOB_VALUE.size(); i++) {
      if (JOB_VALUE.get(i) > smallestDistance) {
        smallestDistance = JOB_VALUE.get(i);
        this.setClosestDrop(i);
      }
    }

    return smallestDistance;
  }

  private float sumReward(){
    float rewardTotal = 0;
    for(Item i : ITEMS){
      rewardTotal+=i.getReward();
    }
    return rewardTotal;
  }

  private Object[] getClosest(int x, int y){
    HashMap<Item, Integer> itemDistances = new HashMap<Item, Integer>();
    for(Item i: itemsTemp){
      int distance = Math.abs(x - i.getX())+Math.abs(y - i.getY());
      itemDistances.put(i,distance);
    }
    HashMap<Item,Integer> itemDistances2 = sortByValues(itemDistances);
    Set<Item> itemSet = itemDistances2.keySet();
    Item[] itemDistanceArray = itemSet.toArray(new Item[itemSet.size()]);
    Item best = itemDistanceArray[itemDistanceArray.length-1];
    return new Object[]{(Item) best,(Integer) itemDistances2.get(best)};
  }

  private Object[] getFurthest(int x, int y){
    HashMap<Item, Integer> itemDistances = new HashMap<Item, Integer>();
    for(Item i: itemsTemp){
      int distance = Math.abs(x - i.getX())+Math.abs(y - i.getY());
      itemDistances.put(i,distance);
    }
    HashMap<Item,Integer> itemDistances2 = sortByValues(itemDistances);
    Set<Item> itemSet = itemDistances2.keySet();
    Item[] itemDistanceArray = itemSet.toArray(new Item[itemSet.size()]);
    Item best = itemDistanceArray[0];
    return new Object[]{(Item) best,(Integer) itemDistances2.get(best)};
  }

  private float getOrder(Item base){
    itemsTemp = ITEMS;
    Object[] itemDistance;
    float weightTotal = 0;
    int distanceTotal=0;
    ArrayList<Item> itemOrder = new ArrayList<>();
    itemOrder.add(base);
    while(itemsTemp.size() > 0){
      //System.out.println("Size " + ITEMS.size());
      if(itemOrder.get(itemOrder.size()-1)==base){
        itemDistance= getFurthest(itemOrder.get(itemOrder.size()-1).getX(),itemOrder.get(itemOrder.size()-1).getY());
      }else{
        itemDistance= getClosest(itemOrder.get(itemOrder.size()-1).getX(),itemOrder.get(itemOrder.size()-1).getY());
        //System.out.println("Size " + itemOrder.size());
      }
      Item closest = (Item) itemDistance[0];
      Integer distance = (Integer) itemDistance[1];
      //System.out.println(base + " " + distance);
      if(weightTotal+closest.getWeight()<50){
        weightTotal+=closest.getWeight();
        distanceTotal +=distance;
        itemOrder.add(closest);
        itemsTemp.remove(closest);
      }else{
        weightTotal = 0;
        distanceTotal+=(Math.abs(base.getX()-itemOrder.get(itemOrder.size()-1).getX())+Math.abs(base.getY()-itemOrder.get(itemOrder.size()-1).getY()));
        itemOrder.add(base);
      }
    }
    distanceTotal+=(Math.abs(base.getX()-itemOrder.get(itemOrder.size()-1).getX())+Math.abs(base.getY()-itemOrder.get(itemOrder.size()-1).getY()));
    itemOrder.add(base);
    //System.out.println(base + " " + distanceTotal);
    //System.out.println(itemOrder);
    ITEMS = itemOrder;
    return distanceTotal;
    //System.out.println(totalDistance);

  }

  private HashMap sortByValues(HashMap<Item,Integer> map) { 
    List<Map.Entry<Item,Integer>> list = new LinkedList<>(map.entrySet());
    // Defined Custom Comparator here
    Collections.sort(list, new Comparator() {
         public int compare(Object o2,Object o1) {
            return ((Comparable) ((Map.Entry<Item,Integer>) (o1)).getValue())
               .compareTo(((Map.Entry<Item,Integer>) (o2)).getValue());
         }
    });

    // Here I am copying the sorted list in HashMap
    // using LinkedHashMap to preserve the insertion order
    HashMap<Item,Integer> sortedHashMap = new LinkedHashMap<Item,Integer>();
    for (Iterator<Map.Entry<Item,Integer>> it = list.iterator(); it.hasNext();) {
           Map.Entry<Item,Integer> entry = (Map.Entry<Item,Integer>) it.next();
           sortedHashMap.put(entry.getKey(), entry.getValue());
    } 
    return sortedHashMap;
  }

  @Override
  public String toString(){
    return ("Name: " + NAME + "\n" + "Reward: " + TOTAL_REWARD + "\nBest Drop: " + DROPS.get(closestDrop) + "\nItemValue: " + JOB_VALUE);
  }
  
}

