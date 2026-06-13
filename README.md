# Rock :rock: Paper :page_with_curl: Scissors :scissors:
RPS Game is a simple CLI application to play Rock Paper Scissors built with Java.

## Features
- Human vs computer gameplay
- Configurable number of rounds (Default 3 rounds)
- Help command
- Unit tests for core game logic and argument parsing

## Requirements
- Java 21
- Maven 3.X

[!Note]
If your environment uses Java 21, update the `maven.compiler.release` property in `pom.xml`

## Build
```bash
mvn clean package
```

## Run
Run the game in manual rounds setup mode:
```bash
java -jar target/rock-paper-scissors.jar
```

Run the game with 5 rounds:
```bash
java -jar target/rock-paper-scissors.jar --rounds 5
```
Short option:
```bash
java -jar target/rock-paper-scissors.jar -r 5
```

Show help:
```bash
java -jar target/rock-paper-scissors.jar --help
```
Short option:
```bash
java -jav target/rock-paper-scissors.jar -h
```

## Game Config
The numbers of rounds is resolved using the following priority:
1. CLI argument: `--rounds` or `-r`
2. Interactive setup at game startup
3. Default fallback: `3`

## Move Inputs

| Move | Input |
| :--- | :---: |
| Rock | r |
| Paper | p |
| Scissors | s |

## Tests

### Run tests
```bash
mvn test
```

### Test Coverage
- Game rules
- Round and Game results
- Game configuration
- Argument parsing
- Move parsing

## Project Structure
```bash
src/
├── main
│   └── java
│       └── com
│           └── nfmdev
│               └── rpsgame
│                   ├── cli
│                   │   ├── Args.java
│                   │   ├── ArgsResolver.java
│                   │   ├── GameRunner.java
│                   │   ├── InputReader.java
│                   │   ├── MoveResolver.java
│                   │   └── OutputWriter.java
│                   ├── config
│                   │   └── GameConfig.java
│                   ├── game
│                   │   ├── machine_move_generator
│                   │   │   ├── DefaultMoveGenerator.java
│                   │   │   └── MachineMoveGenerator.java
│                   │   ├── Move.java
│                   │   ├── Result.java
│                   │   ├── Rules.java
│                   │   └── Score.java
│                   └── Rps.java
└── test
    └── java
        └── com
            └── nfmdev
                └── rpsgame
                    ├── cli
                    │   ├── ArgsResolverTest.java
                    │   └── MoveResolverTest.java
                    ├── config
                    │   └── GameConfigTest.java
                    ├── game
                    │   └── RulesTest.java
                    └── RpsTest.java
```

## Design Descisions
### Project Layers
- `game` package contains core domain login
- `cli` package handles command-line arguments, input and output handling, and console interaction
- `config` config package contains game configuration

### Error Handling
- Invalid command-line arguments produce an error message and points the user to the help command
- Errors during the interative rounds setup are handled by asking the user again until a suitable input is given
- During move selection, invalid inputs are ignored and the prompt remains active until a valid move is entered. This keeps the interaction lightweight and game-like while keeping the available options visible to the user.

### Final Classes
Most classes are declared as `final` because they are not designed for inheritance. The project keeps the design intentionally simple and focus on explicit composition over subclassing.

### GameConfig as Record
`GameConfig`is implemented as `record` because it represents immutable game configuration data.

### Move Generation Strategy
The machine move generation is represented as `interface`, with a default random implementation. The intention of this approach is to leave a clear extension point for future game modes.

## Possible Future Improvements
The submitted version intentionally keeps the scope simple and aligned with the assigment requests.

Possible future improvements:
- Terminal interface using Lanterna
- Game modes
- Persistent statistics

These features were intentionally left out of the submitted version
