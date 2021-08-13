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
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.render.layoutboost.LayoutBoost;
import ohos.agp.utils.Color;
import ohos.app.Context;
import com.warkiz.tickseekbar.OnSeekChangeListener;
import com.warkiz.tickseekbar.ResourceTable;
import com.warkiz.tickseekbar.SeekParams;
import com.warkiz.tickseekbar.TickSeekBar;
import com.warkiz.tickseekbar.utils.TickSeekBarConstants;

/**
 * Discrete Fraction to display Discrete TickSeekBar.
 */
public class DiscreteFraction extends Fraction {
    private ComponentContainer mComponentContainer;
    private Context mContext;

    /**
     * DiscreteFraction constructor.
     *
     * @param context context
     * @param componentContainer component Container
     */
    public DiscreteFraction(Context context, ComponentContainer componentContainer) {
        this.mContext = context;
        this.mComponentContainer = componentContainer;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        return scatter.parse(ResourceTable.Layout_discrete_layout, container, false);
    }

    @Override
    public Component getComponent() {
        Component component = LayoutBoost.inflate(this.mContext, ResourceTable.Layout_discrete_layout,
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
        //customTickTexts
        TickSeekBar seekBar = (TickSeekBar) component.findComponentById(ResourceTable.Id_custom_text);
        String[] arr = {"A", "a", "B", "b", "C", "c", "D"};
        seekBar.customTickTexts(arr);

        TickSeekBar sectionSeekBar = (TickSeekBar) component.findComponentById(ResourceTable.Id_custom_section_color);
        sectionSeekBar.customSectionTrackColor((Color[] colorIntArr) -> {
            // the length of colorIntArray equals section count
            colorIntArr[0] = new Color(mContext.getColor(ResourceTable.Color_color_blue));
            colorIntArr[1] = new Color(mContext.getColor(ResourceTable.Color_color_gray));
            colorIntArr[2] = new Color(Color.getIntColor(TickSeekBarConstants.COLOR_ACCENT));
            colorIntArr[TickSeekBarConstants.INDEX_VALUE_THREE] = new Color(Color
                    .getIntColor(TickSeekBarConstants.COLOR_PRIMARY_DARK_BLUE));
            return true;    //true if apply color , otherwise no change
        });

        //set listener
        TickSeekBar listenerSeekBar = (TickSeekBar) component.findComponentById(ResourceTable.Id_listener);
        final Text states = (Text) component.findComponentById(ResourceTable.Id_states);
        states.setText(mContext.getString(ResourceTable.String_states_text));
        final Text progress = (Text) component.findComponentById(ResourceTable.Id_progress);
        progress.setText(mContext.getString(ResourceTable.String_progress_text) + listenerSeekBar.getProgress());
        final Text progressFloat = (Text) component.findComponentById(ResourceTable.Id_progress_float);
        progressFloat.setText(mContext.getString(ResourceTable.String_progress_float_text)
                + listenerSeekBar.getProgressFloat());
        final Text fromUser = (Text) component.findComponentById(ResourceTable.Id_from_user);
        fromUser.setText(mContext.getString(ResourceTable.String_from_user_text));
        final Text thumbPosition = (Text) component.findComponentById(ResourceTable.Id_thumb_position);
        thumbPosition.setText(mContext.getString(ResourceTable.String_thumb_position_text));
        final Text tickText = (Text) component.findComponentById(ResourceTable.Id_tick_text);
        tickText.setText(mContext.getString(ResourceTable.String_tick_text));
        listenerSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                states.setText(mContext.getString(ResourceTable.String_states_text)
                        + mContext.getString(ResourceTable.String_on_seeking));
                progress.setText(mContext.getString(ResourceTable.String_progress_text) + seekParams.getProgress());
                progressFloat.setText(mContext.getString(ResourceTable.String_progress_float_text)
                        + seekParams.getProgressFloat());
                fromUser.setText(mContext.getString(ResourceTable.String_from_user_text) + seekParams.isFromUser());
                thumbPosition.setText(mContext.getString(ResourceTable.String_thumb_position_text)
                        + seekParams.getThumbPosition());
                tickText.setText(mContext.getString(ResourceTable.String_tick_text) + seekParams.getTickText());
            }

            @Override
            public void onStartTrackingTouch(TickSeekBar seekBar) {
                states.setText(mContext.getString(ResourceTable.String_states_text)
                        + mContext.getString(ResourceTable.String_on_start));
                progress.setText(mContext.getString(ResourceTable.String_progress_text) + seekBar.getProgress());
                progressFloat.setText(mContext.getString(ResourceTable.String_progress_float_text)
                        + seekBar.getProgressFloat());
                fromUser.setText(mContext.getString(ResourceTable.String_from_user_text) + "true");
            }

            @Override
            public void onStopTrackingTouch(TickSeekBar seekBar) {
                states.setText(mContext.getString(ResourceTable.String_states_text)
                        + mContext.getString(ResourceTable.String_on_stop));
                progress.setText(mContext.getString(ResourceTable.String_progress_text) + seekBar.getProgress());
                progressFloat.setText(mContext.getString(ResourceTable.String_progress_float_text)
                        + seekBar.getProgressFloat());
                fromUser.setText(mContext.getString(ResourceTable.String_from_user_text) + "false");
            }
        });
    }
}