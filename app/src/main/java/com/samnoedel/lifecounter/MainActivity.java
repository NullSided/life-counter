package com.samnoedel.lifecounter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private IdleService mIdleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View rootView = findViewById(R.id.rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IdleService().resetIdleTime(getWindow());
            }
        });

        FragmentManager fm = getFragmentManager();
        Fragment midbarFragment = fm.findFragmentById(R.id.midbar);

        if (midbarFragment == null) {
            midbarFragment = new MidbarFragment();
            Fragment p1Fragment = new PlayerFragment();
            Fragment p2Fragment = new PlayerFragment();

            fm.beginTransaction()
                    .add(R.id.midbar, midbarFragment)
                    .add(R.id.playerOneFragmentContainer, p1Fragment)
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
