package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TrainTest {

  @Test
  void shouldStoreName() {
    Train train = new Train("TRAIN_A");
    assertEquals("TRAIN_A", train.getTrainName());
  }

  @Test
  void shouldAddRouteWithoutDuplicates() {
    Train train = new Train("TRAIN_A");
    Station chn = new Station("CHN", "Chennai");
    Station blr = new Station("BLR", "Bangalore");

    train.addRoute(chn);
    train.addRoute(chn); // duplicate
    train.addRoute(blr);

    List<Station> route = getRoute(train);
    assertEquals(2, route.size(), "Route should contain unique stations");
    assertTrue(route.contains(chn));
    assertTrue(route.contains(blr));
  }

  @SuppressWarnings("unchecked")
  private List<Station> getRoute(Train train) {
    try {
      Field field = Train.class.getDeclaredField("route");
      field.setAccessible(true);
      return (List<Station>) field.get(train);
    } catch (Exception e) {
      fail("Failed to access route via reflection: " + e.getMessage());
      return null;
    }
  }
}

