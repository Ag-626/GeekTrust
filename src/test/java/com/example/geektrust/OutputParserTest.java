package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OutputParserTest {

  private final PrintStream originalOut = System.out;
  private ByteArrayOutputStream capturedOut;

  @BeforeEach
  void setUp() {
    capturedOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(capturedOut));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
  }

  @Test
  void shouldPrintArrivals() {
    TrainComposition t1 = new TrainComposition("TRAIN_A");
    t1.addBogieInOrder(new Bogie("CHN"));
    t1.addBogieInOrder(new Bogie("SLM"));
    TrainComposition t2 = new TrainComposition("TRAIN_B");
    t2.addBogieInOrder(new Bogie("HYB"));

    OutputParser.resultOnArrival(Arrays.asList(t1, t2));

    String output = capturedOut.toString().trim();
    assertEquals(
        "ARRIVAL TRAIN_A ENGINE CHN SLM\nARRIVAL TRAIN_B ENGINE HYB",
        output);
  }

  @Test
  void shouldPrintJourneyEndedWhenNoBogies() {
    TrainComposition t = new TrainComposition("TRAIN_AB");
    OutputParser.resultOnDeparture(t);

    String output = capturedOut.toString().trim();
    assertEquals("JOURNEY_ENDED", output);
  }

  @Test
  void shouldPrintDepartureWithBogies() {
    TrainComposition t = new TrainComposition("TRAIN_AB");
    t.setBogieOrder(Arrays.asList(new Bogie("NJP"), new Bogie("GHY")));

    OutputParser.resultOnDeparture(t);

    String output = capturedOut.toString().trim();
    assertEquals("DEPARTURE TRAIN_AB ENGINE ENGINE NJP GHY", output);
  }
}

