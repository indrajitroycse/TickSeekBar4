package com.warkiz.tickseekbar;

/**
 * Tick marks type class.
 */

public class TickMarkType {
    /**
     * don't show the tickMarks.
     */
    public static final int NONE = 0;
    /**
     * show tickMarks shape as regular oval.
     */
    public static final int OVAL = 1;
    /**
     * show tickMarks shape as regular square.
     */
    public static final int SQUARE = 2;
    /**
     * show tickMarks shape as vertical line , line'size is 2 dp.
     */
    public static final int DIVIDER = 3;

    private TickMarkType() {
    }
}