package com.samnoedel.lifecounter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PlayerFragment extends Fragment {

    private static final String TAG = PlayerFragment.class.getName();

    private Player mPlayer;
    private TextView mMinusButton;
    private TextView mPlusButton;
    private TextView mLifeTotal;

    public PlayerFragment() {
        // Required empty public constructor
    }

    public static PlayerFragment newInstance() {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayer = new Player(GamePlayer.PLAYER_ONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player, container, false);

        mMinusButton = (TextView)v.findViewById(R.id.minus_button);
        mPlusButton = (TextView)v.findViewById(R.id.plus_button);
        mLifeTotal = (TextView)v.findViewById(R.id.life_total);

        updateLifeTotal();
        wireEvents();

        return v;
    }

    private void wireEvents() {
        mPlusButton.setOnTouchListener(new RepeatListener(500, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.incrementLifeTotal();
                updateLifeTotal();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.incrementLifeTotal(5);
                updateLifeTotal();
            }
        }));

        mMinusButton.setOnTouchListener(new RepeatListener(500, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.decrementLifeTotal();
                updateLifeTotal();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.incrementLifeTotal(-5);
                updateLifeTotal();
            }
        }));
    }

    private void updateLifeTotal() {
        String text = Integer.toString(mPlayer.getLifeTotal());
        mLifeTotal.setText(text);
    }
}
