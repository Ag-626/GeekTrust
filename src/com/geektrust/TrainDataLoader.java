package com.geektrust;

import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TrainDataLoader {

  public static List<Train> loadTrains(String jsonFilePath) {
    List<Train> trains = new ArrayList<>();
    StationRegistry stationRegistry = StationRegistry.getInstance();

    try {
      String content = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
      JSONObject root = new JSONObject(content);

      // Iterate over TRAIN_A, TRAIN_B, ...
      for (String trainName : root.keySet()) {
        JSONObject routeObj = root.getJSONObject(trainName);
        Train train = new Train(trainName);

        // Collect entries (label, distance) so we can sort by distance
        List<Map.Entry<String, Integer>> entries = new ArrayList<>();

        for (String label : routeObj.keySet()) {
          int distance = routeObj.getInt(label);
          entries.add(Map.entry(label, distance));
        }

        // Sort by distance (so route is in travel order)
        entries.sort(Comparator.comparingInt(Map.Entry::getValue));

        // Process in sorted order
        for (Map.Entry<String, Integer> entry : entries) {
          String label = entry.getKey();
          int distance = entry.getValue();

          String stationCode = extractCode(label);
          String stationName = extractName(label);

          // get or create station
          Station station = stationRegistry.getOrCreateStation(stationCode, stationName);

          // wire up relationships
          train.addRoute(station);
          station.addTrain(train);
          station.addDistanceFromSourceByTrain(trainName, distance);
        }

        trains.add(train);
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to load train data", e);
    }

    return trains;
  }

  private static String extractCode(String label) {
    int open = label.lastIndexOf('(');
    int close = label.lastIndexOf(')');
    if (open == -1 || close == -1 || close <= open) {
      throw new IllegalArgumentException("Invalid station label: " + label);
    }
    return label.substring(open + 1, close).trim();
  }

  private static String extractName(String label) {
    int open = label.lastIndexOf('(');
    if (open == -1) {
      return label.trim();
    }
    return label.substring(0, open).trim();
  }
}