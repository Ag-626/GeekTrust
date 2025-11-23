package com.geektrust;

import java.util.ArrayList;
import java.util.List;

public class Train {

  private String trainName;
  private List<Station> route;
  private List<Bogie> bogieOrder;

  public Train(String trainName){
    this.trainName = trainName;
    this.route = new ArrayList<>();
    this.bogieOrder = new ArrayList<>();
  }

  public void setTrainName(String trainName){
    this.trainName = trainName;
  }

  public void addRoute(Station station){
    if(!this.route.contains(station)) {
      this.route.add(station);
    }
  }

  public void addBogie (Bogie bogie){
    if(!this.bogieOrder.contains(bogie)) {
      this.bogieOrder.add(bogie);
    }
  }

  public String getTrainName(){
    return this.trainName;
  }

}
