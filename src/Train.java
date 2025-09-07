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


}
