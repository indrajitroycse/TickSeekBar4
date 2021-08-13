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

package com.warkiz.tickseekbar.utils;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentState;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.components.element.StateElement;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import ohos.global.systemres.ResourceTable;

import java.io.IOException;

/**
 * State element util class.
 */
public class StateElementUtil {
    private static final String TAG = StateElementUtil.class.getSimpleName();

    private StateElementUtil() {
    }

    /**
     * initialize tick marks drawable state list.
     *
     * @param context context
     * @return drawable state element
     */
    public static StateElement initTickMarksDrawableState(Context context) {
        StateElement drawableStateElement = new StateElement();
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_FOCUSED},
                new PixelMapElement(PixelMapUtil.getPixelMapFromResourceId(context, ResourceTable.Media_ic_app, TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT)));
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_SELECTED},
                new PixelMapElement(PixelMapUtil.getPixelMapFromResourceId(context, ResourceTable.Media_ic_app, TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT)));
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_EMPTY},
                new PixelMapElement(PixelMapUtil.getPixelMapFromResourceId(context, com.warkiz.tickseekbar.ResourceTable.Media_ic_launcher_round, TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT)));
        return drawableStateElement;
    }

    /**
     * initialize thumb drawable state list.
     *
     * @param context context
     * @return drawable state element
     */
    public static StateElement initThumbDrawableState(Context context) {
        StateElement drawableStateElement = new StateElement();
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_PRESSED},
                new PixelMapElement(PixelMapUtil.getPixelMapFromResourceId(context, com.warkiz.tickseekbar.ResourceTable.Media_ic_launcher_round, TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT)));
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_EMPTY},
                new PixelMapElement(PixelMapUtil.getPixelMapFromResourceId(context, ResourceTable.Media_ic_app, TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT)));
        return drawableStateElement;
    }

    /**
     * initialize thumb color state list.
     *
     * @return color state element
     */
    public static StateElement initThumbColorState(Context context) {
        StateElement drawableStateElement = new StateElement();
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_PRESSED},
                createCircularDrawable(context, com.warkiz.tickseekbar.ResourceTable.Color_color_blue,
                        TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT));
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_EMPTY},
                createCircularDrawable(context, com.warkiz.tickseekbar.ResourceTable.Color_colorAccent,
                        TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT));
        return drawableStateElement;
    }

    private static ShapeElement createCircularDrawable(Context context, int resId, float radius) {
        ShapeElement drawable = new ShapeElement();
        drawable.setShape(ShapeElement.OVAL);
        try {
            drawable.setRgbColor(new RgbColor(context.getResourceManager().getElement(resId).getColor()));
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        drawable.setCornerRadius(radius);
        return drawable;
    }

    /**
     * initialize tick text color state list.
     *
     * @param stateCount state count
     * @return color state element
     */
    public static StateElement initTickTextColorState(int stateCount) {
        StateElement colorState = new StateElement();
        RgbColor rgbColor = new RgbColor(Color.RED.getValue());
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setRgbColor(rgbColor);
        for (int i = 0; i < stateCount; i++) {
            colorState.addState(new int[] {ComponentState.COMPONENT_STATE_SELECTED}, shapeElement);
        }
        return colorState;
    }

    /**
     * initialize tick marks color state list.
     *
     * @return color state element
     */
    public static StateElement initTickMarksColorState(Context context) {
        StateElement drawableStateElement = new StateElement();
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_SELECTED},
                createCircularDrawable(context, com.warkiz.tickseekbar.ResourceTable.Color_colorPrimary,
                        TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT));
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_FOCUSED},
                createCircularDrawable(context, com.warkiz.tickseekbar.ResourceTable.Color_colorAccent,
                        TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT));
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_EMPTY},
                createCircularDrawable(context, com.warkiz.tickseekbar.ResourceTable.Color_color_gray,
                        TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT));
        return drawableStateElement;
    }
}
