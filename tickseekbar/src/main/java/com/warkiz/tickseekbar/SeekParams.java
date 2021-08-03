package com.warkiz.tickseekbar;

/**
 * save the params when the seek bar is seeking.
 */
public class SeekParams {
    /**
     * for continuous series seek bar.
     * The SeekBar whose progress has changed.
     */
    private TickSeekBar mSeekBar;

    /**
     * The current progress level.The default value for min is 0, max is 100.
     */
    private int mProgress;

    /**
     * The current progress level.The default value for min is 0.0, max is 100.0.
     */
    private float mProgressFloat;

    /**
     *True if the progress change was initiated by the user, otherwise by setProgress() programmatically.
     */
    private boolean isFromUser;

    /**
     *for discrete series seek bar
     *the thumb location on tick when the section changed, continuous series will be zero.
     */
    private int mThumbPosition;

    /**
     *the text below tick&thumb when the section changed.
     */
    private String mTickText;

    /**
     * Seek Param constructor.
     *
     * @param seekBar seek bar
     */
    public SeekParams(TickSeekBar seekBar) {
        this.mSeekBar = seekBar;
    }

    public void setSeekBar(TickSeekBar seekBar) {
        this.mSeekBar = seekBar;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }

    public void setProgressFloat(float progressFloat) {
        this.mProgressFloat = progressFloat;
    }

    public void setFromUser(boolean fromUser) {
        this.isFromUser = fromUser;
    }

    public void setThumbPosition(int thumbPosition) {
        this.mThumbPosition = thumbPosition;
    }

    public void setTickText(String tickText) {
        this.mTickText = tickText;
    }

    public TickSeekBar getSeekBar() {
        return mSeekBar;
    }

    public int getProgress() {
        return mProgress;
    }

    public float getProgressFloat() {
        return mProgressFloat;
    }

    public boolean isFromUser() {
        return isFromUser;
    }

    public int getThumbPosition() {
        return mThumbPosition;
    }

    public String getTickText() {
        return mTickText;
    }
}
