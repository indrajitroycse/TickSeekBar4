package com.warkiz.tickseekbar.utils;

import ohos.agp.components.AttrHelper;
import ohos.agp.components.Component;
import ohos.app.Context;

/**
 * Size util class.
 */
public class SizeUtils {
    private static final int DENSITY_MEDIUM = 160;
    private static final int ESTIMATED_STATE_TOO_SMALL = 0x01000000;

    private SizeUtils() {
    }

    /**
     * Sizeutils to calculate densityIndependentPixels.
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) ((int)  dpValue * AttrHelper.getDensity(context));
    }

    /**
     * Sizeutils to calculate ScaleIndependentPixels.
     */
    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * AttrHelper.getDensity((context)) / DENSITY_MEDIUM + TickSeekBarConstants.DENSITY_SCALE);
    }

    /**
     * Sizeutils to calculate pixels.
     */
    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / AttrHelper.getDensity((context)) / DENSITY_MEDIUM + TickSeekBarConstants.DENSITY_SCALE);
    }

    /**
     * Sizeutils to calculate Dimension.
     */
    public static int resolveDimension(int size, int estimatedSpec) {
        final int specMode = Component.EstimateSpec.getMode(estimatedSpec);
        final int specSize = Component.EstimateSpec.getSize(estimatedSpec);
        final int result;
        switch (specMode) {
            case Component.EstimateSpec.NOT_EXCEED:
                if (specSize < size) {
                    result = specSize | ESTIMATED_STATE_TOO_SMALL;
                } else {
                    result = specSize;
                }
                break;
            case Component.EstimateSpec.PRECISE:
                result = specSize;
                break;
            case Component.EstimateSpec.UNCONSTRAINT:
            default:
                result = size;
        }
        return result | (Component.EstimateSpec.UNCONSTRAINT & Component.EstimateSpec.ESTIMATED_STATE_BIT_MASK);
    }
}
