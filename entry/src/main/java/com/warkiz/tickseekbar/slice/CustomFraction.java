package com.warkiz.tickseekbar.slice;

import com.warkiz.tickseekbar.ResourceTable;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.render.layoutboost.LayoutBoost;
import ohos.app.Context;

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