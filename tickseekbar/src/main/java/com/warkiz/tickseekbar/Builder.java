package com.warkiz.tickseekbar;

import com.warkiz.tickseekbar.utils.SizeUtils;
import com.warkiz.tickseekbar.utils.TickSeekBarConstants;

import java.io.IOException;

import ohos.agp.components.element.Element;
import ohos.agp.components.element.StateElement;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;

/**
 * Builder class for TickSeekBar.
 */
public class Builder {
    final Context context;

    //seek bar
    float max = TickSeekBarConstants.DEFAULT_MAX_VALUE;
    float min = 0;
    float progress = (float) 0.0;
    boolean progressValueFloat = false;
    boolean seekSmoothly = false;
    boolean r2l = false;
    boolean userSeekable = true;
    boolean onlyThumbDraggable = false;

    //track
    int trackBackgroundSize;
    Color trackBackgroundColor = new Color(Color.getIntColor(TickSeekBarConstants.COLOR_LIGHTER_GRAY));
    int trackProgressSize;
    Color trackProgressColor = new Color(Color.getIntColor(TickSeekBarConstants.COLOR_ACCENT));
    boolean trackRoundedCorners = true;

    //thumbText
    Color thumbTextColor = new Color(Color.getIntColor(TickSeekBarConstants.COLOR_ACCENT));
    int thumbTextShow = TextPosition.NONE;

    //thumb
    int thumbSize;
    Color thumbColor = new Color(Color.getIntColor(TickSeekBarConstants.COLOR_ACCENT));
    boolean thumbAutoAdjust = true;
    StateElement thumbColorStateList = null;
    Element thumbDrawable = null;

    //tickTexts
    int tickTextsShow = TextPosition.NONE;
    Color tickTextsColor = new Color(Color.getIntColor(TickSeekBarConstants.COLOR_ACCENT));
    StateElement tickTextsColorStateList;
    int tickTextsSize = TickSeekBarConstants.DEFAULT_TICK_TEXT_SIZE;
    String[] tickTextsCustomArray = null;
    Font tickTextFontType = Font.DEFAULT;

    //tickMarks
    int tickCount = 2;
    int showTickMarksType = TickMarkType.NONE;
    Color tickMarksColor = new Color(Color.getIntColor(TickSeekBarConstants.COLOR_ACCENT));
    int tickMarksSize;
    Element tickMarksDrawable = null;
    ohos.global.resource.Element tickMarksResourceDrawable;
    boolean tickMarksEndsHide = false;
    boolean tickMarksSweptHide = false;
    StateElement tickMarksColorStateList;

    /**
     * To initialize the clearPadding.
     */
    boolean clearPadding = false;

    Builder(Context context) {
        this.context = context;
        this.trackBackgroundSize = 2;
        this.trackProgressSize = 2;
        this.tickMarksSize = TickSeekBarConstants.DEFAULT_TICK_MARKS_SIZE;
        this.thumbSize = TickSeekBarConstants.DEFAULT_THUMB_SIZE;
    }

    /**
     * call this to new an TickSeekBar.
     *
     * @return TickSeekBar
     */
    public TickSeekBar build() {
        return new TickSeekBar(this);
    }

    /**
     * Set the upper limit of this seek bar's range.
     */
    public Builder max(float max) {
        this.max = max;
        return this;
    }

    /**
     * Set the  lower limit of this seek bar's range.
     */
    public Builder min(float min) {
        this.min = min;
        return this;
    }

    /**
     * Sets the current progress to the specified value.
     */
    public Builder progress(float progress) {
        this.progress = progress;
        return this;
    }

    /**
     * make the progress in float type. default in int type.
     *
     * @param isFloatProgress true for float progress,default false.
     */
    public Builder progressValueFloat(boolean isFloatProgress) {
        this.progressValueFloat = isFloatProgress;
        return this;
    }

    /**
     * seek continuously or discrete.
     *
     * @param seekSmoothly true for seek continuously ignore having tickMarks.
     */
    public Builder seekSmoothly(boolean seekSmoothly) {
        this.seekSmoothly = seekSmoothly;
        return this;
    }

    /**
     * right to left,compat local problem.
     *
     * @param r2l true for local which read text from right to left
     */
    public Builder r2l(boolean r2l) {
        this.r2l = r2l;
        return this;
    }

    /**
     * seek bar has a default padding left and right(16 dp) , call this method to set both to zero.
     *
     * @param clearPadding true to clear the default padding, false to keep.
     * @return Builder
     */
    public Builder clearPadding(boolean clearPadding) {
        this.clearPadding = clearPadding;
        return this;
    }

    /**
     * prevent user from touching to seek or not.
     *
     * @param userSeekable true user can seek.
     * @return Builder object
     */
    public Builder userSeekable(boolean userSeekable) {
        this.userSeekable = userSeekable;
        return this;
    }

