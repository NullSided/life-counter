package com.samnoedel.lifecounter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PlayerFragment extends Fragment {

    private Player mPlayer;
    private Button mMinusButton;
    private Button mPlusButton;
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
        mPlayer = new Player();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player, container, false);

        mMinusButton = (Button)v.findViewById(R.id.minus_button);
        mPlusButton = (Button)v.findViewById(R.id.plus_button);
        mLifeTotal = (TextView)v.findViewById(R.id.life_total);

        updateLifeTotal();
        wireEvents();

        return v;
    }

    private void wireEvents() {
        mPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.incrementLifeTotal();
                updateLifeTotal();
            }
        });

        mPlusButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPlayer.incrementLifeTotal(5);
                updateLifeTotal();
                return true;
            }
        });

        mMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.decrementLifeTotal();
                updateLifeTotal();
            }
        });

        mMinusButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPlayer.incrementLifeTotal(-5);
                updateLifeTotal();
                return true;
            }
        });
    }

    private void updateLifeTotal() {
        new IdleService().resetIdleTime(getActivity().getWindow());
        String text = Integer.toString(mPlayer.getLifeTotal());
        mLifeTotal.setText(text);
    }
}
