package com.geektrust;

import java.util.HashMap;
import java.util.Map;

public class StationRegistry {

  private Map<String, Station> stationMapping;

  public StationRegistry(){
    this.stationMapping = new HashMap<>();
  }

  public Station getStation(String stationCode){
    return stationMapping.get(stationCode);
  }

}
