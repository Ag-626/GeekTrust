package com.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class AppInitializer {

  private final StationRegistry stationRegistry;
  private final List<Train> trains;
  private final String processingStationCode;

  public AppInitializer(String configFilePath) {
    this.stationRegistry = StationRegistry.getInstance();

    Properties props = loadProperties(configFilePath);
    String trainDataFilePath = props.getProperty("trains.data.path");
    this.trains = TrainDataLoader.loadTrains(trainDataFilePath, this.stationRegistry);
    this.processingStationCode = props.getProperty("processing.station.code");
  }

  private Properties loadProperties(String configFilePath) {
    Properties props = new Properties();
    try (FileInputStream fis = new FileInputStream(configFilePath)) {
      props.load(fis);
    } catch (IOException e) {
      throw new RuntimeException("Unable to load " + configFilePath, e);
    }
    return props;
  }

  public StationRegistry getStationRegistry(){
    return this.stationRegistry;
  }

  public List<Train> getTrains() {
    return this.trains;
  }

  public String getProcessingStationCode(){
    return this.processingStationCode;
  }

}
