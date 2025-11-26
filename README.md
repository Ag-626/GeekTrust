# GeekTrust – Train Problem (Java)

This project is a solution to the **Train Problem** from **GeekTrust**.  
The solution reads input commands from a file, processes train bogies based on the problem rules, and prints the output to standard output.

## Requirements

- Java 11 or above  
- No external libraries or frameworks are used  
- Pure Java source + property files

## Project Structure

```
.
├── src/
│   └── com/
│       └── geektrust/
│            ├── Main.java
│            ├── AppInitializer.java
│            ├── InputParser.java
│            ├── OutputParser.java
│            ├── TrainService.java
│            ├── TrainComposition.java
│            ├── Bogie.java
│            ├── Station.java
│            ├── StationRegistry.java
│            └── (any other .java files)
│
├── resources/
│     ├── config.properties
│     ├── trains.properties
│     ├── input1.txt
│     └── input2.txt
│
└── README.md
```

## Configuration

### resources/config.properties

```
trains.data.path=resources/trains.properties
processing.station.code=HYB
```

## How to Compile

From the project root directory:

```
javac -cp src src/com/geektrust/*.java
```

If you have subpackages:

```
javac -cp src src/com/geektrust/*.java src/com/geektrust/init/*.java src/com/geektrust/service/*.java
```

## How to Run

```
java -cp src com.geektrust.Geektrust <path_to_input_file>
```

Example:

```
java -cp src com.geektrust.Geektrust resources/input1.txt
```

## Input Format

```
TRAIN_A ENGINE <BOGIES...>
TRAIN_B ENGINE <BOGIES...>
```

## Output Format

- ARRIVAL output for both trains  
- DEPARTURE output for merged train  
- JOURNEY_ENDED if no bogies after HYB

## Sample Output

```
ARRIVAL TRAIN_A ENGINE
ARRIVAL TRAIN_B ENGINE
JOURNEY_ENDED
```


