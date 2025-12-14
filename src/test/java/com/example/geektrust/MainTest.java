package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainTest {

  private final PrintStream originalOut = System.out;
  private ByteArrayOutputStream capturedOut;

  @BeforeEach
  void setUpStreams() {
    capturedOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(capturedOut));
  }

  @AfterEach
  void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  void shouldThrowWhenInputFileIsMissing() {
    assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{}));
  }

  @Test
  void shouldRunAndPrintArrivalAndDepartureForSampleInput() {
    String inputPath = Paths.get("sample_input", "input1.txt").toString();

    Main.main(new String[]{inputPath});

    String output = capturedOut.toString();
    assertTrue(output.contains("ARRIVAL TRAIN_A ENGINE NDL NDL GHY NJP NGP"), "Output should contain ARRIVAL line");
    assertTrue(output.contains("ARRIVAL TRAIN_B ENGINE NJP GHY AGA BPL PTA"), "Output should contain ARRIVAL line");
    assertTrue(output.contains("DEPARTURE TRAIN_AB ENGINE ENGINE GHY GHY NJP NJP PTA NDL NDL AGA BPL NGP"), "Output should contain DEPARTURE line");
  }

  @Test
  void shouldMatchExactOutputForSampleInput3() {
    String inputPath = Paths.get("sample_input", "input3.txt").toString();

    Main.main(new String[]{inputPath});

    String output = capturedOut.toString().trim();
    assertTrue(output.contains("ARRIVAL TRAIN_A ENGINE HYB"), "Output should contain ARRIVAL for TRAIN_A");
    assertTrue(output.contains("ARRIVAL TRAIN_B ENGINE HYB HYB"), "Output should contain ARRIVAL for TRAIN_B");
    assertTrue(output.contains("JOURNEY_ENDED"), "Output should indicate journey ended");
  }

  @Test
  void shouldWrapFailureWhenInputFileMissing() {
    String missingPath = Paths.get("sample_input", "nonexistent.txt").toString();

    RuntimeException thrown = assertThrows(RuntimeException.class, () -> Main.main(new String[]{missingPath}));
    assertTrue(thrown.getMessage().contains("Application failed to run"), "Should wrap failure with clear message");
  }
}