package com.samnoedel.lifecounter;

public class PlayerRepository {

    public static final PlayerRepository instance = new PlayerRepository();

    private Player mPlayer1;
    private Player mPlayer2;

    private PlayerRepository() {
        mPlayer1 = new Player(GamePlayer.PLAYER_ONE);
        mPlayer2 = new Player(GamePlayer.PLAYER_TWO);
    }

    public Player getPlayer1() {
        return mPlayer1;
    }

    public Player getPlayer2() {
        return mPlayer2;
    }
}
