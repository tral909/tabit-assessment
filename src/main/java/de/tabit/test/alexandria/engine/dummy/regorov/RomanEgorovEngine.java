package de.tabit.test.alexandria.engine.dummy.regorov;

import de.tabit.test.alexandria.engine.api.IAlexandriaGameEngine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class RomanEgorovEngine implements IAlexandriaGameEngine {

    private static final int LINE_LENGTH = 30;
    private static final int BONUSES = 5;
    private static final int TRAPS = 5;
    private static final Map<Integer, PlayerState> PLAYERS_TO_POSITIONS = new HashMap<>();
    private static final Set<Integer> BONUSES_POSITIONS = new HashSet<>();
    private static final Set<Integer> TRAPS_POSITIONS = new HashSet<>();
    private static final Random RANDOM = new Random();
    private int numberOfPlayers = 0;
    private static int playerCounter = 0;

    @Override
    public String startGame(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        for (int i = 1; i <= numberOfPlayers; i++) {
            PLAYERS_TO_POSITIONS.put(i, new PlayerState());
        }

        while (BONUSES_POSITIONS.size() < BONUSES) {
            BONUSES_POSITIONS.add(generateRandomLineIndex());
        }

        while (TRAPS_POSITIONS.size() < TRAPS) {
            int rndPosition = generateRandomLineIndex();
            if (!BONUSES_POSITIONS.contains(rndPosition)) {
                TRAPS_POSITIONS.add(rndPosition);
            }
        }

        //for debug - information about bonuses and traps
        //System.out.println(Arrays.toString(BONUSES_POSITIONS.toArray()));
        //System.out.println(Arrays.toString(TRAPS_POSITIONS.toArray()));
        return String.format("Initialized game line (%d), bonuses (%d) and traps (%d) ...",
                LINE_LENGTH, BONUSES_POSITIONS.size(), TRAPS_POSITIONS.size());
    }

    @Override
    public boolean gameIsRunning() {
        List<Integer> positions = PLAYERS_TO_POSITIONS.values().stream()
                .map(PlayerState::getPosition)
                .collect(Collectors.toList());

        for (Integer position : positions) {
            if (position >= LINE_LENGTH) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String nextPlayer() {
        if (playerCounter >= this.numberOfPlayers) {
            playerCounter = 0;
        }
        return String.valueOf(++playerCounter);
    }

    @Override
    public String nextPlayerTurn(Integer input) {
        PlayerState player = PLAYERS_TO_POSITIONS.get(playerCounter);
        if (player.isSkipTurn()) {
            player.setSkipTurn(false);
            return String.format("Player %d skips turn...", playerCounter);
        }
        player.setPosition(player.getPosition() + input);
        int position = player.getPosition();

        String additionalInfo = "";
        if (BONUSES_POSITIONS.contains(position)) {
            Bonus[] bonuses = Bonus.values();
            Bonus bonus = bonuses[RANDOM.nextInt(bonuses.length)];
            additionalInfo = bonus.apply(player, PLAYERS_TO_POSITIONS);
        } else if (TRAPS_POSITIONS.contains(position)) {
            if (player.isJoker()) {
                player.setJoker(false);
                additionalInfo = "Joker protects you!";
            } else {
                Trap[] traps = Trap.values();
                Trap trap = traps[RANDOM.nextInt(traps.length)];
                additionalInfo = trap.apply(player, PLAYERS_TO_POSITIONS);
            }
        }

        position = player.getPosition();
        if (position >= LINE_LENGTH) {
            return String.format("Player %d wins!", playerCounter);
        }

        return String.format(additionalInfo + " New player's %d position is %d", playerCounter, position);
    }

    private int generateRandomLineIndex() {
        return RANDOM.nextInt(LINE_LENGTH) + 1;
    }
}
