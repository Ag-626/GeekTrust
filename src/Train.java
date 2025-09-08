import java.util.ArrayList;

public class Train {

  private Station sourceStation;
  private Station destinationStation;
  private ArrayList<Station> route;
  private ArrayList<String> boggieOrder;

  public Train(Station sourceStation, Station destinationStation, ArrayList<Station> route, ArrayList<String> boggieOrder){
    this.sourceStation = sourceStation;
    this.destinationStation = destinationStation;
    this.route = route;
    this.boggieOrder = boggieOrder;
  }

  public static Train boggiesLeft(Train train, String stationCode){
    int idx = indexOfStationCode(train, stationCode);

    if(idx>=0){
      for(int i=0;i<idx;i++){
        Station stop = train.getRoute().get(i);
        String codeToRemove = stop.getStationCode();         // e.g., "HYB"
        train.getBoggieOrder().removeIf(
            token -> token.equalsIgnoreCase(codeToRemove) && !"ENGINE".equalsIgnoreCase(token)
        );
      }
    }

    return train;
  }
  public Station getSourceStation() {
    return sourceStation;
  }

  public void setSourceStation(Station sourceStation) {
    this.sourceStation = sourceStation;
  }

  public Station getDestinationStation() {
    return destinationStation;
  }

  public void setDestinationStation(Station destinationStation) {
    this.destinationStation = destinationStation;
  }

  public ArrayList<Station> getRoute() {
    return route;
  }

  public void setRoute(ArrayList<Station> route) {
    this.route = route;
  }

  public ArrayList<String> getBoggieOrder() {
    return boggieOrder;
  }

  public void setBoggieOrder(ArrayList<String> boggieOrder) {
    this.boggieOrder = boggieOrder;
  }

private static int indexOfStationCode(Train train, String stationCode){
    int sze = train.getRoute().size();
    int i=0;
    while(i<sze && !(train.getRoute().get(i).getStationCode().equalsIgnoreCase(stationCode))){
      i++;
    }
    if(i==sze)
      i=-1;
    return i;
}


}
