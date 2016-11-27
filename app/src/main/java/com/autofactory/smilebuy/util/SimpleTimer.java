package com.autofactory.smilebuy.util;

import android.os.AsyncTask;

import java.util.Calendar;

/**
 * Created by Phebe on 16. 1. 20..
 */
public class SimpleTimer {
    public interface TimerListener {
        void onTimerFinished();
        void onTimerTick(int remain);
    }

    private AsyncTask<Void, Void, Void> mTimer = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... params) {
            while(mRemain > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    cancel(true);
                    return null;
                }
                mRemain -= 1;
                publishProgress();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            if(mTimerListener != null) {
                mTimerListener.onTimerTick(mRemain);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mTimerListener != null) {
                mTimerListener.onTimerFinished();
            }
        }
    };

    private TimerListener mTimerListener = null;
    private int mDuration = 0;
    private int mRemain = 0;
    private boolean mTicking = false;
    public SimpleTimer(int duration, TimerListener timerListener) {
        mTimerListener = timerListener;
        mDuration = duration;
        mRemain = mDuration;
        mTicking = false;
        mTimer.cancel(true);
    }

    public int getDuration() {
        return mDuration;
    }
    public int getRemain() {
        return mRemain;
    }

    public void setDuration(int duration) {
        stop();
        mDuration = duration;
        mRemain = mDuration;
    }

    public void start() {
        mRemain = mDuration;
        resume();
    }

    public void stop() {
        mRemain = 0;
        pause();
    }

    public void pause() {
        mTicking = false;
        mTimer.cancel(true);
    }

    public void resume() {
        if(mRemain <= 0) {
            start();
        } else {
            mTicking = true;
            mTimer.execute();
        }
    }

    private int mSavedSecond = -1;
    public void onPause() {
        if(mTicking) {
            mSavedSecond = Calendar.getInstance().get(Calendar.SECOND);
            mTimer.cancel(true);
        }
    }
    public void onResume() {
        if(mTicking) {
            if (mSavedSecond > 0) {
                int diff = Calendar.getInstance().get(Calendar.SECOND) - mSavedSecond;
                mRemain -= diff;
                if (mRemain <= 0) {
                    mRemain = 0;
                    if (mTimerListener != null) {
                        mTimerListener.onTimerFinished();
                    }
                } else {
                    mTimer.execute();
                }
                mSavedSecond = -1;
            }
        }
    }
}
