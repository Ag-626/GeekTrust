package com.geektrust;

import java.util.HashMap;
import java.util.Map;

public class StationRegistry {

  private Map<String, Station> stationMapping;

  public StationRegistry(){
    this.stationMapping = new HashMap<>();
  }

  public void addStation(String stationCode, Station station){

    if (this.stationMapping.containsKey(stationCode)){
      throw new IllegalArgumentException("Station with code " + stationCode + " already exists");
    }
    this.stationMapping.put(stationCode, station);
  }

  public Station getStation(String stationCode){
    return stationMapping.get(stationCode);
  }

}
