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
import ohos.agp.render.layoutboost.LayoutBoost;
import ohos.app.Context;
import com.warkiz.tickseekbar.ResourceTable;

/**
 * Custom Fraction to display Custom TickSeekBar.
 */
public class CustomFraction extends Fraction {
    private ComponentContainer mComponentContainer;
    private Context mContext;

    /**
     * CustomFraction constructor.
     *
     * @param context context
     * @param componentContainer component Container
     */
    public CustomFraction(Context context, ComponentContainer componentContainer) {
        this.mContext = context;
        this.mComponentContainer = componentContainer;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        return scatter.parse(ResourceTable.Layout_custom_layout, container, false);
    }

    @Override
    public Component getComponent() {
        return LayoutBoost.inflate(this.mContext, ResourceTable.Layout_custom_layout, this.mComponentContainer, false);
    }
}