import java.util.*;

public class Main {
  private static ArrayList<String> splitString(String s) {
    return new ArrayList<>(Arrays.asList(s.trim().split("\\s+")));
  }

  public static void main(String[] args) {
    System.out.println("Hello world!");

    ArrayList<Station> routeA = new ArrayList<>();
    routeA.add(new Station("CHENNAI","CHN", 0));
    routeA.add(new Station("SALEM","SLM", 350));
    routeA.add(new Station("BANGALORE", "BLR", 550));
    routeA.add(new Station("KURNOOL", "KRN", 900));
    routeA.add(new Station("HYDERABAD", "HYB", 1200));
    routeA.add(new Station("NAGPUR", "NGP", 1600));
    routeA.add(new Station("ITARSI","ITJ", 2000));
    routeA.add(new Station("BHOPAL", "BPL", 2000));
    routeA.add(new Station("AGRA", "AGA", 2500));
    routeA.add(new Station("NEW DELHI", "NDL", 2700));

    ArrayList<Station> routeB = new ArrayList<>();
    routeB.add(new Station("TRIVANDRUM","TVC", 0));
    routeB.add(new Station("SHORANUR","SRR", 300));
    routeB.add(new Station("MANGALORE", "MAQ", 600));
    routeB.add(new Station("MADGAON", "MAO", 1000));
    routeB.add(new Station("PUNE", "PNE", 1400));
    routeB.add(new Station("HYDERABAD", "HYB", 2000));
    routeB.add(new Station("NAGPUR","NGP", 2400));
    routeB.add(new Station("ITARSI", "ITJ", 2700)); // fixed spelling
    routeB.add(new Station("BHOPAL", "BPL", 2800));
    routeB.add(new Station("PATNA", "PTA", 3800));
    routeB.add(new Station("NEW JALPAIGURI", "NJP", 4200));
    routeB.add(new Station("GUWAHATI", "GHY", 4700));

    Scanner in = new Scanner(System.in); // one scanner for the whole program

    // Read two lines
    String inputA = in.nextLine(); // e.g. ENGINE KRN NGP NJP NDL BLR
    String inputB = in.nextLine(); // second line for train B

    // Build trains
    Train trainA = new Train(
        new Station("CHENNAI","CHN",0),
        new Station("NEW DELHI","NDL", 2700),
        routeA,
        splitString(inputA));

    Train trainB = new Train(
        new Station("TRIVANDRUM","TVC",0),
        new Station("GUWAHATI","GHY", 4700),
        routeB,
        splitString(inputB));

    System.out.println("The source station name from train A " + trainA.getSourceStation().getStationName());
    System.out.println("The bogieOrder for train A " + trainA.getBoggieOrder().get(3));

    System.out.println("The source station name from train B " + trainB.getSourceStation().getStationName());
    System.out.println("The bogieOrder for train B " + trainB.getBoggieOrder().get(3));

    trainA = Train.boggiesLeft(trainA, "HYB");
    int szeA =trainA.getBoggieOrder().size();
    for(int i=0;i<szeA;i++){
      System.out.println("The "+(i+1)+" boggie "+trainA.getBoggieOrder().get(i));
    }

    trainB = Train.boggiesLeft(trainB, "HYB");
    int szeB =trainB.getBoggieOrder().size();
    for(int i=0;i<szeB;i++){
      System.out.println("The "+(i+1)+" boggie "+trainB.getBoggieOrder().get(i));
    }
  }
}
