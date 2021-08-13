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

package com.warkiz.tickseekbar.slice;

import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.render.layoutboost.LayoutBoost;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import com.warkiz.tickseekbar.ResourceTable;
import com.warkiz.tickseekbar.TextPosition;
import com.warkiz.tickseekbar.TickMarkType;
import com.warkiz.tickseekbar.TickSeekBar;
import com.warkiz.tickseekbar.utils.LogUtil;
import com.warkiz.tickseekbar.utils.StateElementUtil;
import com.warkiz.tickseekbar.utils.TickSeekBarConstants;
import java.io.IOException;

/**
 * Java Fraction to display Java TickSeekBar.
 */
public class JavaFraction extends Fraction {
    private static final String TAG = JavaFraction.class.getSimpleName();

    private ComponentContainer mComponentContainer;
    private  Context mContext;

    /**
     * JavaFraction constructor.
     *
     * @param context context
     * @param componentContainer component Container
     */
    public JavaFraction(Context context, ComponentContainer componentContainer) {
        this.mContext = context;
        this.mComponentContainer = componentContainer;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        return scatter.parse(ResourceTable.Layout_java_layout, container, false);
    }

    @Override
    public Component getComponent() {
        Component component = LayoutBoost.inflate(this.mContext, ResourceTable.Layout_java_layout,
                this.mComponentContainer, false);
        initView(component);
        return component;
    }

    /**
     * Initialize the views and components.
     *
     * @param component container component
     */
    protected void initView(Component component) {
        final DirectionalLayout content = (DirectionalLayout) component.findComponentById(ResourceTable.Id_java_build);

        //CONTINUOUS
        addContinuousComponent(content);

        //CONTINUOUS_TEXTS_ENDS
        addContinuousTextEndComponent(content);

        //CONTINUOUS_TEXTS_ENDS_RIPPLE_THUMB
        addContinuousRippleThumb(content);

        //CONTINUOUS_TEXTS_ENDS_CUSTOM
        addContinuousTextEndCustom(content);

        //DISCRETE_TICKS
        addDiscreteTickComponent(content);

        //DISCRETE_TICKS_TEXTS
        addDiscreteTickText(content);

        //DISCRETE_TICKS_TEXTS_CUSTOM
        addDiscreteTextCustom(content);

        //DISCRETE_TICKS_TEXTS_END
        addDiscreteTickTextEnd(content);
    }

