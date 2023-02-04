package de.tabit.test.alexandria.simulation;

import de.tabit.test.alexandria.engine.api.IAlexandriaGameEngine;
import de.tabit.test.alexandria.engine.dummy.DummyEngine;
import de.tabit.test.alexandria.engine.dummy.regorov.RomanEgorovEngine;

import java.io.*;

import static java.lang.String.format;

public class AlexandriaSimulation {

    private static final Boolean USE_DUMMY_ENGINE = Boolean.FALSE;

    public static void main(String[] args) {
        IAlexandriaGameEngine gameEngine = locateGameEngine();
        startGame(gameEngine);

        while(gameEngine.gameIsRunning()) {
            nextTurn(gameEngine);
        }

        System.out.println("Congratulations, the game has ended. Please play again soon...");
    }

    private static void startGame(IAlexandriaGameEngine gameEngine) {
        System.out.println("The game is starting...");
        System.out.print("Please enter the number of players (2-4):");
        Integer input = null;
        while(true) {
            input = userInput();
            if (input != null && input >= 2 && input <= 4) {
                break;
            } else {
                System.err.println("Please enter a number between 2 and 4.");
            }
        }
        String gameEngineInformation = gameEngine.startGame(input);
        System.out.println(gameEngineInformation);
    }

    private static void nextTurn(IAlexandriaGameEngine gameEngine) {
        System.out.print(format("The next player is %s. Please enter the dice number: ", gameEngine.nextPlayer()));
        Integer input = null;
        while(true) {
            input = userInput();
            if (input != null && input >= 1 && input <= 6) {
                break;
            } else {
                System.err.println("Please enter a number between 1 and 6.");
            }
        }
        String gameEngineInformation = gameEngine.nextPlayerTurn(input);
        System.out.println(gameEngineInformation);
    }

    private static IAlexandriaGameEngine locateGameEngine() {
        if (USE_DUMMY_ENGINE) {
            return new DummyEngine();
        }
        return new RomanEgorovEngine();
    }

    private static Integer userInput()
    {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            Integer value = Integer.valueOf(line);
            return value;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Invalid user input. Please try again");
            return null;
        }
    }
}
