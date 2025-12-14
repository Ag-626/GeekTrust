package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppInitializerTest {

  private static final String VALID_TEST_CONFIG = "test-config-valid.properties";
  private static final String MISSING_TRAINS_CONFIG = "test-config-missing-trains.properties";
  private static final String MISSING_CONFIG = "missing-config.properties";

  private StationRegistry registry;

  @BeforeEach
  void setUp() throws Exception {
    registry = StationRegistry.getInstance();
    clearRegistry();
  }

  @Test
  void shouldLoadUsingDefaultConfig() {
    AppInitializer initializer = new AppInitializer();

    assertEquals("HYB", initializer.getProcessingStationCode());
    assertFalse(initializer.getTrains().isEmpty(), "Default config should load trains");
    assertNotNull(registry.getStation("HYB"), "Processing station should be present in registry");
  }

  @Test
  void shouldLoadUsingCustomConfig() {
    AppInitializer initializer = new AppInitializer(VALID_TEST_CONFIG);

    assertEquals("AL", initializer.getProcessingStationCode());
    assertEquals(2, initializer.getTrains().size(), "Should load trains from test config");
    assertNotNull(registry.getStation("AL"), "Custom processing station should be registered");
  }

  @Test
  void shouldThrowWhenConfigFileMissing() {
    RuntimeException ex = assertThrows(RuntimeException.class,
        () -> new AppInitializer(MISSING_CONFIG));
    assertTrue(ex.getMessage().contains("not found in classpath"));
  }

  @Test
  void shouldThrowWhenTrainDataFileMissing() {
    assertThrows(RuntimeException.class, () -> new AppInitializer(MISSING_TRAINS_CONFIG));
  }

  @SuppressWarnings("unchecked")
  private void clearRegistry() throws Exception {
    Field field = StationRegistry.class.getDeclaredField("stationMapping");
    field.setAccessible(true);
    Map<String, Station> map = (Map<String, Station>) field.get(registry);
    map.clear();
  }
}