    /**
     * user change the thumb's location by touching thumb,touching track will not worked.
     *
     * @param onlyThumbDraggable true for seeking only by drag thumb. default false;
     * @return Builder object
     */
    public Builder onlyThumbDraggable(boolean onlyThumbDraggable) {
        this.onlyThumbDraggable = onlyThumbDraggable;
        return this;
    }

    /**
     * set the seek bar's background track's Stroke Width.
     *
     * @param trackBackgroundSize The dp size.
     * @return Builder object
     */
    public Builder trackBackgroundSize(int trackBackgroundSize) {
        this.trackBackgroundSize = SizeUtils.dp2px(context, trackBackgroundSize);
        return this;
    }

    /**
     * set the seek bar's background track's color.
     *
     * @param trackBackgroundColor the background track color
     * @return Builder object
     */
    public Builder trackBackgroundColor(Color trackBackgroundColor) {
        this.trackBackgroundColor = trackBackgroundColor;
        return this;
    }

    /**
     * set the seek bar's progress track's Stroke Width.
     *
     * @param trackProgressSize The dp size.
     */
    public Builder trackProgressSize(int trackProgressSize) {
        this.trackProgressSize = SizeUtils.dp2px(context, trackProgressSize);
        return this;
    }

    /**
     * set the seek bar's progress track's color.
     *
     * @param trackProgressColor the progress track color
     * @return Builder object
     */
    public Builder trackProgressColor(Color trackProgressColor) {
        this.trackProgressColor = trackProgressColor;
        return this;
    }

    /**
     * call this method to show the seek bar's ends to square corners.default rounded corners.
     *
     * @param trackRoundedCorners false to show square corners.
     */
    public Builder trackRoundedCorners(boolean trackRoundedCorners) {
        this.trackRoundedCorners = trackRoundedCorners;
        return this;
    }

    /**
     * set the seek bar's thumb's text color.
     *
     * @param thumbTextColor the thumb text color
     * @return Builder object
     */
    public Builder thumbTextColor(Color thumbTextColor) {
        this.thumbTextColor = thumbTextColor;
        return this;
    }

    /**
     * call this method to show the text below thumb in one place,
     * the text will slide with the thumb.
     *
     * @param thumbTextPosition see{@link TextPosition}
     */
    public Builder thumbTextPosition(int thumbTextPosition) {
        this.thumbTextShow = thumbTextPosition;
        return this;
    }

    /**
     * set the seek bar's thumb's color.
     *
     * @param thumbColor the thumb color
     * @return Builder object
     */
    public Builder thumbColor(Color thumbColor) {
        this.thumbColor = thumbColor;
        return this;
    }

    /**
     * adjust thumb to tick position auto after touch up.
     *
     * @param autoAdjust true to adjust thumb to tick position auto after touch up
     */
    public Builder thumbAutoAdjust(boolean autoAdjust) {
        this.thumbAutoAdjust = autoAdjust;
        return this;
    }

    /**
     * set the seek bar's thumb's selector color.
     *
     * @param thumbColorStateList color selector
     * @return Builder
     * selector format like:
     */
    public Builder thumbColorStateList(StateElement thumbColorStateList) {
        this.thumbColorStateList = thumbColorStateList;
        return this;
    }

    /**
     * set the seek bar's thumb's Width.will be limited in 30dp.
     *
     * @param thumbSize The dp size.
     */
    public Builder thumbSize(int thumbSize) {
        this.thumbSize = SizeUtils.dp2px(context, thumbSize);
        return this;
    }

    /**
     * call this method to custom the thumb showing drawable.
     *
     * @param thumbDrawable the drawable show as Thumb.
     */
    public Builder thumbDrawable(Element thumbDrawable) {
        this.thumbDrawable = thumbDrawable;
        return this;
    }

    /**
     * call this method to custom the thumb showing drawable by selector Drawable.
     *
     * @param thumbStateListDrawable the drawable show as Thumb.
     * @return Builder
     */
    public Builder thumbDrawableStateList(StateElement thumbStateListDrawable) {
        this.thumbDrawable = thumbStateListDrawable;
        return this;
    }

    /**
     * call this method to custom the thumb showing drawable.
     *
     * @param thumbDrawableId the drawableId for thumb drawable.
     */
    public Builder thumbDrawable(int thumbDrawableId) {
        return this;
    }

    /**
     * show the tick texts in one place.
     *
     * @param textPosition the position for texts to show,
     *                     see{@link TextPosition}
     *                     TextPosition.NONE;
     *                     TextPosition.BELOW;
     *                     TextPosition.ABOVE;
     */
    public Builder showTickTextsPosition(int textPosition) {
        this.tickTextsShow = textPosition;
        return this;
    }

