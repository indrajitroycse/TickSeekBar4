package com.warkiz.tickseekbar;

import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.utils.Color;
import ohos.app.Context;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * ExampleOhosTest  for TickSeekBar.
 */
public class ExampleOhosTest {
    Context context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
    TickSeekBar tickSeekBar = new TickSeekBar(context);

    /**
     * testBundleName() for testing Bundle Name.
     */
    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("com.warkiz.tickseekbar", actualBundleName);
    }

    /**
     * testDrawTickTexts() for testing DrawTickTexts.
     */
    @Test
    public void testDrawTickTexts() {
        tickSeekBar.setDrawTickTexts(Color.BLACK);
        assertEquals(Color.BLACK, tickSeekBar.getDrawTickTextsColor());
    }

    /**
     * testDrawTickMarks() for testing DrawTickMarks.
     */
    @Test
    public void testDrawTickMarks() {
        tickSeekBar.setDrawTickMarksColor(Color.BLACK);
        assertEquals(Color.BLACK, tickSeekBar.getDrawTickMarksColor());
    }

    /**
     * testDrawTrack() for testing  DrawTrack.
     */
    @Test
    public void testDrawTrack() {
        tickSeekBar.setDrawTrackColor(Color.BLACK);
        assertEquals(Color.BLACK, tickSeekBar.getDrawTrackColor());
    }

    /**
     * testDrawThumbTextColor() for testing DrawThumbText.
     */
    @Test
    public void testDrawThumbTextColor() {
        tickSeekBar.setDrawThumbTextColor(Color.BLACK);
        assertEquals(Color.BLACK, tickSeekBar.getDrawThumbTextColor());
    }

    /**
     * testDrawThumbTextColor() for testing DrawThumb.
     */
    @Test
    public void testDrawThumb() {
        tickSeekBar.setDrawThumb(Color.BLACK);
        assertEquals(Color.BLACK, tickSeekBar.getDrawThumbColor());
    }
}