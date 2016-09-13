package com.samnoedel.lifecounter;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;

public class RepeatListener implements View.OnTouchListener {

    private Handler handler = new Handler();
    private View downView;
    private int mRepeatInterval;
    private long mLastTouch;
    private final View.OnClickListener mClickListener;
    private final View.OnClickListener mRepeatListener;

    private Runnable handlerRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, mRepeatInterval);
            mRepeatListener.onClick(downView);
        }
    };

    public RepeatListener(int repeatInterval,
                          View.OnClickListener clickListener,
                          View.OnClickListener repeatClickListener) {
        if (clickListener == null)
            throw new IllegalArgumentException("null runnable");
        if (repeatInterval < 0)
            throw new IllegalArgumentException("negative interval");

        this.mRepeatInterval = repeatInterval;
        this.mClickListener = clickListener;
        this.mRepeatListener = repeatClickListener;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastTouch = getTicks();
                handler.removeCallbacks(handlerRunnable);
                handler.postDelayed(handlerRunnable, mRepeatInterval);
                downView = view;
                downView.setPressed(true);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handler.removeCallbacks(handlerRunnable);
                downView.setPressed(false);
                downView = null;

                if (getTicks() < mLastTouch + mRepeatInterval) {
                    mClickListener.onClick(view);
                    mLastTouch = 0;
                }

                return true;
        }

        return false;
    }

    private long getTicks() {
        return new Date().getTime();
    }
}
