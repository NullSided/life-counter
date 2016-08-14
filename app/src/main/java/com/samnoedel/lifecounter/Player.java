package com.samnoedel.lifecounter;

import java.io.Serializable;

public class Player implements Serializable {

    private int mLifeTotal;

    private GamePlayer mGamePlayer;

    public Player(GamePlayer gamePlayer) {
        mGamePlayer = gamePlayer;
        mLifeTotal = 20;
    }

    public int getLifeTotal() {
        return mLifeTotal;
    }

    public void incrementLifeTotal() {
        mLifeTotal += 1;
    }

    public GamePlayer getGamePlayer() {
        return mGamePlayer;
    }

    public void incrementLifeTotal(int delta) {
        mLifeTotal += delta;
    }

    public void decrementLifeTotal() {
        mLifeTotal -= 1;
    }
}
