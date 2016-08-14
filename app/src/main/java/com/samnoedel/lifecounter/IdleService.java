package com.samnoedel.lifecounter;

import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class IdleService {

    private static final float BRIGHTNESS_DIM = 0.01f;

    private static long timeout;
    private static Handler handler;
    private static Runnable runnable;
    private static float oldBrightness = 0.5f;

    public void startIdleTimer(final Window window, long timeout) {
        IdleService.timeout = timeout;

        if (handler == null) {
            handler = new Handler();
        }

        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    dimScreen(window);
                }
            };
        }

        scheduleIdleTimeout();
    }

    public void resetIdleTime(Window window) {
        handler.removeCallbacks(runnable);
        restoreScreen(window);
        scheduleIdleTimeout();
    }

    private void scheduleIdleTimeout() {
        handler.postDelayed(runnable, timeout);
    }

    private void dimScreen(Window window) {
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        oldBrightness = layoutParams.screenBrightness;
        layoutParams.screenBrightness = BRIGHTNESS_DIM;
        window.setAttributes(layoutParams);
    }

    private void restoreScreen(Window window) {
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = oldBrightness;
        window.setAttributes(layoutParams);
    }
}
