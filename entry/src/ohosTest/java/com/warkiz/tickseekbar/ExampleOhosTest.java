/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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