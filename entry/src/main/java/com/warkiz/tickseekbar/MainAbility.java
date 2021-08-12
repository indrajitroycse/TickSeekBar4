package com.warkiz.tickseekbar;

import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.content.Intent;
import com.warkiz.tickseekbar.slice.MainAbilitySlice;

/**
 * Main Ability to display TickSeekBar.
 */
public class MainAbility extends FractionAbility {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }
}
