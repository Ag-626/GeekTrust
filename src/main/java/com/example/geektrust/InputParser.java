package com.example.geektrust;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputParser {

  private static final int TRAIN_NAME_INDEX = 0;
  private static final int ENGINE_KEYWORD_INDEX = 1;
  private static final int FIRST_BOGIE_INDEX = 2;
  private static final int MIN_PARTS_REQUIRED = 3;
  private static final String ENGINE_KEYWORD = "ENGINE";

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

    if (parts.length < MIN_PARTS_REQUIRED){
      throw new IllegalArgumentException("Invalid input line: " + line);
    }

    if (!parts[ENGINE_KEYWORD_INDEX].equalsIgnoreCase(ENGINE_KEYWORD)){
      throw new IllegalArgumentException("Expected ENGINE Keyword for: " + line);
    }

    String trainName = parts[TRAIN_NAME_INDEX];

    TrainComposition trainComposition = new TrainComposition(trainName);

    for(int i= FIRST_BOGIE_INDEX; i< parts.length; i++){
      Bogie bogie = new Bogie(parts[i]);
      trainComposition.addBogieInOrder(bogie);
    }
    return trainComposition;
  }

}

