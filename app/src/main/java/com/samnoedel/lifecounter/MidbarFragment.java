package com.samnoedel.lifecounter;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MidbarFragment extends Fragment {

    private static final long TICKS_ONE_MINUTE = 60000;

    private TextView mPlayer1Clock;
    private TextView mPlayer2Clock;
    private Handler mHandler;
    private GameTimeRepository mGameTimeRepo;
    private ImageView mPassTurnButton;
    private StringBuilder mStringBuilder;

    public MidbarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
        mStringBuilder = new StringBuilder();
        mGameTimeRepo = GameTimeRepository.instance;
        mGameTimeRepo.startGame(GamePlayer.PLAYER_ONE, GameTimeMode.MODE_PLAYER, TICKS_ONE_MINUTE * 15);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_midbar, container, false);

        mPlayer1Clock = (TextView)v.findViewById(R.id.player1Clock);
        mPlayer2Clock = (TextView)v.findViewById(R.id.player2Clock);
        mPassTurnButton = (ImageView)v.findViewById(R.id.passTurn);

        wireEvents();
        scheduleTimeUpdate();

        return v;
    }

    private void wireEvents() {
        mPassTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGameTimeRepo.passTurn();
            }
        });
    }

    private void scheduleTimeUpdate() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long p1Time = mGameTimeRepo.getRemainingTime(GamePlayer.PLAYER_ONE);
                long p2Time = mGameTimeRepo.getRemainingTime(GamePlayer.PLAYER_TWO);

                if (p1Time <= 0 || p2Time <= 0) {
                    return;
                }
                
                mPlayer1Clock.setText(formatTicks(p1Time));
                mPlayer2Clock.setText(formatTicks(p2Time));
                scheduleTimeUpdate();
            }
        }, 1000);
    }

    private String formatTicks(long ticks) {
        long minutes = ticks / TICKS_ONE_MINUTE;
        long seconds = ticks / 1000 - minutes * 60;
        return String.format("%1$02d:%2$02d", minutes, seconds);
    }
}
