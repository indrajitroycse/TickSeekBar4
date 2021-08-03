package com.warkiz.tickseekbar;

import ohos.agp.utils.Color;

/**
 * For collecting each section track color.
 */
public interface ColorCollector {
    /**
     * Collect section track color.
     *
     * @param colorArr color array
     * @return boolean value
     */
    boolean collectSectionTrackColor(Color[] colorArr);
}