package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainDataLoaderTest {

  private static final String VALID_RESOURCE = "test-trains-valid.properties";
  private static final String INVALID_RESOURCE = "test-trains-invalid.properties";
  private static final String MISSING_RESOURCE = "missing-trains.properties";

  private StationRegistry registry;

  @BeforeEach
  void setUp() throws Exception {
    registry = StationRegistry.getInstance();
    clearRegistry();
  }

  @Test
  void shouldLoadTrainsAndSortRoutes() throws Exception {
    List<Train> trains = TrainDataLoader.loadTrains(VALID_RESOURCE, registry);

    assertEquals(2, trains.size(), "Should load only non-empty train definitions");

    Train trainA = findTrain(trains, "TRAIN_A");
    List<Station> routeA = getRoute(trainA);
    assertEquals(3, routeA.size());
    assertEquals("AL", routeA.get(0).getStationCode()); // distance 0
    assertEquals("BT", routeA.get(1).getStationCode()); // distance 300
    assertEquals("GM", routeA.get(2).getStationCode()); // distance 600

    Train trainB = findTrain(trains, "TRAIN_B");
    List<Station> routeB = getRoute(trainB);
    assertEquals(2, routeB.size());
    assertEquals("CH", routeB.get(0).getStationCode()); // distance 50
    assertEquals("DL", routeB.get(1).getStationCode()); // distance 200
  }

  @Test
  void shouldSkipBlankRouteDefinitions() {
    List<Train> trains = TrainDataLoader.loadTrains(VALID_RESOURCE, registry);
    assertTrue(trains.stream().noneMatch(t -> "TRAIN_EMPTY".equals(t.getTrainName())),
        "Blank routes should be skipped");
  }

  @Test
  void shouldThrowWhenResourceMissing() {
    RuntimeException ex = assertThrows(RuntimeException.class,
        () -> TrainDataLoader.loadTrains(MISSING_RESOURCE, registry));
    assertTrue(ex.getMessage().contains("Failed to find train data file"),
        "Should indicate missing resource");
  }

  @Test
  void shouldThrowOnInvalidSegment() {
    assertThrows(IllegalArgumentException.class,
        () -> TrainDataLoader.loadTrains(INVALID_RESOURCE, registry));
  }

  private Train findTrain(List<Train> trains, String name) {
    return trains.stream()
        .filter(t -> name.equals(t.getTrainName()))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Train not found: " + name));
  }

  @SuppressWarnings("unchecked")
  private List<Station> getRoute(Train train) throws Exception {
    Field field = Train.class.getDeclaredField("route");
    field.setAccessible(true);
    return (List<Station>) field.get(train);
  }

  @SuppressWarnings("unchecked")
  private void clearRegistry() throws Exception {
    Field field = StationRegistry.class.getDeclaredField("stationMapping");
    field.setAccessible(true);
    Map<String, Station> map = (Map<String, Station>) field.get(registry);
    map.clear();
  }
}

