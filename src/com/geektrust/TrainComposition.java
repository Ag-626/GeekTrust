package com.geektrust;

import java.util.ArrayList;
import java.util.List;

public class TrainComposition {

  private String trainName;
  private List<Bogie> bogieOrder;

  public TrainComposition(String trainName){
    this.trainName=trainName;
    this.bogieOrder = new ArrayList<>();
  }

  public void addBogieInOrder(Bogie bogie){
    this.bogieOrder.add(bogie);
  }

  public String getTrainName(){
    return this.trainName;
  }

  public List<Bogie> getBogieOrder(){
    return this.bogieOrder;
  }

}
