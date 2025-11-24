package com.geektrust;

import java.util.HashMap;
import java.util.Map;

public class StationRegistry {

  private static final StationRegistry INSTANCE = new StationRegistry();

  private final Map<String, Station> stationMapping;

  private StationRegistry(){
    this.stationMapping = new HashMap<>();
  }

  public static StationRegistry getInstance() {
    return INSTANCE;
  }


  public Station getOrCreateStation(String stationCode, String stationName) {
    Station existing = this.stationMapping.get(stationCode);
    if (existing != null) {
      return existing;
    }
    Station station = new Station(stationCode, stationName);
    this.stationMapping.put(stationCode, station);
    return station;
  }

  public Station getStation(String stationCode){
    return stationMapping.get(stationCode);
  }

}
