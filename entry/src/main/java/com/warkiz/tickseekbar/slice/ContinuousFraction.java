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
import ohos.app.Context;
import com.warkiz.tickseekbar.OnSeekChangeListener;
import com.warkiz.tickseekbar.ResourceTable;
import com.warkiz.tickseekbar.SeekParams;
import com.warkiz.tickseekbar.TickSeekBar;
import com.warkiz.tickseekbar.utils.TickSeekBarConstants;

/**
 * Continuous Fraction to display continuous TickSeekBar.
 */
public class ContinuousFraction extends Fraction {
    private ComponentContainer mContainer;
    private Context mContext;

    /**
     * ContinuousFraction constructor.
     *
     * @param context context
     * @param componentContainer component Container
     */
    public ContinuousFraction(Context context, ComponentContainer componentContainer) {
        this.mContext = context;
        this.mContainer = componentContainer;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        return scatter.parse(ResourceTable.Layout_continuous_layout, container, false);
    }

    /**
     * getting component.
     *
     * @return component object
     */
    @Override
    public Component getComponent() {
        Component component = LayoutBoost.inflate(this.mContext, ResourceTable.Layout_continuous_layout,
                this.mContainer, false);
        initView(component);
        return component;
    }

    private void initView(Component component) {
        TickSeekBar scale = (TickSeekBar) component.findComponentById(ResourceTable.Id_scale);
        if (scale != null) {
            scale.setDecimalScale(TickSeekBarConstants.DECIMAL_SCALE_VALUE);
        }
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
        listenerSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                states.setText(mContext.getString(ResourceTable.String_states_text)
                        + mContext.getString(ResourceTable.String_on_seeking));
                progress.setText(mContext.getString(ResourceTable.String_progress_text) + seekParams.getProgress());
                progressFloat.setText(mContext.getString(ResourceTable.String_progress_float_text)
                        + seekParams.getProgressFloat());
                fromUser.setText(mContext.getString(ResourceTable.String_from_user_text) + seekParams.isFromUser());
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