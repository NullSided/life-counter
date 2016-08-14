package com.samnoedel.lifecounter;

import java.io.Serializable;

public class Player implements Serializable {

    private int lifeTotal;

    public Player() {
        lifeTotal = 20;
    }

    public int getLifeTotal() {
        return lifeTotal;
    }

    public void incrementLifeTotal() {
        lifeTotal += 1;
    }

    public void incrementLifeTotal(int delta) {
        lifeTotal += delta;
    }

    public void decrementLifeTotal() {
        lifeTotal -= 1;
    }
}
