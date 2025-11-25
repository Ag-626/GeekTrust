package com.geektrust;

import java.util.List;

public class Main {

  public static void main(String [] args){

    if(args.length < 1){
      throw new IllegalArgumentException("Missing input file path");
    }

    String inputFilePath = args[0];
    String configFilePath = "resources/config.properties";

    if(args.length >=2){
      configFilePath = args[1];
    }

    AppInitializer appInitializer = new AppInitializer(configFilePath);

    StationRegistry stationRegistry = appInitializer.getStationRegistry();
    List<Train> trains = appInitializer.getTrains();
    String processingStationCode = appInitializer.getProcessingStationCode();

    List<TrainComposition> trainCompositions;

    try {
      trainCompositions = InputParser.processInput(inputFilePath);
    } catch (Exception e){
      throw new RuntimeException("The process to read the input file failed " + e);
    }


  }

}
