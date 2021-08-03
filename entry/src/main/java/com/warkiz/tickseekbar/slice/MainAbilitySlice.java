package com.warkiz.tickseekbar.slice;

import com.warkiz.tickseekbar.ResourceTable;
import com.warkiz.tickseekbar.utils.LogUtil;
import com.warkiz.tickseekbar.utils.TickSeekBarConstants;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.ability.fraction.FractionManager;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PageSlider;
import ohos.agp.components.PageSliderProvider;
import ohos.agp.components.TabList;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;

/**
 * MainAbilitySlice class for TickSeekBar.
 */
public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();

    private static String[] tabNameList = new String[]{"CONTINUOUS", "DISCRETE", "CUSTOM", "JAVA"};
    private TabList tabList;
    private PageSlider pageSlider;
    private int selectedPage = 0;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        tabList = (TabList) findComponentById(ResourceTable.Id_tab);
        pageSlider = (PageSlider) findComponentById(ResourceTable.Id_page);

        for (String tabName : tabNameList) {
            TabList.Tab tab = tabList.new Tab(MainAbilitySlice.this);
            tab.setText(tabName);
            tabList.addTab(tab);
        }
        tabCustom();
        setupViewPager(pageSlider);
        pageListener();
    }

    private void pageListener() {
        pageSlider.addPageChangedListener(new PageSlider.PageChangedListener() {
            @Override
            public void onPageSliding(int itemPos, float itemPosOffset, int itemPosOffsetPixels) {
                LogUtil.info(TAG, "onPageSliding : " + itemPos + " " + itemPosOffsetPixels);
            }

            @Override
            public void onPageSlideStateChanged(int pageState) {
                LogUtil.info(TAG, "onPageSlideStateChanged : " + pageState);
            }

            @Override
            public void onPageChosen(int itemPos) {
                selectedPage = itemPos;
                tabList.selectTabAt(itemPos);
                tabList.setTabTextColors(Color.WHITE.getValue(), getColor(ResourceTable.Color_colorAccent));
            }
        });
    }

    private void tabCustom() {
        tabList.setTabMargin(TickSeekBarConstants.TAB_MARGIN);
        tabList.setTabTextSize(TickSeekBarConstants.TAB_TEXT_SIZE);
        tabList.setPadding(TickSeekBarConstants.TAB_PADDING_VALUE, TickSeekBarConstants.TAB_PADDING_VALUE, TickSeekBarConstants.TAB_PADDING_VALUE, TickSeekBarConstants.TAB_PADDING_VALUE);
        tabList.selectTabAt(0);
        tabList.setTabTextColors(Color.WHITE.getValue(), getColor(ResourceTable.Color_colorAccent));
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                int tabPosition = tab.getPosition();
                ToastDialog toastDialog = new ToastDialog(MainAbilitySlice.this);
                toastDialog.setText(tabNameList[tabPosition]);
                toastDialog.show();
                tabList.setTabTextColors(Color.WHITE.getValue(), getColor(ResourceTable.Color_colorAccent));
                pageSlider.setCurrentPage(tab.getPosition());
            }

            @Override
            public void onUnselected(TabList.Tab tab) {
                LogUtil.info(TAG, "onUnselected : " + tab.getPosition());
            }

            @Override
            public void onReselected(TabList.Tab tab) {
                LogUtil.info(TAG, "onReselected : " + tab.getPosition());
            }
        });
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void setupViewPager(PageSlider viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(((FractionAbility) getAbility()).getFractionManager());
        viewPager.setProvider(adapter);
    }

    /**
     * creating a pagerAdapter.
     */
    public class ViewPagerAdapter extends PageSliderProvider {
        private FractionManager mFractionManager;

        /**
         * ViewPagerAdapter constructor.
         *
         * @param fractionManager fraction manager
         */
        public ViewPagerAdapter(FractionManager fractionManager) {
            this.mFractionManager = fractionManager;
        }

        @Override
        public int getCount() {
            return tabNameList.length;
        }

        @Override
        public Object createPageInContainer(ComponentContainer componentContainer, int position) {
            Fraction fraction = null;
            if (position == 0) {
                fraction = new ContinuousFraction(MainAbilitySlice.this, componentContainer);
            } else if (position == 1) {
                fraction = new DiscreteFraction(MainAbilitySlice.this, componentContainer);
            } else if (position == 2) {
                fraction = new CustomFraction(MainAbilitySlice.this, componentContainer);
            } else if (position == TickSeekBarConstants.PAGE_IN_CONTAINER_POSITION_3) {
                fraction = new JavaFraction(MainAbilitySlice.this, componentContainer);
            }
            componentContainer.removeAllComponents();
            if (fraction != null) {
                componentContainer.addComponent(fraction.getComponent());
            }
            return componentContainer;
        }

        @Override
        public void destroyPageFromContainer(ComponentContainer componentContainer, int position, Object object) {
            componentContainer.removeComponent((Component) object);
        }

        @Override
        public boolean isPageMatchToObject(Component component, Object object) {
            return true;
        }
    }
}