    /**
     * set the color for text below/above seek bar's tickText.
     *
     * @param tickTextsColor for tick text color
     */
    public Builder tickTextsColor(Color tickTextsColor) {
        this.tickTextsColor = tickTextsColor;
        return this;
    }

    /**
     * set the selector color for text below/above seek bar's tickText.
     *
     * @param tickTextsColorStateList tick text color state
     * @return Builder objects
     * selector format like:
     */
    public Builder tickTextsColorStateList(StateElement tickTextsColorStateList) {
        this.tickTextsColorStateList = tickTextsColorStateList;
        return this;
    }

    /**
     * set the size for tickText which below/above seek bar's tick .
     *
     * @param tickTextsSize The scaled pixel size.
     */
    public Builder tickTextsSize(int tickTextsSize) {
        this.tickTextsSize = SizeUtils.sp2px(context, tickTextsSize);
        return this;
    }

    /**
     * call this method to replace the seek bar's tickMarks' below/above tick texts.
     *
     * @param tickTextsArray the length should same as tickCount.
     */
    public Builder tickTextsArray(String[] tickTextsArray) {
        this.tickTextsCustomArray = tickTextsArray;
        return this;
    }

    /**
     * call this method to replace the seek bar's tickMarks' below/above tick texts.
     *
     * @param tickTextsArray the length should same as tickNum.
     */
    public Builder tickTextsArray(int tickTextsArray) throws NotExistException, WrongTypeException, IOException {
        this.tickTextsCustomArray = context.getResourceManager().getElement(tickTextsArray).getStringArray();
        return this;
    }

    /**
     * set the tickMarks number.
     *
     * @param tickCount the tickMarks count show on seek bar.
     *                  if you want the seek bar's block size is N , this tickCount should be N+1.
     */
    public Builder tickCount(int tickCount) {
        this.tickCount = tickCount;
        return this;
    }

    /**
     * call this method to show different tickMark shape.
     *
     * @param tickMarksType see{@link TickMarkType}
     *                      TickMarkType.NONE;
     *                      TickMarkType.OVAL;
     *                      TickMarkType.SQUARE;
     *                      TickMarkType.DIVIDER;
     *                      TickMarkType.CUSTOM;
     */
    public Builder showTickMarksType(int tickMarksType) {
        this.showTickMarksType = tickMarksType;
        return this;
    }

    /**
     * set the seek bar's tick's color.
     *
     * @param tickMarksColor tick marks color
     */
    public Builder tickMarksColor(Color tickMarksColor) {
        this.tickMarksColor = tickMarksColor;
        return this;
    }

    /**
     * set the seek bar's tick's color.
     *
     * @param tickMarksColorStateList tick marks color state
     * @return Builder object
     * selector format like:
     */
    public Builder tickMarksColorState(StateElement tickMarksColorStateList) {
        this.tickMarksColorStateList = tickMarksColorStateList;
        return this;
    }

    /**
     * set the seek bar's tick width , if tick type is divider, call this method will be not worked(tick type is divider,has a regular value 2dp).
     *
     * @param tickMarksSize the dp size.
     */
    public Builder tickMarksSize(int tickMarksSize) {
        this.tickMarksSize = SizeUtils.dp2px(context, tickMarksSize);
        return this;
    }

    /**
     * call this method to custom the tick showing drawable.
     *
     * @param tickMarksDrawable the drawable show as tickMark.
     */
    public Builder tickMarksDrawable(Element tickMarksDrawable) {
        this.tickMarksDrawable = tickMarksDrawable;
        return this;
    }

    /**
     * call this method to custom the tick showing drawable by selector.
     *
     * @param tickMarksStateListDrawable the StateListDrawable show as tickMark.
     * @return Builder
     * selector format like :
     */
    public Builder tickMarksDrawable(StateElement tickMarksStateListDrawable) {
        this.tickMarksDrawable = tickMarksStateListDrawable;
        return this;
    }

    /**
     * call this method to custom the tick showing drawable by selector.
     *
     * @param  tickMarksResourceDrawable tick marks drawable resource.
     * @return Builder object
     */
    public Builder tickMarksDrawable(ohos.global.resource.Element tickMarksResourceDrawable) {
        this.tickMarksResourceDrawable = tickMarksResourceDrawable;
        return this;
    }

    /**
     * call this method to hide the tickMarks which show in the both ends sides of seek bar.
     *
     * @param tickMarksEndsHide true for hide.
     */
    public Builder tickMarksEndsHide(boolean tickMarksEndsHide) {
        this.tickMarksEndsHide = tickMarksEndsHide;
        return this;
    }

    /**
     * call this method to hide the tickMarks on seekBar's thumb left.
     *
     * @param tickMarksSweptHide true for hide.
     */
    public Builder tickMarksSweptHide(boolean tickMarksSweptHide) {
        this.tickMarksSweptHide = tickMarksSweptHide;
        return this;
    }
}