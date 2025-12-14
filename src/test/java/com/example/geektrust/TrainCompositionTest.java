package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TrainCompositionTest {

  @Test
  void shouldStoreTrainName() {
    TrainComposition comp = new TrainComposition("TRAIN_A");
    assertEquals("TRAIN_A", comp.getTrainName());
  }

  @Test
  void shouldAddBogiesInOrder() {
    TrainComposition comp = new TrainComposition("TRAIN_A");
    Bogie b1 = new Bogie("CHN");
    Bogie b2 = new Bogie("BLR");

    comp.addBogieInOrder(b1);
    comp.addBogieInOrder(b2);

    List<Bogie> bogies = comp.getBogieOrder();
    assertEquals(2, bogies.size());
    assertTrue(bogies.get(0) == b1 && bogies.get(1) == b2, "Bogies should preserve insertion order");
  }

  @Test
  void shouldSetBogieOrder() {
    TrainComposition comp = new TrainComposition("TRAIN_A");
    List<Bogie> newOrder = new ArrayList<>();
    Bogie b1 = new Bogie("NGP");
    Bogie b2 = new Bogie("AGA");
    newOrder.add(b1);
    newOrder.add(b2);

    comp.setBogieOrder(newOrder);

    List<Bogie> bogies = comp.getBogieOrder();
    assertEquals(2, bogies.size());
    assertTrue(bogies.get(0) == b1 && bogies.get(1) == b2, "Bogie order should be replaced");
  }
}

