package de.tabit.test.alexandria.engine.dummy.regorov;

import java.util.Map;

public enum Trap {
    BACK_2 {
        public String apply(PlayerState current, Map<Integer, PlayerState> players) {
            if (current.getPosition() >= 2) {
                current.setPosition(current.getPosition() - 2);
            } else {
                current.setPosition(0);
            }

            return "It's a trap: 2 steps back for you!";
        }
    },
    OPPONENTS_FORWARD_2 {
        public String apply(PlayerState current, Map<Integer, PlayerState> players) {
            players.values().forEach(ps -> ps.setPosition(ps.getPosition() + 2));
            current.setPosition(current.getPosition() - 2);
            return "It's a trap: 2 steps forward for your opponents!";
        }
    },
    SKIP {
        public String apply(PlayerState current, Map<Integer, PlayerState> players) {
            current.setSkipTurn(true);
            return "It's a trap: you skip next turn!";
        }
    };

    public abstract String apply(PlayerState current, Map<Integer, PlayerState> players);
}
