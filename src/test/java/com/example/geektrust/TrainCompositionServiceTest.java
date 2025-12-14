package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainCompositionServiceTest {

  private StationRegistry registry;

  @BeforeEach
  void setUp() throws Exception {
    registry = StationRegistry.getInstance();
    clearRegistry();
    seedRegistry();
  }

  @Test
  void filterBogiesShouldDropStationsBeforeProcessingAndKeepAfter() {
    String processingStation = "PROC";
    List<TrainComposition> input = new ArrayList<>();

    TrainComposition trainA = new TrainComposition("TRAIN_A");
    trainA.addBogieInOrder(new Bogie("PRE"));   // distance 50 -> negative diff
    trainA.addBogieInOrder(new Bogie("POST"));  // distance 150 -> positive diff
    input.add(trainA);

    TrainComposition trainB = new TrainComposition("TRAIN_B");
    trainB.addBogieInOrder(new Bogie("BPOST")); // distance 120 -> positive diff
    input.add(trainB);

    List<TrainComposition> filtered =
        TrainCompositionService.filterBogies(processingStation, registry, input);

    assertEquals(2, filtered.size());
    List<Bogie> aBogies = filtered.get(0).getBogieOrder();
    assertEquals(1, aBogies.size());
    assertEquals("POST", aBogies.get(0).getBogieCode());

    List<Bogie> bBogies = filtered.get(1).getBogieOrder();
    assertEquals(1, bBogies.size());
    assertEquals("BPOST", bBogies.get(0).getBogieCode());
  }

  @Test
  void mergeBogiesShouldOrderByDistanceDescendingFromProcessing() {
    String processingStation = "PROC";

    TrainComposition trainA = new TrainComposition("TRAIN_A");
    trainA.addBogieInOrder(new Bogie("POST"));  // diff +50

    TrainComposition trainB = new TrainComposition("TRAIN_B");
    trainB.addBogieInOrder(new Bogie("BPOST")); // diff +20

    List<TrainComposition> filtered = List.of(trainA, trainB);

    TrainComposition merged =
        TrainCompositionService.mergeBogies(processingStation, registry, filtered);

    List<Bogie> order = merged.getBogieOrder();
    assertEquals(2, order.size());
    assertEquals("POST", order.get(0).getBogieCode());  // farther first
    assertEquals("BPOST", order.get(1).getBogieCode());
  }

  @Test
  void filterBogiesShouldThrowWhenProcessingStationMissingInTrainRoute() {
    String processingStation = "PROC";
    TrainComposition trainC = new TrainComposition("TRAIN_C"); // not in registry
    trainC.addBogieInOrder(new Bogie("POST"));
    List<TrainComposition> input = List.of(trainC);

    assertThrows(RuntimeException.class,
        () -> TrainCompositionService.filterBogies(processingStation, registry, input));
  }

  // Helpers
  private void seedRegistry() {
    // Common processing station PROC at distance 100 for TRAIN_A and TRAIN_B
    register("PROC", "Processing", Map.of("TRAIN_A", 100, "TRAIN_B", 100));
    // Before processing for TRAIN_A
    register("PRE", "PreStation", Map.of("TRAIN_A", 50));
    // After processing for TRAIN_A
    register("POST", "PostStation", Map.of("TRAIN_A", 150));
    // After processing for TRAIN_B
    register("BPOST", "BPostStation", Map.of("TRAIN_B", 120));

    // Wire trains into station lists
    Train trainA = new Train("TRAIN_A");
    Train trainB = new Train("TRAIN_B");
    addTrainToStations(trainA, "PROC", "PRE", "POST");
    addTrainToStations(trainB, "PROC", "BPOST");
  }

  private void register(String code, String name, Map<String, Integer> distancesByTrain) {
    Station station = registry.getOrCreateStation(code, name);
    distancesByTrain.forEach(station::addDistanceFromSourceByTrain);
  }

  private void addTrainToStations(Train train, String... stationCodes) {
    for (String code : stationCodes) {
      Station station = registry.getStation(code);
      station.addTrain(train);
      train.addRoute(station);
    }
  }

  @SuppressWarnings("unchecked")
  private void clearRegistry() throws Exception {
    Field field = StationRegistry.class.getDeclaredField("stationMapping");
    field.setAccessible(true);
    Map<String, Station> map = (Map<String, Station>) field.get(registry);
    map.clear();
  }
}

