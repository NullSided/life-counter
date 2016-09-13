package com.samnoedel.lifecounter;

import java.util.Date;

public class GameTimeRepository {

    public static final GameTimeRepository instance = new GameTimeRepository();

    private long mGameStartedAt;
    private long mGameTime;
    private long mPlayer1ElapsedTime;
    private long mPlayer2ElapsedTime;
    private GamePlayer mCurrentPlayer;
    private long mCurrentSessionStart;
    private GameTimeMode mGameTimeMode;

    private GameTimeRepository() { }

    public void startGame(GamePlayer gamePlayer, GameTimeMode timeMode, long gameTime) {
        long now = new Date().getTime();

        mGameTimeMode = timeMode;
        mGameTime = gameTime;
        mCurrentPlayer = gamePlayer;
        mCurrentSessionStart = now;
        mGameStartedAt = now;
        mPlayer1ElapsedTime = 0;
        mPlayer2ElapsedTime = 0;
    }

    public void passTurn() {
        long now = new Date().getTime();
        long deltaTime = now - mCurrentSessionStart;

        mCurrentSessionStart = now;

        switch (mCurrentPlayer) {
            case PLAYER_ONE:
                mPlayer1ElapsedTime += deltaTime;
                mCurrentPlayer = GamePlayer.PLAYER_TWO;
                break;
            case PLAYER_TWO:
                mPlayer2ElapsedTime += deltaTime;
                mCurrentPlayer = GamePlayer.PLAYER_ONE;
                break;
        }
    }

    public GamePlayer getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public long getRemainingTime(GamePlayer gamePlayer) {
        switch (mGameTimeMode) {
            case MODE_GLOBAL:
                return mGameStartedAt + mGameTime - new Date().getTime();
            case MODE_PLAYER:
                return getPlayerElapsedTime(gamePlayer);
            case MODE_NONE:
            default:
                return 0;
        }
    }

    private long getPlayerElapsedTime(GamePlayer gamePlayer) {
        long currentSessionDelta = new Date().getTime() - mCurrentSessionStart;

        if (gamePlayer == GamePlayer.PLAYER_ONE) {
            if (mCurrentPlayer == GamePlayer.PLAYER_ONE) {
                return mGameTime - mPlayer1ElapsedTime - currentSessionDelta;
            }
            return mGameTime - mPlayer1ElapsedTime;
        } else {
            if (mCurrentPlayer == GamePlayer.PLAYER_TWO) {
                return mGameTime - mPlayer2ElapsedTime - currentSessionDelta;
            }
            return mGameTime - mPlayer2ElapsedTime;
        }
    }
}
