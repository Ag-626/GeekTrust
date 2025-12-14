package com.example.geektrust;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Station {

  private final String stationCode;
  private final String stationName;
  private List <Train> listOfTrainsThatStop;
  private Map <String, Integer> distanceFromSourceByTrain;


  public Station(String stationCode, String stationName){
    this.stationCode = stationCode;
    this.stationName = stationName;
    this.listOfTrainsThatStop = new ArrayList<>();
    this.distanceFromSourceByTrain = new HashMap<>();
  }

  public void addTrain(Train train){
    if(!this.listOfTrainsThatStop.contains(train)) {
      this.listOfTrainsThatStop.add(train);
    }
  }

  public void addDistanceFromSourceByTrain(String trainName, Integer distance){
    distanceFromSourceByTrain.put(trainName,distance);
  }

  public String getStationCode(){
    return this.stationCode;
  }
  public String getStationName(){
    return this.stationName;
  }
  public List < Train > getListOfTrainsThatStop(){
    return this.listOfTrainsThatStop;
  }
  public Integer getDistanceOfStationForParticularTrain(String trainName){
    return this.distanceFromSourceByTrain.get(trainName);
  }

}

