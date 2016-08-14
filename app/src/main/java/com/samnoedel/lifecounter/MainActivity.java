package com.samnoedel.lifecounter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class MainActivity extends Activity {

    private View mRootView;
    private IdleService mIdleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootView = findViewById(R.id.rootView);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IdleService().resetIdleTime(getWindow());
            }
        });

        FragmentManager fm = getFragmentManager();
        Fragment p1Fragment = fm.findFragmentById(R.id.playerOneFragmentContainer);
        Fragment p2Fragment = fm.findFragmentById(R.id.playerTwoFragmentContainer);

        if (p1Fragment == null) {
            p1Fragment = new PlayerFragment();
            fm.beginTransaction()
                    .add(R.id.playerOneFragmentContainer, p1Fragment)
                    .commit();
        }

        if (p2Fragment == null) {
            p2Fragment = new PlayerFragment();
            fm.beginTransaction()
                    .add(R.id.playerTwoFragmentContainer, p2Fragment)
                    .commit();
        }

        mIdleService = new IdleService();
        mIdleService.startIdleTimer(getWindow(), 5000);
    }

    @Override
    public void onResume() {
        super.onResume();
        mIdleService.resetIdleTime(getWindow());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}
