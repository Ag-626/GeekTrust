package com.geektrust;

import java.util.List;

public class OutputParser {

  public static void resultOnArrival(List<TrainComposition> trainCompositions){
    for(TrainComposition trainComposition: trainCompositions){
      System.out.print("ARRIVAL " + trainComposition.getTrainName() + " ENGINE");

      for(Bogie b: trainComposition.getBogieOrder()){
        System.out.print(" " + b.getBogieCode());
      }
      System.out.println();
    }
  }

  public static void resultOnDeparture(TrainComposition trainComposition){
    if(trainComposition.getBogieOrder().size()==0){
      System.out.println("JOURNEY_ENDED");
      return;
    }
    System.out.print("DEPARTURE " + trainComposition.getTrainName() + " ENGINE ENGINE");

    for(Bogie b: trainComposition.getBogieOrder()){
      System.out.print(" " + b.getBogieCode());
    }
    System.out.println();
  }
}