    private void addContinuousComponent(DirectionalLayout content) {
        Text textView1 = getTextView();
        textView1.setText("continuous");
        content.addComponent(textView1);
        TickSeekBar continuous = null;
        try {
            continuous = TickSeekBar
                    .with(mContext)
                    .max(TickSeekBarConstants.CUSTOM_MAX_VALUE)
                    .min(TickSeekBarConstants.CUSTOM_MIN_VALUE)
                    .progress(TickSeekBarConstants.CUSTOM_PROGRESS_VALUE)
                    .thumbColorStateList(StateElementUtil.initThumbColorState(mContext))
                    .thumbSize(TickSeekBarConstants.CUSTOM_THUMB_SIZE)
                    .trackProgressColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_colorAccent).getColor()))
                    .trackProgressSize(TickSeekBarConstants.CUSTOM_TRACK_PROGRESS_SIZE)
                    .trackBackgroundColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_color_gray).getColor()))
                    .trackBackgroundSize(2)
                    .thumbTextPosition(TextPosition.BELOW)
                    .thumbTextColor(new Color(mContext.getResourceManager().getElement(ResourceTable.Color_color_gray)
                            .getColor()))
                    .build();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        content.addComponent(continuous);
    }

    private void addContinuousTextEndComponent(DirectionalLayout content) {
        Text textView2 = getTextView();
        textView2.setText("continuous_texts_ends");
        content.addComponent(textView2);
        TickSeekBar continuous2TickTexts = null;
        try {
            continuous2TickTexts = TickSeekBar
                    .with(mContext)
                    .max(TickSeekBarConstants.CUSTOM_MAX_VALUE)
                    .min(TickSeekBarConstants.CUSTOM_MIN_VALUE)
                    .progress(TickSeekBarConstants.CUSTOM_PROGRESS_VALUE)
                    .tickCount(2)
                    .showTickMarksType(TickMarkType.NONE)
                    .showTickTextsPosition(TextPosition.ABOVE)
                    .thumbDrawableStateList(StateElementUtil.initThumbDrawableState(mContext))
                    .thumbSize(TickSeekBarConstants.CONTINUOUS_TEXTS_ENDS_THUMB_SIZE)
                    .trackProgressColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_colorAccent).getColor()))
                    .trackProgressSize(TickSeekBarConstants.CUSTOM_TRACK_PROGRESS_SIZE)
                    .trackBackgroundColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_color_gray).getColor()))
                    .trackBackgroundSize(2)
                    .build();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        content.addComponent(continuous2TickTexts);
    }

    private void addContinuousRippleThumb(DirectionalLayout content) {
        Text textView22 = getTextView();
        textView22.setText("continuous_texts_ends_custom_ripple_thumb");
        content.addComponent(textView22);
        TickSeekBar continuousTextsEndsCustomThumb = null;
        try {
            continuousTextsEndsCustomThumb = TickSeekBar
                    .with(mContext)
                    .max(TickSeekBarConstants.CUSTOM_MAX_VALUE)
                    .min(TickSeekBarConstants.CUSTOM_MIN_VALUE)
                    .progress(TickSeekBarConstants.CUSTOM_PROGRESS_VALUE)
                    .tickCount(2)
                    .showTickMarksType(TickMarkType.NONE)
                    .showTickTextsPosition(TextPosition.ABOVE)
                    //.thumbDrawable(mContext.getResourceManager()
                    //.getElement(ResourceTable.Graphic_selector_thumb_ripple_drawable))
                    .thumbSize(TickSeekBarConstants.CONTINUOUS_TEXTS_ENDS_CUSTOM_THUMB_SIZE)
                    .trackProgressColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_colorAccent).getColor()))
                    .trackProgressSize(TickSeekBarConstants.CUSTOM_TRACK_PROGRESS_SIZE)
                    .trackBackgroundColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_color_gray).getColor()))
                    .trackBackgroundSize(2)
                    .build();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        content.addComponent(continuousTextsEndsCustomThumb);
    }

    private void addContinuousTextEndCustom(DirectionalLayout content) {
        Text textView3 = getTextView();
        textView3.setText("continuous_texts_ends_custom");
        content.addComponent(textView3);
        TickSeekBar continuous2TickTexts1 = null;
        try {
            continuous2TickTexts1 = TickSeekBar
                    .with(mContext)
                    .max(TickSeekBarConstants.CUSTOM_MAX_VALUE)
                    .min(TickSeekBarConstants.CUSTOM_MIN_VALUE)
                    .progress(TickSeekBarConstants.CUSTOM_PROGRESS_VALUE)
                    .tickCount(2)
                    .showTickMarksType(TickMarkType.NONE)
                    .showTickTextsPosition(TextPosition.ABOVE)
                    .tickTextsArray(mContext.getResourceManager().getElement(ResourceTable.Strarray_last_next_length_2)
                            .getStringArray())
                    .thumbColor(new Color(mContext.getResourceManager().getElement(ResourceTable.Color_color_1)
                            .getColor()))
                    .thumbSize(TickSeekBarConstants.CUSTOM_THUMB_SIZE)
                    .trackProgressColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_colorAccent).getColor()))
                    .trackProgressSize(TickSeekBarConstants.CUSTOM_TRACK_PROGRESS_SIZE)
                    .trackBackgroundColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_color_gray).getColor()))
                    .trackBackgroundSize(2)
                    .build();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        content.addComponent(continuous2TickTexts1);
    }

    private void addDiscreteTickComponent(DirectionalLayout content) {
        Text textView4 = getTextView();
        textView4.setText("discreteTicks");
        content.addComponent(textView4);
        TickSeekBar discreteTicks = null;
        try {
            discreteTicks = TickSeekBar
                    .with(mContext)
                    .max(TickSeekBarConstants.CUSTOM_MAX_VALUE)
                    .min(TickSeekBarConstants.CUSTOM_MIN_VALUE)
                    .progress(TickSeekBarConstants.CUSTOM_PROGRESS_VALUE)
                    .tickCount(TickSeekBarConstants.CUSTOM_TICK_COUNT)
                    .tickMarksSize(TickSeekBarConstants.CUSTOM_TICK_MARKS_SIZE)
                    .tickMarksDrawable(mContext.getResourceManager()
                            .getElement(ResourceTable.Graphic_selector_tick_marks_drawable))
                    .thumbColor(new Color(mContext.getResourceManager().getElement(ResourceTable.Color_color_1)
                            .getColor()))
                    .thumbSize(TickSeekBarConstants.CUSTOM_THUMB_SIZE)
                    .trackProgressColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_colorAccent).getColor()))
                    .trackProgressSize(TickSeekBarConstants.CUSTOM_TRACK_PROGRESS_SIZE)
                    .trackBackgroundColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_color_gray).getColor()))
                    .trackBackgroundSize(2)
                    .build();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        content.addComponent(discreteTicks);
    }

    private void addDiscreteTickText(DirectionalLayout content) {
        Text textView5 = getTextView();
        textView5.setText("discreteTicksTexts");
        content.addComponent(textView5);
        TickSeekBar discreteTicksTexts = null;
        try {
            discreteTicksTexts = TickSeekBar
                    .with(mContext)
                    .max(TickSeekBarConstants.CUSTOM_MAX_VALUE)
                    .min(TickSeekBarConstants.CUSTOM_DISCRETE_TICKS_MIN)
                    .progressValueFloat(true)
                    .progress(TickSeekBarConstants.CUSTOM_PROGRESS_VALUE)
                    .tickCount(TickSeekBarConstants.CUSTOM_TICK_COUNT)
                    .showTickMarksType(TickMarkType.DIVIDER)
                    .tickMarksColor(new Color(ResourceTable.Color_color_blue))
                    .tickMarksSize(TickSeekBarConstants.CUSTOM_DISCRETE_TICK_MARKS_SIZE)
                    .tickTextsSize(TickSeekBarConstants.CUSTOM_TICK_TEXTS_SIZE)
                    .showTickTextsPosition(TextPosition.ABOVE)
                    .tickTextsColorStateList(StateElementUtil.initTickTextColorState(
                            TickSeekBarConstants.INDEX_VALUE_THREE))
                    .thumbColor(new Color(mContext.getResourceManager().getElement(ResourceTable.Color_color_1)
                            .getColor()))
                    .thumbSize(TickSeekBarConstants.CUSTOM_THUMB_SIZE)
                    .trackProgressColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_colorAccent).getColor()))
                    .trackProgressSize(TickSeekBarConstants.CUSTOM_TRACK_PROGRESS_SIZE)
                    .trackBackgroundColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_color_gray).getColor()))
                    .trackBackgroundSize(2)
                    .build();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        content.addComponent(discreteTicksTexts);
    }

    private void addDiscreteTextCustom(DirectionalLayout content) {
        Text textView6 = getTextView();
        textView6.setText("discrete_ticks_texts_custom");
        content.addComponent(textView6);
        String[] array = {"A", "B", "C", "D", "E", "F", "G"};
        TickSeekBar discreteTicksTexts1 = null;
        try {
            discreteTicksTexts1 = TickSeekBar
                    .with(mContext)
                    .max(TickSeekBarConstants.CUSTOM_MAX_VALUE)
                    .min(TickSeekBarConstants.CUSTOM_MIN_VALUE)
                    .progress(TickSeekBarConstants.CUSTOM_PROGRESS_VALUE)
                    .tickCount(TickSeekBarConstants.CUSTOM_TICK_COUNT)
                    .showTickMarksType(TickMarkType.SQUARE)
                    .tickTextsArray(array)
                    .showTickTextsPosition(TextPosition.ABOVE)
                    .tickTextsColorStateList(StateElementUtil.initTickTextColorState(2))
                    .thumbColor(new Color(mContext.getResourceManager().getElement(ResourceTable.Color_color_1)
                            .getColor()))
                    .thumbSize(TickSeekBarConstants.CUSTOM_THUMB_SIZE)
                    .trackProgressColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_colorAccent).getColor()))
                    .trackProgressSize(TickSeekBarConstants.CUSTOM_TRACK_PROGRESS_SIZE)
                    .trackBackgroundColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_color_gray).getColor()))
                    .trackBackgroundSize(2)
                    .build();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        content.addComponent(discreteTicksTexts1);
    }

    private void addDiscreteTickTextEnd(DirectionalLayout content) {
        Text textView7 = getTextView();
        textView7.setText("discreteTicksTextsEnds");
        content.addComponent(textView7);
        String[] arrayEnds = {"A", "", "", "", "", "", "G"};
        TickSeekBar discreteTicksTextsEnds = null;
        try {
            discreteTicksTextsEnds = TickSeekBar
                    .with(mContext)
                    .max(TickSeekBarConstants.CUSTOM_MAX_VALUE)
                    .min(TickSeekBarConstants.CUSTOM_MIN_VALUE)
                    .progress(TickSeekBarConstants.CUSTOM_PROGRESS_VALUE)
                    .tickCount(TickSeekBarConstants.CUSTOM_TICK_COUNT)
                    .showTickMarksType(TickMarkType.OVAL)
                    .tickMarksColorState(StateElementUtil.initTickMarksColorState(mContext))
                    .tickTextsArray(arrayEnds)
                    .showTickTextsPosition(TextPosition.BELOW)
                    .thumbColor(new Color(mContext.getResourceManager().getElement(ResourceTable.Color_color_red)
                            .getColor()))
                    .thumbSize(TickSeekBarConstants.CUSTOM_THUMB_SIZE)
                    .trackProgressColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_color_blue).getColor()))
                    .trackProgressSize(TickSeekBarConstants.CUSTOM_TRACK_PROGRESS_SIZE)
                    .trackBackgroundColor(new Color(mContext.getResourceManager()
                            .getElement(ResourceTable.Color_color_pink).getColor()))
                    .trackBackgroundSize(2)
                    .build();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        content.addComponent(discreteTicksTextsEnds);
    }

    private Text getTextView() {
        Text textView = new Text(getContext());
        int padding = dp2px(getContext(), TickSeekBarConstants.DP_VALUE);
        textView.setPadding(padding, padding, padding, 0);
        return textView;
    }

    /**
     * Method to convert vp tp px.
     *
     * @param context context value
     * @param dpValue dp value to change
     * @return px value in int
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) ((int)  dpValue * AttrHelper.getDensity(context));
    }
}