package com.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Loads initial train route data from a .properties file.
 *
 * Expected format (trains.properties):
 *
 * TRAIN_A=CHENNAI (CHN):0,SALEM (SLM):350,...
 * TRAIN_B=TRIVANDRUM (TVC):0,SHORANUR (SRR):300,...
 */
public class TrainDataLoader {

  public static List<Train> loadTrains(String propertiesFilePath, StationRegistry stationRegistry) {
    List<Train> trains = new ArrayList<>();
    Properties props = new Properties();

    try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
      props.load(fis);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load train data from: " + propertiesFilePath, e);
    }

    // For each train key: TRAIN_A, TRAIN_B, ...
    for (String trainName : props.stringPropertyNames()) {
      String routeDefinition = props.getProperty(trainName);
      if (routeDefinition == null || routeDefinition.isBlank()) {
        continue;
      }

      Train train = new Train(trainName);

      // Split into segments: "CHENNAI (CHN):0", "SALEM (SLM):350", ...
      String[] segments = routeDefinition.split(",");

      // Collect (label, distance) to sort by distance, just in case order in properties changes
      List<Map.Entry<String, Integer>> entries = new ArrayList<>();

      for (String segment : segments) {
        segment = segment.trim();
        if (segment.isEmpty()) continue;

        String[] parts = segment.split(":");
        if (parts.length != 2) {
          throw new IllegalArgumentException("Invalid segment for " + trainName + ": " + segment);
        }

        String label = parts[0].trim();      // "CHENNAI (CHN)"
        int distance = Integer.parseInt(parts[1].trim()); // 0, 350, ...

        entries.add(new AbstractMap.SimpleEntry<>(label, distance));
      }

      // Sort by distance so that route in Train is in travel order
      entries.sort(Comparator.comparingInt(Map.Entry::getValue));

      // Build stations and wire relationships
      for (Map.Entry<String, Integer> entry : entries) {
        String label = entry.getKey();
        int distance = entry.getValue();

        String stationCode = extractCode(label); // "CHN"
        String stationName = extractName(label); // "CHENNAI"

        // One global registry instance ensures we reuse Station objects
        Station station = stationRegistry.getOrCreateStation(stationCode, stationName);

        // Wire up both sides
        train.addRoute(station); // make sure Train has this method
        station.addTrain(train);
        station.addDistanceFromSourceByTrain(trainName, distance);
      }

      trains.add(train);
    }

    return trains;
  }

  // "CHENNAI (CHN)" -> "CHN"
  private static String extractCode(String label) {
    int open = label.lastIndexOf('(');
    int close = label.lastIndexOf(')');
    if (open == -1 || close == -1 || close <= open) {
      throw new IllegalArgumentException("Invalid station label: " + label);
    }
    return label.substring(open + 1, close).trim();
  }

  // "CHENNAI (CHN)" -> "CHENNAI"
  private static String extractName(String label) {
    int open = label.lastIndexOf('(');
    if (open == -1) {
      return label.trim();
    }
    return label.substring(0, open).trim();
  }
}