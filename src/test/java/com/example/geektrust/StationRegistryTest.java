package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StationRegistryTest {

  private static final String CODE_CHN = "CHN";
  private static final String CODE_BLR = "BLR";

  @BeforeEach
  void clearRegistry() throws Exception {
    StationRegistry registry = StationRegistry.getInstance();
    Field field = StationRegistry.class.getDeclaredField("stationMapping");
    field.setAccessible(true);
    @SuppressWarnings("unchecked")
    Map<String, Station> map = (Map<String, Station>) field.get(registry);
    map.clear();
  }

  @Test
  void shouldReturnSameSingletonInstance() {
    StationRegistry registry1 = StationRegistry.getInstance();
    StationRegistry registry2 = StationRegistry.getInstance();

    assertSame(registry1, registry2);
  }

  @Test
  void shouldReturnSameStationForSameCode() {
    StationRegistry registry = StationRegistry.getInstance();

    Station first = registry.getOrCreateStation(CODE_CHN, "Chennai");
    Station second = registry.getOrCreateStation(CODE_CHN, "Chennai Duplicate Name");

    assertSame(first, second);
  }

  @Test
  void shouldReturnDifferentStationsForDifferentCodes() {
    StationRegistry registry = StationRegistry.getInstance();

    Station chn = registry.getOrCreateStation(CODE_CHN, "Chennai");
    Station blr = registry.getOrCreateStation(CODE_BLR, "Bangalore");

    assertNotSame(chn, blr);
  }

  @Test
  void shouldReturnNullWhenStationNotPresent() {
    StationRegistry registry = StationRegistry.getInstance();

    assertNull(registry.getStation(CODE_CHN));
  }

  @Test
  void shouldGetStationAfterCreation() {
    StationRegistry registry = StationRegistry.getInstance();
    Station created = registry.getOrCreateStation(CODE_CHN, "Chennai");

    Station fetched = registry.getStation(CODE_CHN);

    assertNotNull(fetched);
    assertSame(created, fetched);
  }
}

