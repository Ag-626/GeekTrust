package com.geektrust;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputParser {


  public static List<TrainComposition> processInput(String inputFilePath) {
    List<TrainComposition> trainCompositions = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))){
      String line;
      while((line = br.readLine()) != null ){
        try {
          TrainComposition trainComposition = parseLine(line);
          trainCompositions.add(trainComposition);
        }catch (Exception e){
          System.err.println("Skipping Invalid line: " + line);
          continue;
        }
      }
    }catch (IOException e){
      throw new RuntimeException("Failed to read file: " + inputFilePath, e);
    }
    return trainCompositions;
  }

  private static TrainComposition parseLine(String line) {
    String[] parts = line.trim().split("\\s+");

    if (parts.length < 3){
      throw new IllegalArgumentException("Invalid input line: " + line);
    }

    if (!parts[1].equalsIgnoreCase("ENGINE")){
      throw new IllegalArgumentException("Expected ENGINE Keyword for: " + line);
    }

    String trainName = parts[0];

    TrainComposition trainComposition = new TrainComposition(trainName);

    for(int i=2;i< parts.length; i++){
      Bogie bogie = new Bogie(parts[i]);
      trainComposition.addBogieInOrder(bogie);
    }
    return trainComposition;
  }

}
