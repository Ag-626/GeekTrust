package com.example.geektrust;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class AppInitializer {

  private final StationRegistry stationRegistry;
  private final List<Train> trains;
  private final String processingStationCode;

  public AppInitializer() {
    this("config.properties");
  }

  public AppInitializer(String configFilePath) {
    this.stationRegistry = StationRegistry.getInstance();

    Properties props = loadProperties(configFilePath);
    String trainDataFilePath = props.getProperty("trains.data.path");
    this.trains = TrainDataLoader.loadTrains(trainDataFilePath, this.stationRegistry);
    this.processingStationCode = props.getProperty("processing.station.code");
  }

  private Properties loadProperties(String configFilePath) {
    Properties props = new Properties();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(configFilePath)) {
      if (is == null) {
        throw new RuntimeException(configFilePath + " not found in classpath");
      }
      props.load(is);
    } catch (IOException e) {
      throw new RuntimeException("Unable to load " + configFilePath, e);
    }
    return props;
  }

  public StationRegistry getStationRegistry() {
    return this.stationRegistry;
  }

  public List<Train> getTrains() {
    return this.trains;
  }

  public String getProcessingStationCode() {
    return this.processingStationCode;
  }
}