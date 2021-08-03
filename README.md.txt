# TickSeekBar

[![DOWNLOAD](https://api.bintray.com/packages/warkiz/maven/tickseekbar/images/download.svg)](https://bintray.com/warkiz/maven/tickseekbar/_latestVersion)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)

This is a customizable SeekBar library on HMOS ported from Android.


## OverView
<img src="https://github.com/warkiz/TickSeekBar/blob/master/gif/overview.png?raw=true" width = "392" height = "115"/>

## Screenshot



## Demo


 Scan QR code to download:


## Setup

```gradle
implementation 'com.github.warkiz.tickseekbar:tickseekbar:0.1.4'
```

## Usage
#### xml

```xml
<com.warkiz.tickseekbar.TickSeekBar
    ohos:id="$+id:listener"
    ohos:width="match_parent"
    ohos:height="match_content"
    app:tsb_show_tick_marks_type="oval"
    app:tsb_show_tick_texts="above"
    app:tsb_thumb_color="$color:colorAccent"
    app:tsb_thumb_size="16vp"
    app:tsb_tick_marks_color="$color:colorAccent"
    app:tsb_tick_marks_size="8vp"
    app:tsb_tick_texts_color="$color:color_pink"
    app:tsb_tick_texts_size="13vp"
    app:tsb_ticks_count="5"
    app:tsb_track_background_color="$color:color_gray"
    app:tsb_track_background_size="2vp"
    app:tsb_track_progress_color="$color:color_blue"
    app:tsb_track_progress_size="3vp" />
```

#### Java

```Java
TickSeekBar seekBar = TickSeekBar
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
                    .tickTextsColorStateList(StateElementUtil.initTickTextColorState(TickSeekBarConstants.INDEX_VALUE_THREE))
                    .thumbColor(new Color(mContext.getResourceManager().getElement(ResourceTable.Color_color_1).getColor()))
                    .thumbSize(TickSeekBarConstants.CUSTOM_THUMB_SIZE)
                    .trackProgressColor(new Color(mContext.getResourceManager().getElement(ResourceTable.Color_colorAccent).getColor()))
                    .trackProgressSize(TickSeekBarConstants.CUSTOM_TRACK_PROGRESS_SIZE)
                    .trackBackgroundColor(new Color(mContext.getResourceManager().getElement(ResourceTable.Color_color_gray).getColor()))
                    .trackBackgroundSize(2)
                    .build();
```

## Custom section tracks color
The color of every block of seek bar can also be custom.

```Java
sectionSeekBar.customSectionTrackColor(new ColorCollector() {
    @Override
    public boolean collectSectionTrackColor(int[] colorIntArr) {
        //the length of colorIntArray equals section count
        colorIntArr[0] = getResources().getColor(R.color.color_blue, null);
        colorIntArr[1] = getResources().getColor(R.color.color_gray, null);
        colorIntArr[2] = Color.parseColor("#FF4081");
        ...
        return true; //True if apply color , otherwise no change
    }
});
```

## Selector color were supported, drawable elements not yet supported

You can set the StateElement for the thumb, tickMarks, tickText colors in Java. Xml selector not supported.
Usage's format acccording to:


Thumb selector color:

```Java
StateElement drawableStateElement = new StateElement();
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_PRESSED},
                createCircularDrawable(context, com.warkiz.tickseekbar.ResourceTable.Color_color_blue,
                        TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT));
        drawableStateElement.addState(new int[] {ComponentState.COMPONENT_STATE_EMPTY},
                createCircularDrawable(context, com.warkiz.tickseekbar.ResourceTable.Color_colorAccent,
                        TickSeekBarConstants.DEFAULT_PIXEL_MAP_HEIGHT));
```

TickMarks selector color：

```Java
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
```

TickTexts selector color：

```Java
if (stateSet.length > 0 && stateSet[0] == COMPONENT_STATE_SELECTED){
                    mSelectedTextsColor = new Color(getContext().getColor(ResourceTable.Color_colorAccent));
                    mUnselectedTextsColor = new Color(getContext().getColor(ResourceTable.Color_color_gray));
                    mHoveredTextColor = new Color(getContext().getColor(ResourceTable.Color_color_blue));
                } else if (stateSet.length > 1 && stateSet[1] == COMPONENT_STATE_HOVERED) {
                    mSelectedTextsColor = new Color(getContext().getColor(ResourceTable.Color_colorAccent));
                    mHoveredTextColor = new Color(getContext().getColor(ResourceTable.Color_color_blue));
                } else {
                    mUnselectedTextsColor = new Color(getContext().getColor(ResourceTable.Color_color_gray));
                    mSelectedTextsColor = mUnselectedTextsColor;
                    mHoveredTextColor = mUnselectedTextsColor;
                }
```

## Listener
```Java
seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
        @Override
        public void onSeeking(SeekParams seekParams) {
            Log.i(TAG, seekParams.seekBar);
            Log.i(TAG, seekParams.progress);
            Log.i(TAG, seekParams.progressFloat);
            Log.i(TAG, seekParams.fromUser);
            //when tick count > 0
            Log.i(TAG, seekParams.thumbPosition);
            Log.i(TAG, seekParams.tickText);
        }

        @Override
        public void onStartTrackingTouch(TickSeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(TickSeekBar seekBar) {
        }

});
```

## Proguard

``` groovy
-dontwarn com.warkiz.tickseekbar.**
```


## License

	Copyright (C) 2017 zhuangguangquan warkiz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
