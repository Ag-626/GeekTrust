package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BogieTest {

  @Test
  void shouldReturnBogieCode() {
    Bogie bogie = new Bogie("CHN");
    assertEquals("CHN", bogie.getBogieCode());
  }
}

