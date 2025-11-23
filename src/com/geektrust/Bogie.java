package com.geektrust;

public class Bogie {

  private String bogieCode;
  private String trainItBelong;

  public Bogie(String bogieCode, String trainItBelong){
    this.bogieCode = bogieCode;
    this.trainItBelong = trainItBelong;
  }

  public String getBogieCode(){
    return this.bogieCode;
  }

  public String getTrainItBelong(){
    return this.trainItBelong;
  }

}
