package com.geektrust;

import java.util.ArrayList;
import java.util.List;

public class Train {

  private String trainName;
  private List<Station> route;

  public Train(String trainName){
    this.trainName = trainName;
    this.route = new ArrayList<>();
  }

  public void setTrainName(String trainName){
    this.trainName = trainName;
  }

  public void addRoute(Station station){
    if(!this.route.contains(station)) {
      this.route.add(station);
    }
  }


  public String getTrainName(){
    return this.trainName;
  }

}
