package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class InputParserTest {

  private Path tempInput;

  @AfterEach
  void cleanup() throws IOException {
    if (tempInput != null) {
      Files.deleteIfExists(tempInput);
    }
  }

  @Test
  void shouldParseValidLinesAndPreserveOrder() throws IOException {
    tempInput = Files.createTempFile("input-valid", ".txt");
    Files.write(
        tempInput,
        List.of(
            "TRAIN_A ENGINE CHN SLM BLR",
            "TRAIN_B ENGINE TVC SRR"));

    List<TrainComposition> result = InputParser.processInput(tempInput.toString());

    assertEquals(2, result.size());
    TrainComposition trainA = result.get(0);
    assertEquals("TRAIN_A", trainA.getTrainName());
    assertEquals(List.of("CHN", "SLM", "BLR"),
        trainA.getBogieOrder().stream().map(Bogie::getBogieCode).collect(Collectors.toList()));

    TrainComposition trainB = result.get(1);
    assertEquals("TRAIN_B", trainB.getTrainName());
    assertEquals(List.of("TVC", "SRR"),
        trainB.getBogieOrder().stream().map(Bogie::getBogieCode).collect(Collectors.toList()));
  }

  @Test
  void shouldSkipInvalidLinesAndContinue() throws IOException {
    tempInput = Files.createTempFile("input-invalid", ".txt");
    Files.write(
        tempInput,
        List.of(
            "TRAIN_A ENGINE CHN",
            "INVALID LINE",
            "TRAIN_B ENGINE SRR MAQ"));

    List<TrainComposition> result = InputParser.processInput(tempInput.toString());

    assertEquals(2, result.size(), "Only valid lines should be parsed");
    assertEquals("TRAIN_A", result.get(0).getTrainName());
    assertEquals("TRAIN_B", result.get(1).getTrainName());
  }

  @Test
  void shouldThrowWhenFileMissing() {
    assertThrows(RuntimeException.class,
        () -> InputParser.processInput("non-existent-file.txt"));
  }
}

