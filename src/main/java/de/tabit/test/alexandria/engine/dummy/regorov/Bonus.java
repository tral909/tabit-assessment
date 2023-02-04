package de.tabit.test.alexandria.engine.dummy.regorov;

import java.util.Map;

public enum Bonus {
    FORWARD_2 {
        public String apply(PlayerState current, Map<Integer, PlayerState> players) {
            current.setPosition(current.getPosition() + 2);
            return "It's a bonus: 2 steps forward for you!";
        }
    },
    OPPONENTS_BACK_2 {
        public String apply(PlayerState current, Map<Integer, PlayerState> players) {
            int currentPrev = current.getPosition();
            players.values().forEach(ps -> {
                if (ps.getPosition() >= 2) {
                    ps.setPosition(ps.getPosition() - 2);
                } else {
                    ps.setPosition(0);
                }
            });
            current.setPosition(currentPrev);
            return "It's a bonus: 2 steps back for your opponents!";
        }
    },
    JOKER {
        public String apply(PlayerState current, Map<Integer, PlayerState> players) {
            current.setJoker(true);
            return "It's a bonus: you get joker - protection from next trap!";
        }
    };

    public abstract String apply(PlayerState current, Map<Integer, PlayerState> players);
}
