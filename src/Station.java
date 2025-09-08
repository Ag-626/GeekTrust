public class Station {

  private String stationName;
  private String stationCode;
  private int distanceFromSource;

  public Station(String stationName, String stationCode, int distanceFromSource){
    this.stationName=stationName;
    this.stationCode=stationCode;
    this.distanceFromSource=distanceFromSource;
  }

  public void setStationName(String stationName){
    this.stationName=stationName;
  }

  public void setStationCode(String stationCode){
    this.stationCode=stationCode;
  }

  public void setDistanceFromSource(int distanceFromSource){
    this.distanceFromSource=distanceFromSource;
  }


  public String getStationName(){
    return this.stationName;
  }

  public String getStationCode(){
    return this.stationCode;
  }

  public int getDistanceFromSource(){
    return this.distanceFromSource;
  }
}
