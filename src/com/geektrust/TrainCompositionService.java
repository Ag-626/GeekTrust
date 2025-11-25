package com.geektrust;

import java.util.ArrayList;
import java.util.List;

public class TrainCompositionService {

  public static List<TrainComposition> orderOfBogies(String processingStationCode, StationRegistry stationRegistry, List<TrainComposition> trainCompositions){
    List<TrainComposition> trainCompositionsFinal = new ArrayList<>();

    for(TrainComposition comp: trainCompositions){
      String trainName = comp.getTrainName();
      Integer distanceToCompareWith;
      try{
        distanceToCompareWith = findDistance(processingStationCode, stationRegistry, trainName);
      } catch (Exception e) {
        throw new RuntimeException(
            "Unable to find the distance for " + processingStationCode + "Reason " + e);
      }
      if(distanceToCompareWith == null)
        throw new RuntimeException("The " + processingStationCode + " this station doesnot exists in the " + trainName + " route");
      TrainComposition trainComposition = new TrainComposition(trainName);
      List<Bogie> bogies = comp.getBogieOrder();
      for(Bogie bogie: bogies){
        Integer stationDistance;
        try {
          stationDistance = findDistance(bogie.getBogieCode(), stationRegistry, trainName);
        } catch (Exception e){
          throw new RuntimeException("Unable to find the distance for " + bogie.getBogieCode() +  " Reason " + e);
        }
        if((stationDistance == null) || (stationDistance>=distanceToCompareWith)){
          trainComposition.addBogieInOrder(bogie);
        }
      }
      trainCompositionsFinal.add(trainComposition);
    }
    return trainCompositionsFinal;
  }

  private static Integer findDistance(String stationCode, StationRegistry stationRegistry, String trainName){
    Station station = stationRegistry.getStation(stationCode);

    if(station == null){
      throw new RuntimeException("There is no entry in StationRegistry for " +  stationCode);
    }
    Integer distance = station.getDistanceOfStationForParticularTrain(trainName);
    return distance;
  }

}
