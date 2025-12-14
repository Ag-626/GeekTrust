package com.example.geektrust;

import java.util.ArrayList;
import java.util.List;

public class Train {

  private final String trainName;
  private final List<Station> route;

  public Train(String trainName){
    this.trainName = trainName;
    this.route = new ArrayList<>();
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

