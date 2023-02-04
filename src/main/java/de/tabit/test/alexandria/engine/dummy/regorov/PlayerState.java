package de.tabit.test.alexandria.engine.dummy.regorov;

public class PlayerState {
    private int position;
    private boolean joker;
    private boolean skipTurn;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isJoker() {
        return joker;
    }

    public void setJoker(boolean joker) {
        this.joker = joker;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }
}
