package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {

  private final PrintStream originalOut = System.out;
  private ByteArrayOutputStream capturedOut;

  @BeforeEach
  void setUp() throws Exception {
    capturedOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(capturedOut));
    clearRegistry();
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
  }

  @Test
  void shouldProduceExpectedOutputForSampleInput1() {
    App app = new App("config.properties");
    String inputPath = Paths.get("sample_input", "input1.txt").toString();

    app.run(inputPath);

    String output = capturedOut.toString();
    assertTrue(output.contains("ARRIVAL TRAIN_A ENGINE NDL NDL GHY NJP NGP"), "ARRIVAL TRAIN_A should match");
    assertTrue(output.contains("ARRIVAL TRAIN_B ENGINE NJP GHY AGA BPL PTA"), "ARRIVAL TRAIN_B should match");
    assertTrue(output.contains("DEPARTURE TRAIN_AB ENGINE ENGINE GHY GHY NJP NJP PTA NDL NDL AGA BPL NGP"),
        "DEPARTURE should match");
  }

  @Test
  void shouldProduceExpectedOutputForSampleInput3() {
    App app = new App("config.properties");
    String inputPath = Paths.get("sample_input", "input3.txt").toString();

    app.run(inputPath);

    String output = capturedOut.toString();
    assertTrue(output.contains("ARRIVAL TRAIN_A ENGINE HYB"), "ARRIVAL TRAIN_A should match");
    assertTrue(output.contains("ARRIVAL TRAIN_B ENGINE HYB HYB"), "ARRIVAL TRAIN_B should match");
    assertTrue(output.contains("JOURNEY_ENDED"), "Journey should end for sample input3");
  }

  @Test
  void shouldThrowWhenInputFileMissing() {
    App app = new App("config.properties");
    String missingPath = Paths.get("sample_input", "missing.txt").toString();

    assertThrows(RuntimeException.class, () -> app.run(missingPath));
  }

  @Test
  void shouldWrapExceptionWhenFilterBogiesFails() throws Exception {
    App app = new App("config.properties");
    Path tempInput = Files.createTempFile("bad-input", ".txt");
    try {
      Files.write(tempInput, Arrays.asList("TRAIN_A ENGINE XYZ"));

      RuntimeException thrown = assertThrows(RuntimeException.class, () -> app.run(tempInput.toString()));
      assertTrue(thrown.getMessage().contains("trainComposition on Arrival"),
          "Should wrap failure from filterBogies");
    } finally {
      Files.deleteIfExists(tempInput);
    }
  }

  @SuppressWarnings("unchecked")
  private void clearRegistry() throws Exception {
    StationRegistry registry = StationRegistry.getInstance();
    Field field = StationRegistry.class.getDeclaredField("stationMapping");
    field.setAccessible(true);
    Map<String, Station> map = (Map<String, Station>) field.get(registry);
    map.clear();
  }
}

