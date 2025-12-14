package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;

public class StationTest {

  @Test
  void shouldStoreAndReturnCodeAndName() {
    Station station = new Station("CHN", "Chennai");
    assertEquals("CHN", station.getStationCode());
    assertEquals("Chennai", station.getStationName());
  }

  @Test
  void shouldAddTrainOnce() {
    Station station = new Station("CHN", "Chennai");
    Train train = new Train("TRAIN_A");

    station.addTrain(train);
    station.addTrain(train); // duplicate attempt

    List<Train> trains = station.getListOfTrainsThatStop();
    assertEquals(1, trains.size(), "Train should be added only once");
    assertTrue(trains.contains(train));
  }

  @Test
  void shouldStoreDistancePerTrain() {
    Station station = new Station("CHN", "Chennai");

    station.addDistanceFromSourceByTrain("TRAIN_A", 0);
    station.addDistanceFromSourceByTrain("TRAIN_B", 1200);

    assertEquals(Integer.valueOf(0), station.getDistanceOfStationForParticularTrain("TRAIN_A"));
    assertEquals(Integer.valueOf(1200), station.getDistanceOfStationForParticularTrain("TRAIN_B"));
    assertNull(station.getDistanceOfStationForParticularTrain("UNKNOWN"), "Unknown train should return null");
  }
}

