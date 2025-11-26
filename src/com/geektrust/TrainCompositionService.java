package com.geektrust;

import java.util.ArrayList;
import java.util.List;

public class TrainCompositionService {

  public static List<TrainComposition> filterBogies(String processingStationCode, StationRegistry stationRegistry, List<TrainComposition> trainCompositions){
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
          stationDistance = normalizedDistance(processingStationCode, bogie.getBogieCode(), stationRegistry);
        } catch (Exception e){
          throw new RuntimeException("Unable to find the distance for " + bogie.getBogieCode() +  " Reason " + e);
        }
        if(stationDistance<0)
          continue;
        trainComposition.addBogieInOrder(bogie);
      }
      trainCompositionsFinal.add(trainComposition);
    }
    return trainCompositionsFinal;
  }

  public static TrainComposition mergeBogies(String processingStationCode, StationRegistry stationRegistry, List<TrainComposition> trainCompositions){
    String mergeTrainName = getmergeTrainName(trainCompositions.get(0).getTrainName(), trainCompositions.get(1).getTrainName());
    TrainComposition mergeTrainComposition = new TrainComposition(mergeTrainName);

    List<Bogie> finalBogieOrder = new ArrayList<>();

    for(TrainComposition comp: trainCompositions){
      for(Bogie bogie: comp.getBogieOrder()){
        try{
          addBogieInOrder(finalBogieOrder, stationRegistry, processingStationCode, bogie);
        } catch (Exception e){
          throw new RuntimeException("There is an execption while adding Bogie in Order "+ e);
        }
      }
    }
    mergeTrainComposition.setBogieOrder(finalBogieOrder);
    return mergeTrainComposition;
  }

  private static void addBogieInOrder(List<Bogie> finalBogieOrder, StationRegistry stationRegistry, String processingStationCode, Bogie bogie){
    Integer distance;
    try{
      distance = normalizedDistance(processingStationCode, bogie.getBogieCode(), stationRegistry);
      if (distance <=0)
        return;
    } catch (Exception e){
      throw new RuntimeException("Unable to addBogieInOrder " + e);
    }
    if(finalBogieOrder.isEmpty()){
      finalBogieOrder.add(bogie);
      return;
    }
    int indx=finalBogieOrder.size();
    for(int i=0;i<finalBogieOrder.size(); i++){
      Integer distanceToCompare;
      try{
        distanceToCompare = normalizedDistance(processingStationCode, finalBogieOrder.get(i).getBogieCode(), stationRegistry);
      } catch (Exception e){
        throw new RuntimeException("Unable to addBogieInOrder " + e);
      }
      if(distance>=distanceToCompare){
        indx=i;
        break;
      }
    }
    finalBogieOrder.add(indx, bogie);
  }

  private static Integer normalizedDistance(String processingStationCode, String stationCode, StationRegistry stationRegistry){
    Integer distance;
    Station station = stationRegistry.getStation(stationCode);
    if(station == null){
      throw new RuntimeException("There is no entry in StationRegistry for " +  stationCode);
    }
    String trainName = station.getListOfTrainsThatStop().get(0).getTrainName();
    if(trainName == null){
      throw new RuntimeException("No train stops at " +  stationCode);
    }
    Integer distanceToCompareWith;
    try{
      distanceToCompareWith = findDistance(processingStationCode, stationRegistry, trainName);
    } catch (Exception e) {
      throw new RuntimeException(
          "Unable to find the distance for " + processingStationCode + "Reason " + e);
    }
    if(distanceToCompareWith == null)
      throw new RuntimeException("The " + processingStationCode + " this station doesnot exists in the " + trainName + " route");

    Integer stationDistance;
    try {
      stationDistance = findDistance(stationCode, stationRegistry, trainName);
    } catch (Exception e){
      throw new RuntimeException("Unable to find the distance for " + stationCode +  " Reason " + e);
    }
    distance = stationDistance-distanceToCompareWith;
    return distance;
  }

  private static String getmergeTrainName(String name1, String name2){
    int idx1 = name1.indexOf('_');
    int idx2 = name2.indexOf('_');

    String part1 = name1.substring(idx1+1);
    String part2 = name2.substring(idx2+1);

    return "TRAIN_" + part1 + part2;

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
