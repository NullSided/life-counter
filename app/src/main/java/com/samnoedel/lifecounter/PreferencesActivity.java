package com.samnoedel.lifecounter;

import android.app.Fragment;

public class PreferencesActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PreferencesFragment();
    }
}
