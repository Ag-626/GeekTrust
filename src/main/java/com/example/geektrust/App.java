package com.example.geektrust;

import java.util.List;

public class App {

  private final AppInitializer appInitializer;

  public App(String configFilePath) {
    this.appInitializer = new AppInitializer(configFilePath);
  }

  public void run(String inputFilePath) {
    StationRegistry stationRegistry = appInitializer.getStationRegistry();
    String processingStationCode = appInitializer.getProcessingStationCode();
    List<TrainComposition> trainCompositions;

    try {
      trainCompositions = InputParser.processInput(inputFilePath);
    } catch (Exception e){
      throw new RuntimeException("The process to read the input file failed " + e);
    }

    List<TrainComposition> trainCompositionOnArrival;
    try{
      trainCompositionOnArrival = TrainCompositionService.filterBogies(processingStationCode, stationRegistry, trainCompositions);
    } catch (Exception e){
      throw new RuntimeException("The runtime exception occur while find the trainComposition on Arrival " + e);
    }

    OutputParser.resultOnArrival(trainCompositionOnArrival);

    TrainComposition trainCompositionOnDeparture = TrainCompositionService.mergeBogies(processingStationCode, stationRegistry, trainCompositionOnArrival);

    OutputParser.resultOnDeparture(trainCompositionOnDeparture);
  }
}

