package com.warkiz.tickseekbar;

import com.warkiz.tickseekbar.utils.FormatUtils;
import com.warkiz.tickseekbar.utils.LogUtil;
import com.warkiz.tickseekbar.utils.PixelMapUtil;
import com.warkiz.tickseekbar.utils.SizeUtils;
import com.warkiz.tickseekbar.utils.StateElementUtil;
import com.warkiz.tickseekbar.utils.TickSeekBarConstants;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import ohos.agp.animation.AnimatorValue;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.DragInfo;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.components.element.StateElement;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.PixelMapHolder;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.agp.utils.Rect;
import ohos.agp.utils.RectFloat;
import ohos.agp.utils.TextAlignment;
import ohos.agp.utils.TextTool;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Size;
import ohos.multimodalinput.event.TouchEvent;

import static ohos.agp.components.ComponentState.COMPONENT_STATE_PRESSED;
import static ohos.agp.components.ComponentState.COMPONENT_STATE_SELECTED;
import static ohos.agp.components.ComponentState.COMPONENT_STATE_HOVERED;
import static ohos.agp.components.ComponentState.COMPONENT_STATE_FOCUSED;

/**
 * Creating the TickSeekBar.
 */
public class TickSeekBar extends Component implements Component.DrawTask, Component.TouchEventListener,
        Component.EstimateSizeListener, Component.DraggedListener {
    private static final String TAG = TickSeekBar.class.getSimpleName();
    private static final String TSB_SHOW_THUMB_TEXT = "tsb_show_thumb_text";
    private static final String TSB_PROGRESS = "tsb_progress";
    private static final String TSB_PROGRESS_VALUE_FLOAT = "tsb_progress_value_float";
    private static final String TSB_MAX = "tsb_max";
    private static final String TSB_MIN = "tsb_min";
    private static final String TSB_USER_SEEKABLE = "tsb_user_seekable";
    private static final String TSB_CLEAR_DEFAULT_PADDING = "tsb_clear_default_padding";
    private static final String TSB_ONLY_THUMB_DRAGGABLE = "tsb_only_thumb_draggable";
    private static final String TSB_SEEK_SMOOTHLY = "tsb_seek_smoothly";
    private static final String TSB_R2L = "tsb_r2l";
    private static final String TSB_TRACK_BACKGROUND_SIZE = "tsb_track_background_size";
    private static final String TSB_TRACK_PROGRESS_SIZE = "tsb_track_progress_size";
    private static final String TSB_TRACK_BACKGROUND_COLOR = "tsb_track_background_color";
    private static final String TSB_TRACK_PROGRESS_COLOR = "tsb_track_progress_color";
    private static final String TSB_TRACK_ROUNDED_CORNERS = "tsb_track_rounded_corners";
    private static final String TSB_THUMB_SIZE = "tsb_thumb_size";
    private static final String TSB_THUMB_DRAWABLE = "tsb_thumb_drawable";
    private static final String TSB_THUMB_DRAWABLE_SELECTOR = "tsb_thumb_drawable_selector";
    private static final String TSB_THUMB_ADJUST_AUTO = "tsb_thumb_adjust_auto";
    private static final String TSB_THUMB_TEXT_COLOR = "tsb_thumb_text_color";
    private static final String TSB_TICKS_COUNT = "tsb_ticks_count";
    private static final String TSB_SHOW_TICK_MARKS_TYPE = "tsb_show_tick_marks_type";
    private static final String TSB_TICK_MARKS_SIZE = "tsb_tick_marks_size";
    private static final String TSB_TICK_MARKS_DRAWABLE = "tsb_tick_marks_drawable";
    private static final String TSB_TICK_MARKS_DRAWABLE_SELECTOR = "tsb_tick_marks_drawable_selector";
    private static final String TSB_TICK_MARKS_SWEPT_HIDE = "tsb_tick_marks_swept_hide";
    private static final String TSB_TICK_MARKS_ENDS_HIDE = "tsb_tick_marks_ends_hide";
    private static final String TSB_SHOW_TICK_TEXTS = "tsb_show_tick_texts";
    private static final String TSB_TICK_TEXTS_SIZE = "tsb_tick_texts_size";
    private static final String TSB_TICK_TEXTS_ARRAY = "tsb_tick_texts_array";
    private static final String TSB_TICK_TEXTS_COLOR = "tsb_tick_texts_color";
    private static final String TSB_TICK_TEXTS_COLOR_SELECTOR = "tsb_tick_texts_color_selector";
    private static final String TSB_TICK_MARKS_COLOR = "tsb_tick_marks_color";
    private static final String TSB_TICK_MARKS_COLOR_SELECTOR = "tsb_tick_marks_color_selector";
    private static final String TSB_THUMB_COLOR = "tsb_thumb_color";
    private static final String TSB_THUMB_COLOR_SELECTOR = "tsb_thumb_color_selector";
    private static final int THUMB_MAX_WIDTH = 30;

    private Context mContext;
    private Paint mStrokePaint = new Paint();
    private Paint mTextPaint = new Paint();
    private OnSeekChangeListener mSeekChangeListener;
    private Rect mRect;
    private float mCustomDrawableMaxHeight;
    private float lastProgress;
    private float mFaultTolerance = -1;

    //seek bar
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mMeasuredWidth;
    private int mPaddingTop;
    private float mSeekLength;
    private float mSeekBlockLength;
    private boolean mIsTouching;
    private float mMax;
    private float mMin;
    private float mProgress;
    private boolean mIsFloatProgress;
    private boolean mUserSeekable;
    private boolean mOnlyThumbDraggable;
    private boolean mSeekSmoothly;
    private float[] mProgressArr;
    private boolean mR2L;

    //tick texts
    private int mTickTextsPosition;
    private int mTickTextsHeight;
    private String[] mTickTextsArr;
    private float[] mTickTextsWidth;
    private float[] mTextCenterX;
    private float mTickTextY;
    private int mTickTextsSize;
    private Font mTextFont;
    private Color mUnselectedTextsColor;
    private Color mSelectedTextsColor;
    private Color mHoveredTextColor;
    private int mDefaultTickTextsHeight;
    private CharSequence[] mTickTextsCustomArray = {"last", "next"};

    //tick marks
    private float[] mTickMarksX;
    private int mTicksCount;
    private Color mUnSelectedTickMarksColor;
    private Color mSelectedTickMarksColor;
    private float mTickRadius;
    private PixelMap mUnselectedTickMarksPixelMap;
    private PixelMap mSelectTickMarksPixelMap;
    private Element mTickMarksDrawable;
    private int mShowTickMarksType;
    private boolean mTickMarksEndsHide;
    private boolean mTickMarksSweptHide;
    private int mTickMarksSize;
    private RectFloat mRectFloat = new RectFloat();

    //track
    private boolean mTrackRoundedCorners;
    private RectFloat mProgressTrack;
    private RectFloat mBackgroundTrack;
    private int mBackgroundTrackSize;
    private int mProgressTrackSize;
    private Color mBackgroundTrackColor;
    private Color mProgressTrackColor;
    private Color[] mSectionTrackColorArray;
    private boolean mCustomTrackSectionColorResult = false;

    //thumb
    private float mThumbRadius;
    private float mThumbTouchRadius;
    private PixelMap mThumbPixelMap;
    private Color mThumbColor;
    private int mThumbSize;
    private Element mThumbDrawable;
    private PixelMap mPressedThumbPixelMap;
    private Color mPressedThumbColor;

    //thumb text
    private int mThumbTextShowPos;
    private float mThumbTextY;
    private Color mThumbTextColor;
    private boolean mClearPadding;
    private SeekParams mSeekParams;
    private int mScale = 1;
    private boolean mAdjustAuto;
    private Color drawThumbTextColor;
    private Color drawTickTextsColor;
    private Color drawTickMarksColor;
    private Color drawTrackColor;
    private Color drawThumbColor;

    /**
     * TickSeekBar constructor.
     *
     * @param context context
     */
    public TickSeekBar(Context context) {
        super(context);
    }

    /**
     * TickSeekBar constructor.
     *
     * @param context context
     * @param attrSet attribute set
     */
    public TickSeekBar(Context context, AttrSet attrSet) {
        super(context, attrSet);
        setListeners();
        initStrokePaint();
        initAttrs(context, attrSet);
        initParams();
        initTextPaint();
    }

    /**
     * TickSeekBar constructor.
     *
     * @param context context
     * @param attrSet attribute set
     * @param styleName style name
     */
    public TickSeekBar(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        setListeners();
        initStrokePaint();
        initAttrs(context, attrSet);
        initParams();
        initTextPaint();
    }

    /**
     * TickSeekBar constructor.
     *
     * @param context context
     * @param attrSet attribute set
     * @param resId resource id
     */
    public TickSeekBar(Context context, AttrSet attrSet, int resId) {
        super(context, attrSet, resId);
        setListeners();
        initStrokePaint();
        initAttrs(context, attrSet);
        initParams();
        initTextPaint();
    }

    /**
     * TickSeekBar constructor.
     *
     * @param builder object
     */
    public TickSeekBar(Builder builder) {
        super(builder.context);
        this.mContext = builder.context;
        int defaultPadding = SizeUtils.dp2px(mContext, TickSeekBarConstants.DP_VALUE);
        setPadding(defaultPadding, getPaddingTop(), defaultPadding, getPaddingBottom());
        this.apply(builder);
        setListeners();
        initStrokePaint();
        initParams();
        initTextPaint();
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        if (getHeight() == 0) {
            setHeight((int) (mCustomDrawableMaxHeight * TickSeekBarConstants.INDEX_VALUE_FOUR));
        }
        drawTrack(canvas);
        drawTickMarks(canvas);
        drawTickTexts(canvas);
        drawThumb(canvas);
        drawThumbText(canvas);
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
        return false;
    }

    @Override
    public boolean onEstimateSize(int widthMeasureSpec, int heightMeasureSpec) {
        super.setEstimatedSize(widthMeasureSpec, heightMeasureSpec);
        int height = Math.round(mCustomDrawableMaxHeight + getPaddingTop() + getPaddingBottom());
        if (isAboveBelowText()) {
            setEstimatedSize(SizeUtils.resolveDimension(SizeUtils.dp2px(mContext,
                    TickSeekBarConstants.DENSITY_PIXEL_VALUE), widthMeasureSpec), height
                    + 2 * mTickTextsHeight);
        } else {
            setEstimatedSize(SizeUtils.resolveDimension(SizeUtils.dp2px(mContext,
                    TickSeekBarConstants.DENSITY_PIXEL_VALUE), widthMeasureSpec), height
                    + mTickTextsHeight);
        }
        initSeekBarInfo();
        refreshSeekBarLocation();
        return false;
    }

    private void initStrokePaint() {
        if (mStrokePaint == null) {
            mStrokePaint = new Paint();
        }
        if (mTrackRoundedCorners) {
            mStrokePaint.setStrokeCap(Paint.StrokeCap.ROUND_CAP);
        }
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStyle(Paint.Style.FILLANDSTROKE_STYLE);
        if (mBackgroundTrackSize > mProgressTrackSize) {
            mProgressTrackSize = mBackgroundTrackSize;
        }
    }

    private void setListeners() {
        addDrawTask(this);
        setEstimateSizeListener(this);
        setTouchEventListener(this);
        setDraggedListener(DRAG_HORIZONTAL_VERTICAL, this);
    }

    private void initAttrs(Context context, AttrSet attrs) {
        Builder builder = new Builder(context);
        this.apply(builder);
        initTextFontType(Font.MEDIUM, builder.tickTextFontType);
        setTickTextsColor(builder.tickTextsColor);
        setThumbColor(builder.thumbColor);
        setThumbTextColor(builder.thumbTextColor);

        //seekBar
        setSeekBarCustomAttributes(attrs, builder);

        //track
        setTrackCustomAttributes(attrs, builder);

        //thumb
        setThumbCustomAttributes(context, attrs, builder);

        //thumb text
        setThumbTextCustomAttributes(attrs, builder);

        //tickMarks
        setTickMarksCustomAttributes(context, attrs, builder);

        //tickTexts
        setTickTextCustomAttributes(attrs, builder);
    }

    private void setSeekBarCustomAttributes(AttrSet attrs, Builder builder) {
        mMax = (attrs.getAttr(TSB_MAX).isPresent() ? attrs.getAttr(TSB_MAX).get().getFloatValue() : builder.max);
        mMin = (attrs.getAttr(TSB_MIN).isPresent() ? attrs.getAttr(TSB_MIN).get().getFloatValue() : builder.min);
        mProgress = (attrs.getAttr(TSB_PROGRESS).isPresent() ? attrs.getAttr(TSB_PROGRESS).get().getFloatValue()
                : builder.progress);
        mIsFloatProgress = (attrs.getAttr(TSB_PROGRESS_VALUE_FLOAT).isPresent()
                ? attrs.getAttr(TSB_PROGRESS_VALUE_FLOAT).get().getBoolValue() : builder.progressValueFloat);
        mUserSeekable = (attrs.getAttr(TSB_USER_SEEKABLE).isPresent()
                ? attrs.getAttr(TSB_USER_SEEKABLE).get().getBoolValue() : builder.userSeekable);
        mClearPadding = (attrs.getAttr(TSB_CLEAR_DEFAULT_PADDING).isPresent()
                ? attrs.getAttr(TSB_CLEAR_DEFAULT_PADDING).get().getBoolValue() : builder.clearPadding);
        mOnlyThumbDraggable = (attrs.getAttr(TSB_ONLY_THUMB_DRAGGABLE).isPresent()
                ? attrs.getAttr(TSB_ONLY_THUMB_DRAGGABLE).get().getBoolValue() : builder.onlyThumbDraggable);
        mSeekSmoothly = (attrs.getAttr(TSB_SEEK_SMOOTHLY).isPresent()
                ? attrs.getAttr(TSB_SEEK_SMOOTHLY).get().getBoolValue() : builder.seekSmoothly);
        mR2L = (attrs.getAttr(TSB_R2L).isPresent() ? attrs.getAttr(TSB_R2L).get().getBoolValue() : builder.r2l);
    }

    private void setTrackCustomAttributes(AttrSet attrs, Builder builder) {
        mBackgroundTrackSize = (attrs.getAttr(TSB_TRACK_BACKGROUND_SIZE).isPresent()
                ? attrs.getAttr(TSB_TRACK_BACKGROUND_SIZE).get().getDimensionValue() : builder.trackBackgroundSize);
        mProgressTrackSize = (attrs.getAttr(TSB_TRACK_PROGRESS_SIZE).isPresent()
                ? attrs.getAttr(TSB_TRACK_PROGRESS_SIZE).get().getDimensionValue() : builder.trackProgressSize);
        mBackgroundTrackColor = (attrs.getAttr(TSB_TRACK_BACKGROUND_COLOR).isPresent()
                ? attrs.getAttr(TSB_TRACK_BACKGROUND_COLOR).get().getColorValue() : builder.trackBackgroundColor);
        mProgressTrackColor = (attrs.getAttr(TSB_TRACK_PROGRESS_COLOR).isPresent()
                ? attrs.getAttr(TSB_TRACK_PROGRESS_COLOR).get().getColorValue() : builder.trackProgressColor);
        mTrackRoundedCorners = (attrs.getAttr(TSB_TRACK_ROUNDED_CORNERS).isPresent()
                ? attrs.getAttr(TSB_TRACK_ROUNDED_CORNERS).get().getBoolValue() : builder.trackRoundedCorners);
    }

    private void setThumbCustomAttributes(Context context, AttrSet attrs, Builder builder) {
        mThumbSize = (attrs.getAttr(TSB_THUMB_SIZE).isPresent()
                ? attrs.getAttr(TSB_THUMB_SIZE).get().getDimensionValue() : builder.thumbSize);
        mThumbDrawable = (attrs.getAttr(TSB_THUMB_DRAWABLE).isPresent()
                ? attrs.getAttr(TSB_THUMB_DRAWABLE).get().getElement() : builder.thumbDrawable);
        String thumbDrawableString = (attrs.getAttr(TSB_THUMB_DRAWABLE_SELECTOR).isPresent()
                ? attrs.getAttr(TSB_THUMB_DRAWABLE_SELECTOR).get().getStringValue() : "");
        if (!TextTool.isNullOrEmpty(thumbDrawableString) && thumbDrawableString.contains("selector")) {
            mThumbDrawable = StateElementUtil.initThumbDrawableState(context);
        }
        if (attrs.getAttr(TSB_THUMB_COLOR).isPresent()) {
            setThumbColor(attrs.getAttr(TSB_THUMB_COLOR).get().getColorValue());
        }
        if (attrs.getAttr(TSB_THUMB_COLOR_SELECTOR).isPresent()) {
            initThumbColor(StateElementUtil.initThumbColorState(context), builder.thumbColor);
        }
        mAdjustAuto = (attrs.getAttr(TSB_THUMB_ADJUST_AUTO).isPresent()
                ? attrs.getAttr(TSB_THUMB_ADJUST_AUTO).get().getBoolValue() : builder.thumbAutoAdjust);
    }

    private void setThumbTextCustomAttributes(AttrSet attrs, Builder builder) {
        String thumbTextShowPosString = (attrs.getAttr(TSB_SHOW_THUMB_TEXT).isPresent()
                ? attrs.getAttr(TSB_SHOW_THUMB_TEXT).get().getStringValue() : "None");
        setThumbTextShowPos(thumbTextShowPosString);
        mThumbTextColor = (attrs.getAttr(TSB_THUMB_TEXT_COLOR).isPresent()
                ? attrs.getAttr(TSB_THUMB_TEXT_COLOR).get().getColorValue() : builder.thumbTextColor);
        setThumbTextColor(mThumbTextColor);
    }

    private void setTickMarksCustomAttributes(Context context, AttrSet attrs, Builder builder) {
        mTicksCount = (attrs.getAttr(TSB_TICKS_COUNT).isPresent()
                ? attrs.getAttr(TSB_TICKS_COUNT).get().getIntegerValue() : builder.tickCount);
        if (attrs.getAttr(TSB_SHOW_TICK_MARKS_TYPE).isPresent()) {
            setTickMarksType(attrs.getAttr(TSB_SHOW_TICK_MARKS_TYPE).get().getStringValue());
        }
        mTickMarksSize = (attrs.getAttr(TSB_TICK_MARKS_SIZE).isPresent()
                ? attrs.getAttr(TSB_TICK_MARKS_SIZE).get().getDimensionValue() : builder.tickMarksSize);
        if (attrs.getAttr(TSB_TICK_MARKS_COLOR).isPresent()) {
            setTickMarksColor(attrs.getAttr(TSB_TICK_MARKS_COLOR).get().getColorValue());
        }
        if (attrs.getAttr(TSB_TICK_MARKS_COLOR_SELECTOR).isPresent()) {
            initTickMarksColor((StateElement) attrs.getAttr(TSB_TICK_MARKS_COLOR_SELECTOR).get().getElement(),
                    builder.tickMarksColor);
        }
        mTickMarksDrawable = (attrs.getAttr(TSB_TICK_MARKS_DRAWABLE).isPresent()
                ? attrs.getAttr(TSB_TICK_MARKS_DRAWABLE).get().getElement() : builder.tickMarksDrawable);
        String tickMarksDrawableString = (attrs.getAttr(TSB_TICK_MARKS_DRAWABLE_SELECTOR).isPresent()
                ? attrs.getAttr(TSB_TICK_MARKS_DRAWABLE_SELECTOR).get().getStringValue() : "");
        if (!TextTool.isNullOrEmpty(tickMarksDrawableString) && tickMarksDrawableString.contains("selector")) {
            mTickMarksDrawable = StateElementUtil.initTickMarksDrawableState(context);
        }
        mTickMarksSweptHide = (attrs.getAttr(TSB_TICK_MARKS_SWEPT_HIDE).isPresent()
                ? attrs.getAttr(TSB_TICK_MARKS_SWEPT_HIDE).get().getBoolValue() : builder.tickMarksSweptHide);
        mTickMarksEndsHide = (attrs.getAttr(TSB_TICK_MARKS_ENDS_HIDE).isPresent()
                ? attrs.getAttr(TSB_TICK_MARKS_ENDS_HIDE).get().getBoolValue() : builder.tickMarksEndsHide);
    }

    private void setTickTextCustomAttributes(AttrSet attrs, Builder builder) {
        String tickTextsPositionString = (attrs.getAttr(TSB_SHOW_TICK_TEXTS).isPresent()
                ? attrs.getAttr(TSB_SHOW_TICK_TEXTS).get().getStringValue() : "none");
        setTickTextShowPos(tickTextsPositionString);
        mTickTextsSize = (attrs.getAttr(TSB_TICK_TEXTS_SIZE).isPresent()
                ? attrs.getAttr(TSB_TICK_TEXTS_SIZE).get().getDimensionValue() : builder.tickTextsSize);
        if (attrs.getAttr(TSB_TICK_TEXTS_COLOR).isPresent()) {
            setTickTextsColor(attrs.getAttr(TSB_TICK_TEXTS_COLOR).get().getColorValue());
        }
        if (attrs.getAttr(TSB_TICK_TEXTS_COLOR_SELECTOR).isPresent()) {
            initTickTextsColor(StateElementUtil.initTickTextColorState(2), builder.tickTextsColor);
        }
        if (attrs.getAttr(TSB_TICK_TEXTS_ARRAY).isPresent()) {
            String tsbTickTextArrayValue = attrs.getAttr(TSB_TICK_TEXTS_ARRAY).get().getStringValue();
            if (!TextTool.isNullOrEmpty(tsbTickTextArrayValue) && tsbTickTextArrayValue.contains("strarray")) {
                setTickTextCustomArray(tsbTickTextArrayValue);
            }
        }
    }

    private void setTickTextCustomArray(String customArrayValue) {
        if (customArrayValue.contains("last_next_length_2")) {
            mTickTextsCustomArray = getContext().getStringArray(ResourceTable.Strarray_last_next_length_2);
        } else if (customArrayValue.contains("last_next_length_")) {
            mTickTextsCustomArray = getContext().getStringArray(ResourceTable.Strarray_last_next_length_5);
        } else if (customArrayValue.contains("tick_below_text_length_5")) {
            mTickTextsCustomArray = getContext().getStringArray(ResourceTable.Strarray_tick_below_text_length_5);
        } else if (customArrayValue.contains("irregular_length_6")) {
            mTickTextsCustomArray = getContext().getStringArray(ResourceTable.Strarray_irregular_length_6);
        } else if (customArrayValue.contains("small_normal_middle_large_length_7")) {
            mTickTextsCustomArray = getContext()
                    .getStringArray(ResourceTable.Strarray_small_normal_middle_large_length_7);
        }
    }

    private void setThumbTextShowPos(String thumbTextShowPos) {
        if (!TextTool.isNullOrEmpty(thumbTextShowPos)) {
            if (thumbTextShowPos.equalsIgnoreCase("above")) {
                mThumbTextShowPos = TextPosition.ABOVE;
            } else if (thumbTextShowPos.equalsIgnoreCase("below")) {
                mThumbTextShowPos = TextPosition.BELOW;
            } else {
                mThumbTextShowPos = TextPosition.NONE;
            }
            LogUtil.info(TAG, "mThumbTextShowPos - " + mThumbTextShowPos);
        }
    }

    private void setTickTextShowPos(String tickTextShowPos) {
        if (!TextTool.isNullOrEmpty(tickTextShowPos)) {
            if (tickTextShowPos.equalsIgnoreCase("above")) {
                mTickTextsPosition = TextPosition.ABOVE;
            } else if (tickTextShowPos.equalsIgnoreCase("below")) {
                mTickTextsPosition = TextPosition.BELOW;
            } else {
                mTickTextsPosition = TextPosition.NONE;
            }
            LogUtil.info(TAG, "mTickTextsPosition - " + mTickTextsPosition);
        }
    }

    private void setTickMarksType(String tickMarksType) {
        if (!TextTool.isNullOrEmpty(tickMarksType)) {
            if (tickMarksType.equalsIgnoreCase("oval")) {
                mShowTickMarksType = TickMarkType.OVAL;
            } else if (tickMarksType.equalsIgnoreCase("square")) {
                mShowTickMarksType = TickMarkType.SQUARE;
            } else if (tickMarksType.equalsIgnoreCase("divider")) {
                mShowTickMarksType = TickMarkType.DIVIDER;
            } else {
                mShowTickMarksType = TickMarkType.NONE;
            }
            LogUtil.info(TAG, "mShowTickMarksType - " + mShowTickMarksType);
        }
    }

    private void initTextFontType(int fontType, Font defaultFontType) {
        switch (fontType) {
            case 0:
                mTextFont = Font.DEFAULT;
                break;
            case 1:
                mTextFont = Font.MONOSPACE;
                break;
            case 2:
                mTextFont = Font.SANS_SERIF;
                break;
            case TickSeekBarConstants.INDEX_VALUE_THREE:
                mTextFont = Font.SERIF;
                break;
            default:
                if (defaultFontType == null) {
                    mTextFont = Font.DEFAULT;
                    break;
                }
                mTextFont = defaultFontType;
                break;
        }
    }

    private void initParams() {
        if (mTicksCount < 0 || mTicksCount > TickSeekBarConstants.MAX_TICK_COUNT) {
            throw new IllegalArgumentException("the Argument: TICK COUNT must be limited between 0-50, "
                    + "Now is " + mTicksCount);
        }
        initProgressRangeValue();
        if (mBackgroundTrackSize > mProgressTrackSize) {
            mBackgroundTrackSize = mProgressTrackSize;
        }
        if (mThumbDrawable == null) {
            mThumbRadius = mThumbSize / 2.0f;
            mThumbTouchRadius = mThumbRadius * TickSeekBarConstants.THUMB_RADIUS_FACTOR;
        } else {
            mThumbRadius = Math.min(SizeUtils.dp2px(mContext, THUMB_MAX_WIDTH), mThumbSize) / 2.0f;
            mThumbTouchRadius = mThumbRadius;
        }
        if (mTickMarksDrawable == null) {
            mTickRadius = mTickMarksSize / 2.0f;
        } else {
            mTickRadius = Math.min(SizeUtils.dp2px(mContext, THUMB_MAX_WIDTH), mTickMarksSize) / 2.0f;
        }
        mCustomDrawableMaxHeight = Math.max(mThumbTouchRadius, mTickRadius) * 2.0f;
        initStrokePaint();
        measureTickTextsBonds();
        lastProgress = mProgress;
        collectTicksInfo();
        mProgressTrack = new RectFloat();
        mBackgroundTrack = new RectFloat();
        initDefaultPadding();
    }

    private void initProgressRangeValue() {
        if (mMax < mMin) {
            throw new IllegalArgumentException("the Argument: MAX's value must be larger than MIN's.");
        }
        if (mProgress < mMin) {
            mProgress = mMin;
        }
        if (mProgress > mMax) {
            mProgress = mMax;
        }
    }

    private void initDefaultPadding() {
        LogUtil.info(TAG, "initDefaultPadding() begin");
        if (!mClearPadding) {
            int normalPadding = SizeUtils.dp2px(mContext, TickSeekBarConstants.DP_VALUE);
            if (getPaddingLeft() == 0) {
                setPadding(normalPadding, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            }
            if (getPaddingRight() == 0) {
                setPadding(getPaddingLeft(), getPaddingTop(), normalPadding, getPaddingBottom());
            }
        }
        LogUtil.info(TAG, "initDefaultPadding() end");
    }

    private void collectTicksInfo() {
        LogUtil.info(TAG, "collectTicksInfo() begin");
        if (mTicksCount < 0 || mTicksCount > TickSeekBarConstants.MAX_TICK_COUNT) {
            throw new IllegalArgumentException("the Argument: TICK COUNT must be limited between (0-50), Now is "
                    + mTicksCount);
        }
        if (mTicksCount != 0) {
            mTickMarksX = new float[mTicksCount];
            if (mTickTextsPosition != TextPosition.NONE) {
                mTextCenterX = new float[mTicksCount];
                mTickTextsWidth = new float[mTicksCount];
            }
            mProgressArr = new float[mTicksCount];
            for (int i = 0; i < mProgressArr.length; i++) {
                mProgressArr[i] = mMin + i * (mMax - mMin) / ((mTicksCount - 1) > 0 ? (mTicksCount - 1) : 1);
            }
        }
        LogUtil.info(TAG, "collectTicksInfo() end");
    }

    private void initTrackLocation() {
        LogUtil.info(TAG, "initTrackLocation() begin");
        if (mR2L) {
            mBackgroundTrack.left = mPaddingLeft;
            if (hasAboveText()) {
                mBackgroundTrack.top = mPaddingTop + mThumbTouchRadius + mDefaultTickTextsHeight
                        + SizeUtils.dp2px(mContext, TickSeekBarConstants.BACKGROUND_TRACK_DP_VALUE);
            } else {
                mBackgroundTrack.top = mPaddingTop + mThumbTouchRadius;
            }
            mBackgroundTrack.right = mPaddingLeft + mSeekLength * (1.0f - (mProgress - mMin) / (mMax - mMin));
            mBackgroundTrack.bottom = mBackgroundTrack.top;
            mProgressTrack.left = mBackgroundTrack.right;
            mProgressTrack.top = mBackgroundTrack.top;
            mProgressTrack.right = (float) mMeasuredWidth - mPaddingRight;
            mProgressTrack.bottom = mBackgroundTrack.bottom;
        } else {
            mProgressTrack.left = mPaddingLeft;
            if (hasAboveText()) {
                mProgressTrack.top = mPaddingTop + mThumbTouchRadius + mDefaultTickTextsHeight
                        + SizeUtils.dp2px(mContext, TickSeekBarConstants.PROGRESS_TRACK_DP_VALUE);
            } else {
                mProgressTrack.top = mPaddingTop + mThumbTouchRadius;
            }
            mProgressTrack.right = (mProgress - mMin) * mSeekLength / (mMax - mMin) + mPaddingLeft;
            mProgressTrack.bottom = mProgressTrack.top;
            mBackgroundTrack.left = mProgressTrack.right;
            mBackgroundTrack.top = mProgressTrack.bottom;
            mBackgroundTrack.right = (float) mMeasuredWidth - mPaddingRight;
            mBackgroundTrack.bottom = mProgressTrack.bottom;
        }
        LogUtil.info(TAG, "initTrackLocation() end");
    }

    private void initSeekBarInfo() {
        LogUtil.info(TAG, "initSeekBarInfo() begin");
        mMeasuredWidth = getEstimatedWidth();
        mPaddingLeft = getPaddingStart();
        mPaddingRight = getPaddingEnd();
        mPaddingTop = getPaddingTop();
        mSeekLength = (float) mMeasuredWidth - mPaddingLeft - mPaddingRight;
        mSeekBlockLength = mSeekLength / (mTicksCount - 1 > 0 ? mTicksCount - 1 : 1);
        LogUtil.info(TAG, "initSeekBarInfo() end");
    }

    private void measureTickTextsBonds() {
        LogUtil.info(TAG, "measureTickTextsBonds() begin");
        if (needDrawText()) {
            initTextPaint();
            mTextPaint.setFont(mTextFont);
            mRect = mTextPaint.getTextBounds("j");
            mTickTextsHeight = mRect.getHeight() + SizeUtils.dp2px(mContext,
                    TickSeekBarConstants.TICK_TEXTS_HEIGHT_DP_VALUE);
        }
        LogUtil.info(TAG, "measureTickTextsBonds() end");
    }

    private void refreshSeekBarLocation() {
        LogUtil.info(TAG, "refreshSeekBarLocation() begin");
        initTrackLocation();
        initTickTextsYLocation();
        if (mTickMarksX == null) {
            return;
        }
        initTextsArray();

        //adjust thumb auto,so find out the closest mProgressArr in the mProgressArr array and replace it.
        //it is not necessary to adjust thumb while count is less than 3.
        if (mTicksCount > 2) {
            mProgress = mProgressArr[getClosestIndex()];
            lastProgress = mProgress;
        }
        LogUtil.info(TAG, "refreshSeekBarLocation() end");
    }

    private int getClosestIndex() {
        LogUtil.info(TAG, "getClosestIndex() begin");
        int closestIndex = 0;
        float amplitude = Math.abs(mMax - mMin);
        for (int i = 0; i < mProgressArr.length; i++) {
            float amplitudeTemp = Math.abs(mProgressArr[i] - mProgress);
            if (amplitudeTemp <= amplitude) {
                amplitude = amplitudeTemp;
                closestIndex = i;
            }
        }
        return closestIndex;
    }

    private boolean hasBelowText() {
        return (mTicksCount != 0 && mTickTextsPosition == TextPosition.BELOW)
                || mThumbTextShowPos == TextPosition.BELOW;
    }

    private boolean hasAboveText() {
        return (mTicksCount != 0 && mTickTextsPosition == TextPosition.ABOVE)
                || mThumbTextShowPos == TextPosition.ABOVE;
    }

    private boolean isAboveBelowText() {
        return isTickAboveThumbBelow() || isTickBelowThumbAbove();
    }

    private boolean isTickAboveThumbBelow() {
        return ((mTicksCount != 0 && mTickTextsPosition == TextPosition.ABOVE)
                && mThumbTextShowPos == TextPosition.BELOW);
    }

    private boolean isTickBelowThumbAbove() {
        return ((mTicksCount != 0 && mTickTextsPosition == TextPosition.BELOW)
                && mThumbTextShowPos == TextPosition.ABOVE);
    }

    private void initTextsArray() {
        LogUtil.info(TAG, "initTextsArray() begin");
        if (mTickMarksX == null) {
            return;
        }
        if (mTickTextsPosition != TextPosition.NONE) {
            mTickTextsArr = new String[mTicksCount];
        }
        for (int i = 0; i < mTickMarksX.length; i++) {
            if (mTickTextsPosition != TextPosition.NONE) {
                mTickTextsArr[i] = getTickTextByPosition(i);
                mTextPaint.getTextBounds(mTickTextsArr[i]);
                mTickTextsWidth[i] = mRect.getWidth();
                mTextCenterX[i] = mPaddingLeft + mSeekBlockLength * i;
            }
            mTickMarksX[i] = mPaddingLeft + mSeekBlockLength * i;
        }
        LogUtil.info(TAG, "initTextsArray() end");
    }

    private void initTextPaint() {
        if (mTextPaint == null) {
            mTextPaint = new Paint();
        }
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(TextAlignment.CENTER);
        mTextPaint.setTextSize(mTickTextsSize);
        if (mRect == null) {
            mRect = new Rect();
        }
    }

    private void initTickTextsYLocation() {
        if (needDrawText()) {
            mRect = mTextPaint.getTextBounds("j");
            mDefaultTickTextsHeight = mRect.getHeight();
            if (isAboveBelowText()) {
                if (mTickTextsPosition == TextPosition.BELOW) {
                    mThumbTextY = mPaddingTop + (float) Math.round(mDefaultTickTextsHeight - mTextPaint.descent())
                            + SizeUtils.dp2px(mContext, TickSeekBarConstants.THUMB_TEXT_Y_DP_VALUE);
                    mTickTextY = mTickTextsHeight + mPaddingTop + mCustomDrawableMaxHeight
                            + Math.round(mDefaultTickTextsHeight - mTextPaint.descent()) + SizeUtils.dp2px(mContext,
                            TickSeekBarConstants.TICK_TEXT_Y_DP_VALUE);
                } else {
                    mTickTextY = mPaddingTop + (float) Math.round(mDefaultTickTextsHeight - mTextPaint.descent())
                            + SizeUtils.dp2px(mContext, TickSeekBarConstants.TICK_TEXT_Y_DP_VALUE);
                    mThumbTextY = mTickTextsHeight + mPaddingTop + mCustomDrawableMaxHeight
                            + Math.round(mDefaultTickTextsHeight - mTextPaint.descent()) + SizeUtils.dp2px(mContext,
                            TickSeekBarConstants.THUMB_TEXT_Y_DP_VALUE);
                }
                return;
            }
            if (hasBelowText()) {
                mTickTextY = mPaddingTop + mCustomDrawableMaxHeight + Math.round(mDefaultTickTextsHeight
                        - mTextPaint.descent()) + SizeUtils.dp2px(mContext, TickSeekBarConstants.TICK_TEXT_Y_DP_VALUE);
            } else if (hasAboveText()) {
                mTickTextY = mPaddingTop + (float) Math.round(mDefaultTickTextsHeight - mTextPaint.descent())
                        + SizeUtils.dp2px(mContext, TickSeekBarConstants.TICK_TEXT_Y_DP_VALUE);
            }
            mThumbTextY = mTickTextY;
        }
    }

    /**
     * initial the color for the tick texts.
     *
     * <p>NOTICE: make sure the format of color selector you set is right.
     * int[][] states = colorStateList.getStates();
     * (1) if the states.length == 1,the way you set the tick texts' color like :
     * app:isb_tick_text_color="#XXXXXX"  or
     * app:isb_tick_text_color="@color/color_name" ;
     *
     * <p>(2) if the states.length == 3,the way you set the tick texts' color like :
     * app:isb_tick_text_color="@color/selector_color_file_name". the file(located at res/color/)'s format should like:
     *
     * <p><?xml version="1.0" encoding="utf-8"?>
     * <selector xmlns:android="http://schemas.android.com/apk/res/android">
     * <item android:color="#555555" android:state_selected="true" />  <!--this color is for texts those are at left side of thumb-->
     * <item android:color="#FF4081" android:state_hovered="true" />     <!--for thumb text-->
     * <item android:color="#555555"/>                                 <!--for texts those are at right side of thumb-->
     * </selector>
     *
     * <p>(3) if the states.length == other, the way you set is not support.
     */
    private void initTickTextsColor(StateElement colorStateList, Color defaultColor) {
        LogUtil.info(TAG, "initTickTextsColor() begin - " + colorStateList);
        if (colorStateList == null) {
            mUnselectedTextsColor = defaultColor;
            mSelectedTextsColor = mUnselectedTextsColor;
            mHoveredTextColor = mUnselectedTextsColor;
            return;
        }
        int stateCount = colorStateList.getStateCount();
        if (stateCount == 0) {
            mUnselectedTextsColor = defaultColor;
            mSelectedTextsColor = mUnselectedTextsColor;
            mHoveredTextColor = mUnselectedTextsColor;
            return;
        }
        if (stateCount == 1) {
            mUnselectedTextsColor = new Color(getContext().getColor(ResourceTable.Color_color_gray));
            mSelectedTextsColor = mUnselectedTextsColor;
            mHoveredTextColor = mUnselectedTextsColor;
        } else if (stateCount == 2) {
            mUnselectedTextsColor = new Color(getContext().getColor(ResourceTable.Color_color_gray));
            for (int i = 0; i < stateCount; i++) {
                int[] stateSet = colorStateList.getStateSet(i);
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
            }
        } else {
            LogUtil.info(TAG, "color state not matched");
        }
        LogUtil.info(TAG, "initTickTextsColor() end");
    }

    /**
     * initial the color for the thumb.
     *
     * <p>NOTICE: make sure the format of color selector you set is right.
     * int[][] states = colorStateList.getStates();
     * (1) if the states.length == 1,the way you set the thumb color like :
     * app:isb_thumb_color="#XXXXXX"  or
     * app:isb_thumb_color="@color/color_name" ;
     *
     * <p>(2) if the states.length == 3,the way you set the thumb color like :
     * app:isb_thumb_color="@color/selector_color_file_name". the file(located at res/color/)'s format should like:
     *
     * <p><?xml version="1.0" encoding="utf-8"?>
     * <selector xmlns:android="http://schemas.android.com/apk/res/android">
     * <item android:color="#555555" android:state_pressed="true" />  <!--this color is for thumb which is at pressing status-->
     * <item android:color="#555555"/>                                <!--for thumb which is at normal status-->
     * </selector>
     *
     * <p>(3) if the states.length == other, the color's format you set is not support.
     */
    private void initThumbColor(StateElement colorStateList, Color defaultColor) {
        LogUtil.info(TAG, "initThumbColor() begin - " + colorStateList);

        //if you didn't set the thumb color, set a default color.
        if (colorStateList == null) {
            mThumbColor = defaultColor;
            mPressedThumbColor = mThumbColor;
            return;
        }
        int stateCount = colorStateList.getStateCount();
        if (stateCount == 1) {
            mThumbColor = new Color(getContext().getColor(ResourceTable.Color_colorAccent));
            mPressedThumbColor = mThumbColor;
        } else if (stateCount == 2) {
            setThumbColorValues(colorStateList, stateCount);
        } else {
            //the color selector file was set by a wrong format , please see above to correct.
            throw new IllegalArgumentException("the selector color file you set for the argument: isb_thumb_color is in wrong format.");
        }
    }

    private void setThumbColorValues(StateElement colorStateList, int stateCount) {
        for (int i = 0; i < stateCount; i++) {
            int[] stateSet = colorStateList.getStateSet(i);
            if (stateSet.length == 0) { //didn't have state,so just get color.
                ShapeElement element = (ShapeElement) colorStateList.getStateElement(i);
                RgbColor[] rgbColors = element.getRgbColors();
                if (rgbColors != null && rgbColors.length > 0) {
                    mThumbColor = new Color(rgbColors[0].asRgbaInt());
                }
                continue;
            }
            switch (stateSet[0]) {
                case COMPONENT_STATE_PRESSED:
                    ShapeElement element = (ShapeElement) colorStateList.getStateElement(i);
                    RgbColor[] rgbColors = element.getRgbColors();
                    if (rgbColors != null && rgbColors.length > 0) {
                        mPressedThumbColor = new Color(rgbColors[0].asRgbaInt());
                    }
                    break;
                default:
            }
        }
    }

    /**
     * initial the PixelMap for the thumb.
     *
     * <p>NOTICE: make sure the format of drawable selector file you set is right.
     * int stateCount = listDrawable.getStateCount();
     * (1) if the drawable instanceof PixelMapDrawable,the way you set like :
     * app:isb_thumb_drawable="@drawable/ic_launcher"
     *
     * <p>(2) if the drawable instanceof StateListDrawable,the way you set like :
     * app:isb_thumb_drawable="@drawable/selector_thumb_drawable". the file(located at res/drawable/)'s format should like:
     *
     * <p><?xml version="1.0" encoding="utf-8"?>
     * <selector xmlns:android="http://schemas.android.com/apk/res/android">
     * <item android:drawable="@drawable/ic_launcher" android:state_pressed="true" />  <!--this drawable is for thumb when pressing-->
     * <item android:drawable="@drawable/ic_launcher_round" />  <!--for thumb when normal-->
     * </selector>
     */
    private void initThumbPixelMap() {
        LogUtil.info(TAG, "initThumbPixelMap() begin");
        if (mThumbDrawable == null) {
            return;
        }
        if (mThumbDrawable instanceof StateElement) {
            try {
                setThumbPixelMapValues();
            } catch (IllegalArgumentException e) {
                mThumbPixelMap = getPixelMapFromDrawable(mThumbDrawable, true);
                mPressedThumbPixelMap = mThumbPixelMap;
            }
        } else {
            mThumbPixelMap = getPixelMapFromResId(true, false);
            mPressedThumbPixelMap = mThumbPixelMap;
        }
        LogUtil.info(TAG, "initThumbPixelMap() end");
    }

    private void setThumbPixelMapValues() {
        StateElement listDrawable = (StateElement) mThumbDrawable;
        int stateCount = listDrawable.getStateCount();
        if (stateCount == 2) {
            for (int i = 0; i < stateCount; i++) {
                int[] stateSet = listDrawable.getStateSet(i);
                if (stateSet.length > 0) {
                    if (stateSet[0] == COMPONENT_STATE_PRESSED) {
                        PixelMapElement stateDrawable = (PixelMapElement) listDrawable.getStateElement(i);
                        mPressedThumbPixelMap = stateDrawable.getPixelMap();
                    } else {

                        //please check your selector drawable's format, please see above to correct.
                        throw new IllegalArgumentException("the state of the selector thumb drawable is wrong!");
                    }
                } else {
                    PixelMapElement stateDrawable = (PixelMapElement) listDrawable.getStateElement(i);
                    mThumbPixelMap = stateDrawable.getPixelMap();
                }
            }
        } else {

            //please check your selector drawable's format, please see above to correct.
            throw new IllegalArgumentException("the format of the selector thumb drawable is wrong!");
        }
    }

    /**
     * initial the PixelMap for the thickMarks.
     *
     * <p>NOTICE: make sure the format of drawable selector file you set is right.
     * int stateCount = listDrawable.getStateCount();
     * (1) if the drawable instanceof PixelMapDrawable,the way you set like :
     * app:isb_tick_marks_drawable="@drawable/ic_launcher"
     *
     * <p>(2) if the drawable instanceof StateListDrawable,the way you set like :
     * app:isb_tick_marks_drawable="@drawable/selector_thumb_drawable". the file(located at res/drawable/)'s format should like:
     *
     * <p><?xml version="1.0" encoding="utf-8"?>
     * <selector xmlns:android="http://schemas.andtroid.com/apk/res/android">
     * <item android:drawable="@drawable/ic_launcher" android:state_selected="true" />  <!--this drawable is for thickMarks which thumb swept-->
     * <item android:drawable="@drawable/ic_launcher_round" />  <!--for thickMarks which thumb haven't reached-->
     * </selector>
     */
    private void initTickMarksPixelMap() {
        LogUtil.info(TAG, "initTickMarksPixelMap() begin");
        if (mTickMarksDrawable instanceof StateElement) {
            StateElement listDrawable = (StateElement) mTickMarksDrawable;
            try {
                int stateCount = listDrawable.getStateCount();
                if (stateCount == 2) {
                    setTickMarksPixelMapValues(listDrawable, stateCount);
                } else {

                    //please check your selector drawable's format, please see above to correct.
                    throw new IllegalArgumentException("the format of the selector TickMarks drawable is wrong!");
                }
            } catch (IllegalArgumentException e) {
                mUnselectedTickMarksPixelMap = getPixelMapFromDrawable(mTickMarksDrawable, false);
                mSelectTickMarksPixelMap = mUnselectedTickMarksPixelMap;
            }
        } else {
            mUnselectedTickMarksPixelMap = getPixelMapFromResId(false, false);
            mSelectTickMarksPixelMap = mUnselectedTickMarksPixelMap;
        }
        LogUtil.info(TAG, "initTickMarksPixelMap() end");
    }

    private void setTickMarksPixelMapValues(StateElement listDrawable, int stateCount) {
        for (int i = 0; i < stateCount; i++) {
            int[] stateSet = listDrawable.getStateSet(i);
            if (stateSet.length > 0) {
                if (stateSet[0] == COMPONENT_STATE_SELECTED || stateSet[0] == COMPONENT_STATE_FOCUSED) {
                    PixelMapElement stateDrawable = (PixelMapElement) listDrawable.getStateElement(i);
                    mSelectTickMarksPixelMap = stateDrawable.getPixelMap();
                } else {

                    //please check your selector drawable's format, please see above to correct.
                    throw new IllegalArgumentException("the state of the selector TickMarks drawable is wrong!");
                }
            } else {
                PixelMapElement stateDrawable = (PixelMapElement) listDrawable.getStateElement(i);
                mUnselectedTickMarksPixelMap = stateDrawable.getPixelMap();
            }
        }
    }

    /**
     * initial the color for the tick masks
     *
     * <p>NOTICE: make sure the format of color selector you set is right.
     * int[][] states = colorStateList.getStates();
     * (1) if the states.length == 1,the way you set the tick marks' color like :
     * app:isb_tick_marks_color="#XXXXXX"  or
     * app:isb_tick_marks_color="@color/color_name" ;
     *
     * <p>(2) if the states.length == 2,the way you set the tick marks' color like :
     * app:isb_tick_marks_color="@color/selector_color_file_name". the file(located at res/color/)'s format should like:
     *
     * <p><?xml version="1.0" encoding="utf-8"?>
     * <selector xmlns:android="http://schemas.android.com/apk/res/android">
     * <item android:color="#555555" android:state_selected="true" />  <!--this color is for marks those are at left side of thumb-->
     * <item android:color="#555555"/>                                 <!--for marks those are at right side of thumb-->
     * </selector>
     *
     * <p>(3) if the states.length == other, the way you set is not support.
     */
    private void initTickMarksColor(StateElement colorStateList, Color defaultColor) {
        LogUtil.info(TAG, "initTickMarksColor()  begin: " + colorStateList);

        //if you didn't set the tick's text color, set a default selector color file.
        if (colorStateList == null) {
            mSelectedTickMarksColor = defaultColor;
            mUnSelectedTickMarksColor = mSelectedTickMarksColor;
            return;
        }
        int stateCount = colorStateList.getStateCount();
        if (stateCount == 0) {
            mSelectedTickMarksColor = defaultColor;
            mUnSelectedTickMarksColor = mSelectedTickMarksColor;
            return;
        }
        if (stateCount == 1) {
            mSelectedTickMarksColor = new Color(getContext().getColor(ResourceTable.Color_colorPrimary));
            mUnSelectedTickMarksColor = new Color(getContext().getColor(ResourceTable.Color_color_gray));
        } else if (stateCount == 2) {
            setTickMarksColorValues(colorStateList, stateCount);
        }
        LogUtil.info(TAG, "initTickMarksColor(StateElement colorStateList, Color defaultColor)  end");
    }

    private void setTickMarksColorValues(StateElement colorStateList, int stateCount) {
        for (int i = 0; i < stateCount; i++) {
            int[] stateSet = colorStateList.getStateSet(i);
            ShapeElement shapeElement = (ShapeElement) colorStateList.getStateElement(i);
            if (stateSet.length > 0 && stateSet[0] == COMPONENT_STATE_SELECTED) {
                RgbColor[] rgbColors = shapeElement.getRgbColors();
                if (rgbColors != null && rgbColors.length > 0) {
                    mSelectedTickMarksColor = new Color(rgbColors[0].asRgbaInt());
                    mUnSelectedTickMarksColor = new Color(getContext().getColor(ResourceTable.Color_color_gray));
                }
            } else {
                mUnSelectedTickMarksColor = new Color(getContext().getColor(ResourceTable.Color_color_gray));
                mSelectedTickMarksColor = mUnSelectedTickMarksColor;
            }
        }
    }

    private PixelMap getPixelMapFromResId(boolean isThumb, boolean isStateChanged) {
        int resId = ohos.global.systemres.ResourceTable.Media_ic_app;
        if (isStateChanged) {
            resId = ResourceTable.Media_ic_launcher_round;
        }
        ResourceManager resourceManager = getContext().getResourceManager();
        Optional<PixelMap> pixelMap = Optional.empty();
        int width;
        int maxRange = SizeUtils.dp2px(mContext, THUMB_MAX_WIDTH);
        if (isThumb) {
            width = mThumbSize;
        } else {
            width = mTickMarksSize;
        }
        if (width > maxRange) {
            width = maxRange;
        }
        try {
            Resource resource = resourceManager.getResource(resId);
            if (resource == null) {
                return null;
            }
            pixelMap = PixelMapUtil.preparePixelMap(resource, width);
            resource.close();
        } catch (IOException | NotExistException e) {
            LogUtil.error(TAG, "set imageview pixelmap failed, pixelmap is empty");
        }
        return pixelMap.get();
    }

    private PixelMap getPixelMapFromDrawable(Element drawable, boolean isThumb) {
        if (drawable == null) {
            return null;
        }
        int width;
        int height;
        int maxRange = SizeUtils.dp2px(mContext, THUMB_MAX_WIDTH);
        int intrinsicWidth = drawable.getWidth();
        if (intrinsicWidth > maxRange) {
            if (isThumb) {
                width = mThumbSize;
            } else {
                width = mTickMarksSize;
            }
            height = getHeightByRatio(drawable, width);

            if (width > maxRange) {
                width = maxRange;
                height = getHeightByRatio(drawable, width);
            }
        } else {
            width = drawable.getWidth();
            height = drawable.getHeight();
        }

        try {
            PixelMap pixelMap;
            PixelMap.InitializationOptions initializationOptions = new PixelMap.InitializationOptions();
            initializationOptions.size = new Size(height, width);
            initializationOptions.pixelFormat = PixelFormat.ARGB_8888;
            pixelMap = PixelMap.create(initializationOptions);
            Canvas canvas = new Canvas();
            float radius = (float) (pixelMap.getImageInfo().size.width) / 2;
            canvas.drawPixelMapHolderCircleShape(new PixelMapHolder(pixelMap), mRectFloat, 0, 0, radius);
            return pixelMap;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private String getTickTextByPosition(int index) {
        LogUtil.info(TAG, "getTickTextByPosition(int index) begin");
        if (mTickTextsCustomArray == null) {
            return getProgressString(mProgressArr[index]);
        }
        if (index < mTickTextsCustomArray.length) {
            return String.valueOf(mTickTextsCustomArray[index]);
        }
        return "";
    }

    /**
     * transfer the progress value to string type
     */
    private String getProgressString(float progress) {
        if (mIsFloatProgress) {
            return FormatUtils.fastFormat(progress, mScale);
        }
        return String.valueOf(Math.round(progress));
    }

    private int getHeightByRatio(Element drawable, int width) {
        int intrinsicWidth = drawable.getWidth();
        int intrinsicHeight = drawable.getHeight();
        return Math.round(1.0f * width * intrinsicHeight / intrinsicWidth);
    }

    private boolean needDrawText() {
        return (mTickTextsPosition != TextPosition.NONE && mTicksCount != 0) || mThumbTextShowPos != 0;
    }

    private void drawTrack(Canvas canvas) {
        LogUtil.info(TAG, "drawTrack(Canvas canvas)  begin");
        if (mCustomTrackSectionColorResult) {
            int sectionSize = mTicksCount - 1 > 0 ? mTicksCount - 1 : 1;
            for (int i = 0; i < sectionSize; i++) {
                drawCustomTrackColors(canvas, sectionSize, i);
            }
        } else {

            //draw mProgressArr track
            mStrokePaint.setColor(mProgressTrackColor);
            mStrokePaint.setStrokeWidth(mProgressTrackSize);
            canvas.drawLine(mProgressTrack.left, mProgressTrack.top, mProgressTrack.right, mProgressTrack.bottom,
                    mStrokePaint);

            //draw BG track
            mStrokePaint.setColor(mBackgroundTrackColor);
            mStrokePaint.setStrokeWidth(mBackgroundTrackSize);
            canvas.drawLine(mBackgroundTrack.left, mBackgroundTrack.top, mBackgroundTrack.right,
                    mBackgroundTrack.bottom, mStrokePaint);
        }
        LogUtil.info(TAG, "drawTrack(Canvas canvas)  end");
    }

    private void drawCustomTrackColors(Canvas canvas, int sectionSize, int index) {
        if (mR2L) {
            mStrokePaint.setColor(mSectionTrackColorArray[sectionSize - index - 1]);
        } else {
            mStrokePaint.setColor(mSectionTrackColorArray[index]);
        }
        float thumbPosFloat = getThumbPosOnTickFloat();
        if (index < thumbPosFloat && thumbPosFloat < (index + 1)) {

            // the section track include the thumb,
            // set the ProgressTrackSize for thumb's left side track ,
            // BGTrackSize for the right's.
            float thumbCenterX = getThumbCenterX();
            mStrokePaint.setStrokeWidth(getLeftSideTrackSize());
            canvas.drawLine(mTickMarksX[index], mProgressTrack.top, thumbCenterX, mProgressTrack.bottom, mStrokePaint);
            mStrokePaint.setStrokeWidth(getRightSideTrackSize());
            canvas.drawLine(thumbCenterX, mProgressTrack.top, mTickMarksX[index + 1], mProgressTrack.bottom,
                    mStrokePaint);
        } else {
            if (index < thumbPosFloat) {
                mStrokePaint.setStrokeWidth(getLeftSideTrackSize());
            } else {
                mStrokePaint.setStrokeWidth(getRightSideTrackSize());
            }
            canvas.drawLine(mTickMarksX[index], mProgressTrack.top, mTickMarksX[index + 1], mProgressTrack.bottom,
                    mStrokePaint);
        }
    }

    private void drawTickMarks(Canvas canvas) {
        LogUtil.info(TAG, "drawTickMarks(Canvas canvas)  begin");
        if (checkValidTickMarks()) {
            return;
        }
        float thumbCenterX = getThumbCenterX();
        for (int i = 0; i < mTickMarksX.length; i++) {
            if (mTickMarksSweptHide && thumbCenterX >= mTickMarksX[i]) {
                continue;
            }
            if (mTickMarksEndsHide && (i == 0 || i == mTickMarksX.length - 1)) {
                continue;
            }
            if (i == getThumbPosOnTick() && mTicksCount > 2 && !mSeekSmoothly) {
                continue;
            }
            float thumbPosFloat = getThumbPosOnTickFloat();
            drawLeftRightTickMarksColorValues(thumbPosFloat, i);
            if (mTickMarksDrawable != null) {
                drawTickMarksPixelMapValues(canvas, thumbPosFloat, i);
                continue;
            }
            drawTickMarksTypeValues(canvas, thumbCenterX, i);
        }
        LogUtil.info(TAG, "drawTickMarks(Canvas canvas)  end");
    }

    private boolean checkValidTickMarks() {
        return  (mTicksCount == 0 || (mShowTickMarksType == TickMarkType.NONE && mTickMarksDrawable == null));
    }

    private void drawLeftRightTickMarksColorValues(float thumbPosFloat, int index) {
        if (index <= thumbPosFloat) {
            mStrokePaint.setColor(getLeftSideTickColor());
        } else {
            mStrokePaint.setColor(getRightSideTickColor());
        }
    }

    private void drawTickMarksPixelMapValues(Canvas canvas, float thumbPosFloat, int index) {
        if (mSelectTickMarksPixelMap == null || mUnselectedTickMarksPixelMap == null) {
            initTickMarksPixelMap();
        }
        if (mSelectTickMarksPixelMap == null || mUnselectedTickMarksPixelMap == null) {

            //please check your selector drawable's format and correct.
            throw new IllegalArgumentException("the format of the selector TickMarks drawable is wrong!");
        }
        if (index <= thumbPosFloat) {
            canvas.drawPixelMapHolder(new PixelMapHolder(mSelectTickMarksPixelMap), mTickMarksX[index]
                    - mUnselectedTickMarksPixelMap.getImageInfo().size.width / 2.0f, mProgressTrack.top
                    - mUnselectedTickMarksPixelMap.getImageInfo().size.height / 2.0f, mStrokePaint);
        } else {
            canvas.drawPixelMapHolder(new PixelMapHolder(mUnselectedTickMarksPixelMap), mTickMarksX[index]
                    - mUnselectedTickMarksPixelMap.getImageInfo().size.width / 2.0f, mProgressTrack.top
                    - mUnselectedTickMarksPixelMap.getImageInfo().size.height / 2.0f, mStrokePaint);
        }
    }

    private void drawTickMarksTypeValues(Canvas canvas, float thumbCenterX, int index) {
        if (mShowTickMarksType == TickMarkType.OVAL) {
            canvas.drawCircle(mTickMarksX[index], mProgressTrack.top, mTickRadius, mStrokePaint);
        } else if (mShowTickMarksType == TickMarkType.DIVIDER) {
            float dividerTickHeight;
            int rectWidth = 1;
            if (thumbCenterX >= mTickMarksX[index]) {
                dividerTickHeight = getLeftSideTrackSize();
            } else {
                dividerTickHeight = getRightSideTrackSize();
            }
            canvas.drawRect(mTickMarksX[index] - rectWidth, mProgressTrack.top - dividerTickHeight / 2.0f,
                    mTickMarksX[index] + rectWidth, mProgressTrack.top + dividerTickHeight / 2.0f,
                    mStrokePaint);
        } else if (mShowTickMarksType == TickMarkType.SQUARE) {
            canvas.drawRect(mTickMarksX[index] - mTickMarksSize / 2.0f, mProgressTrack.top
                    - mTickMarksSize / 2.0f, mTickMarksX[index] + mTickMarksSize / 2.0f,
                    mProgressTrack.top + mTickMarksSize / 2.0f, mStrokePaint);
        }
    }

    private void drawTickTexts(Canvas canvas) {
        LogUtil.info(TAG, "drawTickTexts(Canvas canvas) begin");
        if (mTickTextsArr == null) {
            return;
        }
        if (mTextPaint == null) {
            initTextPaint();
        }
        float thumbPosFloat = getThumbPosOnTickFloat();
        Color leftSideTickTextsColor = getLeftSideTickTextsColor();
        Color rightSideTickTextsColor = getRightSideTickTextsColor();
        for (int i = 0; i < mTickTextsArr.length; i++) {
            if (i == getThumbPosOnTick() && mHoveredTextColor != null) {
                mTextPaint.setColor(mHoveredTextColor);
            } else if (i < thumbPosFloat && leftSideTickTextsColor != null) {
                mTextPaint.setColor(leftSideTickTextsColor);
            } else {
                if (rightSideTickTextsColor != null) {
                    mTextPaint.setColor(rightSideTickTextsColor);
                }
            }
            int index = i;
            drawTickTextColorValues(canvas, index, i);
        }
        LogUtil.info(TAG, "drawTickTexts(Canvas canvas) end");
    }

    private void drawTickTextColorValues(Canvas canvas, int index, int loopIndex) {
        if (mR2L) {
            index = mTickTextsArr.length - 1 - loopIndex;
        }
        if (loopIndex == 0) {
            canvas.drawText(mTextPaint, mTickTextsArr[index], mTextCenterX[loopIndex] + mTickTextsWidth[index]
                    / 2.0f, mTickTextY);
        } else if (loopIndex == mTickTextsArr.length - 1) {
            canvas.drawText(mTextPaint, mTickTextsArr[index], mTextCenterX[loopIndex] - mTickTextsWidth[index]
                    / 2.0f, mTickTextY);
        } else {
            canvas.drawText(mTextPaint, mTickTextsArr[index], mTextCenterX[loopIndex], mTickTextY);
        }
    }

    private void drawThumb(Canvas canvas) {
        LogUtil.info(TAG, "drawThumb(Canvas canvas) begin");
        float thumbCenterX = getThumbCenterX();
        if (mThumbDrawable != null) {
            drawThumbDrawable(canvas, thumbCenterX);
        } else {
            if (mIsTouching) {
                mStrokePaint.setColor(mPressedThumbColor);
            } else {
                mStrokePaint.setColor(mThumbColor);
            }
            canvas.drawCircle(thumbCenterX, mProgressTrack.top, mIsTouching ? mThumbTouchRadius : mThumbRadius,
                    mStrokePaint);
        }
        LogUtil.info(TAG, "drawThumb(Canvas canvas) end");
    }

    private void drawThumbDrawable(Canvas canvas, float thumbCenterX) {
        if (mThumbPixelMap == null || mPressedThumbPixelMap == null) {
            initThumbPixelMap();
        }
        if (mThumbPixelMap == null || mPressedThumbPixelMap == null) {

            //please check your selector drawable's format and correct.
            throw new IllegalArgumentException("the format of the selector thumb drawable is wrong!");
        }
        mStrokePaint.setAlpha(TickSeekBarConstants.OPACITY_VALUE);
        if (mIsTouching) {
            canvas.drawPixelMapHolder(new PixelMapHolder(mPressedThumbPixelMap), thumbCenterX
                    - mPressedThumbPixelMap.getImageInfo().size.width / 2.0f, mProgressTrack.top
                    - mPressedThumbPixelMap.getImageInfo().size.height / 2.0f, mStrokePaint);
        } else {
            canvas.drawPixelMapHolder(new PixelMapHolder(mThumbPixelMap), thumbCenterX
                    - mThumbPixelMap.getImageInfo().size.width / 2.0f, mProgressTrack.top
                    - mThumbPixelMap.getImageInfo().size.height / 2.0f, mStrokePaint);
        }
    }

    private void drawThumbText(Canvas canvas) {
        LogUtil.info(TAG, "drawThumbText(Canvas canvas)  begin");
        if (mThumbTextShowPos == TextPosition.NONE) {
            return;
        }

        //don't show thumb text in this conflict text position
        if (mTickTextsPosition == mThumbTextShowPos) {
            return;
        }
        mTextPaint.setColor(mThumbTextColor);
        canvas.drawText(mTextPaint, getProgressString(mProgress), getThumbCenterX(), mThumbTextY);
        LogUtil.info(TAG, "drawThumbText(Canvas canvas)  end");
    }

    private float getThumbPosOnTickFloat() {
        if (mTicksCount != 0) {
            return (getThumbCenterX() - mPaddingLeft) / mSeekBlockLength;
        }
        return 0;
    }

    private float getThumbCenterX() {
        if (mR2L) {
            return mBackgroundTrack.right;
        }
        return mProgressTrack.right;
    }

    private int getThumbPosOnTick() {
        if (mTicksCount != 0) {
            return Math.round((getThumbCenterX() - mPaddingLeft) / mSeekBlockLength);
        }
        return 0;
    }

    /**
     * get the track size which on the thumb left in R2L/L2R case.
     */
    private int getLeftSideTrackSize() {
        if (mR2L) {
            return mBackgroundTrackSize;
        }
        return mProgressTrackSize;
    }

    /**
     * get the track size which on the thumb right in R2L/L2R case.
     */
    private int getRightSideTrackSize() {
        if (mR2L) {
            return mProgressTrackSize;
        }
        return mBackgroundTrackSize;
    }

    private Color getLeftSideTickTextsColor() {
        if (mR2L) {
            return mUnselectedTextsColor;
        }
        return mSelectedTextsColor;
    }

    private Color getRightSideTickTextsColor() {
        if (mR2L) {
            return mUnselectedTextsColor;
        }
        return mUnselectedTextsColor;
    }

    private Color getLeftSideTickColor() {
        if (mR2L) {
            return mUnSelectedTickMarksColor;
        }
        return mSelectedTickMarksColor;
    }

    private Color getRightSideTickColor() {
        if (mR2L) {
            return mSelectedTickMarksColor;
        }
        return mUnSelectedTickMarksColor;
    }

    /**
     * The specified scale for the progress value,
     * make sure you had chosen the float progress type
     *
     * <p>such as:
     * scale = 3; progress: 1.78627347 to 1.786
     * scale = 4; progress: 1.78627347 to 1.7863
     *
     * <p>make sure you have call the attr progress_value_float=true before, otherwise no change.
     *
     * @param scale scale for the float type progress value.
     */
    public void setDecimalScale(int scale) {
        this.mScale = scale;
    }

    /**
     * set the seek bar's thumb's color.
     *
     * @param thumbColor colorInt
     */
    public void setThumbColor(Color thumbColor) {
        this.mThumbColor = thumbColor;
        this.mPressedThumbColor = mThumbColor;
        invalidate();
    }

    /**
     * set the seek bar's thumb's Text color.
     *
     * @param thumbTextColor colorInt
     */
    public void setThumbTextColor(Color thumbTextColor) {
        this.mThumbTextColor = thumbTextColor;
    }

    private void apply(Builder builder) {
        LogUtil.info(TAG, "apply(Builder builder) begin");

        //seek bar
        this.mMax = builder.max;
        this.mMin = builder.min;
        this.mProgress = builder.progress;
        this.mIsFloatProgress = builder.progressValueFloat;
        this.mSeekSmoothly = builder.seekSmoothly;
        this.mR2L = builder.r2l;
        this.mUserSeekable = builder.userSeekable;
        this.mClearPadding = builder.clearPadding;
        this.mOnlyThumbDraggable = builder.onlyThumbDraggable;

        //track
        this.mBackgroundTrackSize = builder.trackBackgroundSize;
        this.mBackgroundTrackColor = builder.trackBackgroundColor;
        this.mProgressTrackSize = builder.trackProgressSize;
        this.mProgressTrackColor = builder.trackProgressColor;
        this.mTrackRoundedCorners = builder.trackRoundedCorners;

        //thumb
        this.mThumbSize = builder.thumbSize;
        this.mThumbDrawable = builder.thumbDrawable;
        this.mThumbTextColor = builder.thumbTextColor;
        setThumbColor(builder.thumbColor);
        initThumbColor(builder.thumbColorStateList, builder.thumbColor);
        this.mThumbTextShowPos = builder.thumbTextShow;

        //tickMarks
        this.mTicksCount = builder.tickCount;
        this.mShowTickMarksType = builder.showTickMarksType;
        this.mTickMarksSize = builder.tickMarksSize;
        this.mTickMarksDrawable = builder.tickMarksDrawable;
        this.mTickMarksEndsHide = builder.tickMarksEndsHide;
        this.mTickMarksSweptHide = builder.tickMarksSweptHide;
        setTickMarksColor(builder.tickMarksColor);
        initTickMarksColor(builder.tickMarksColorStateList, builder.tickMarksColor);

        //tickTexts
        this.mTickTextsPosition = builder.tickTextsShow;
        this.mTickTextsSize = builder.tickTextsSize;
        this.mTickTextsCustomArray = builder.tickTextsCustomArray;
        this.mTextFont = builder.tickTextFontType;
        initTickTextsColor(builder.tickTextsColorStateList, builder.tickTextsColor);
        LogUtil.info(TAG, "apply(Builder builder) end");
    }

    /**
     * get user seekable.
     *
     * @return user seekable value
     */
    public boolean isUserSeekable() {
        return mUserSeekable;
    }

    private boolean isTouchSeekBar(float mX, float mY) {
        LogUtil.info(TAG, "isTouchSeekBar(float mX, float mY) begin");
        if (mFaultTolerance == -1) {
            mFaultTolerance = SizeUtils.dp2px(mContext, TickSeekBarConstants.TOUCH_SEEK_BAR_DP_VALUE);
        }
        boolean inWidthRange = mX >= (mPaddingLeft - 2 * mFaultTolerance) && mX <= (mMeasuredWidth - mPaddingRight + 2
                * mFaultTolerance);
        boolean inHeightRange = mY >= mProgressTrack.top - mThumbTouchRadius - mFaultTolerance && mY
                <= mProgressTrack.top + mThumbTouchRadius + mFaultTolerance;
        return inWidthRange && inHeightRange;
    }

    private boolean isTouchThumb(float mX) {
        LogUtil.info(TAG, "isTouchThumb(float mX) begin");
        float rawTouchX = getTouchX();
        return rawTouchX - mThumbSize / 2f <= mX && mX <= rawTouchX + mThumbSize / 2f;
    }

    private synchronized float getTouchX() {
        refreshThumbCenterXByProgress(mProgress);
        if (mR2L) {
            return mBackgroundTrack.right;
        }
        return mProgressTrack.right;
    }

    /**
     * calculate the thumb's centerX by the changing mProgressArr.
     */
    private void refreshThumbCenterXByProgress(float progress) {
        if (mR2L) {
            mBackgroundTrack.right = mPaddingLeft + mSeekLength * (1.0f - (progress - mMin) / (mMax - mMin));
            mProgressTrack.left = mBackgroundTrack.right;
        } else {
            mProgressTrack.right = (progress - mMin) * mSeekLength / (mMax - mMin) + mPaddingLeft;
            mBackgroundTrack.left = mProgressTrack.right;
        }
    }

    private void refreshSeekBar(float xOffset) {
        refreshThumbCenterXByProgress(calculateProgress(calculateTouchX(adjustTouchX(xOffset))));
        setSeekListener(true);
        invalidate();
    }

    private float adjustTouchX(float xOffset) {
        float touchXCache;
        float totalX = getThumbCenterX() + xOffset;
        if (totalX < mPaddingLeft) {
            touchXCache = mPaddingLeft;
        } else if (totalX > mMeasuredWidth - mPaddingRight) {
            touchXCache = (float) mMeasuredWidth - mPaddingRight;
        } else {
            touchXCache = totalX;
        }
        return touchXCache;
    }

    private float calculateProgress(float touchX) {
        lastProgress = mProgress;
        mProgress = mMin + (mMax - mMin) * (touchX - mPaddingLeft) / mSeekLength;
        return mProgress;
    }

    private float calculateTouchX(float touchX) {
        float touchXTemp = touchX;

        // make sure the seek bar to seek smoothly always
        // while the tick's count is less than 3(tick's count is 1 or 2.).
        if (mTicksCount > 2 && !mSeekSmoothly) {
            int touchBlockSize = Math.round((touchX - mPaddingLeft) / mSeekBlockLength);
            touchXTemp = mSeekBlockLength * touchBlockSize + mPaddingLeft;
        }
        if (mR2L) {
            return mSeekLength - touchXTemp + 2 * mPaddingLeft;
        }
        return touchXTemp;
    }

    private void setSeekListener(boolean formUser) {
        if (mSeekChangeListener == null) {
            return;
        }
        if (progressChange()) {
            mSeekChangeListener.onSeeking(collectParams(formUser));
        }
    }

    private boolean progressChange() {
        if (mIsFloatProgress) {
            return lastProgress != mProgress;
        } else {
            return Math.round(lastProgress) != Math.round(mProgress);
        }
    }

    private SeekParams collectParams(boolean fromUser) {
        LogUtil.info(TAG, "collectParams(boolean formUser) begin");
        if (mSeekParams == null) {
            mSeekParams = new SeekParams(this);
        }
        mSeekParams.setProgress(getProgress());
        mSeekParams.setProgressFloat(getProgressFloat());
        mSeekParams.setFromUser(fromUser);

        //for discrete series seek bar
        if (mTicksCount > 2) {
            int rawThumbPos = getThumbPosOnTick();
            if (mTickTextsPosition != TextPosition.NONE && mTickTextsArr != null) {
                mSeekParams.setTickText(mTickTextsArr[rawThumbPos]);
            }
            if (mR2L) {
                mSeekParams.setThumbPosition(mTicksCount - rawThumbPos - 1);
            } else {
                mSeekParams.setThumbPosition(rawThumbPos);
            }
        }
        return mSeekParams;
    }

    /**
     * Get the seek bar's current level of progress in float type.
     *
     * @return current progress in float type.
     */
    public synchronized float getProgressFloat() {
        BigDecimal bigDecimal = BigDecimal.valueOf(mProgress);
        return bigDecimal.setScale(mScale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * Get the seek bar's current level of progress in int type.
     *
     * @return progress in int type.
     */
    public int getProgress() {
        return Math.round(mProgress);
    }

    /**
     * Set the listener to listen the seeking params changing.
     *
     * @param listener OnSeekChangeListener
     */
    public void setOnSeekChangeListener(OnSeekChangeListener listener) {
        this.mSeekChangeListener = listener;
    }

    /**
     * set the seek bar's tick's color.
     *
     * @param tickMarksColor colorInt
     */
    public void setTickMarksColor(Color tickMarksColor) {
        this.mSelectedTickMarksColor = tickMarksColor;
        this.mUnSelectedTickMarksColor = tickMarksColor;
        invalidate();
    }

    /**
     * set the color for text below/above seek bar's tickText.
     *
     * @param tickTextsColor ColorInt
     */
    public void setTickTextsColor(Color tickTextsColor) {
        mSelectedTextsColor = tickTextsColor;
        mUnselectedTextsColor = tickTextsColor;
        mHoveredTextColor = tickTextsColor;
        invalidate();
    }

    /**
     * Replace the number ticks' texts with your's by String[].
     * Usually, the text array's length your set should equals seek bar's tickMarks' count.
     *
     * @param tickTextsArr The array contains the tick text
     */
    public void customTickTexts(String[] tickTextsArr) {
        LogUtil.info(TAG, "customTickTexts( String[] tickTextsArr) begin");
        this.mTickTextsCustomArray = tickTextsArr;
        if (mTickTextsArr != null) {
            for (int i = 0; i < mTickTextsArr.length; i++) {
                String tickText;
                if (i < tickTextsArr.length) {
                    tickText = String.valueOf(tickTextsArr[i]);
                } else {
                    tickText = "";
                }
                int index = i;
                if (mR2L) {
                    index = mTicksCount - 1 - i;
                }
                mTickTextsArr[index] = tickText;
                if (mTextPaint != null && mRect != null) {
                    mTextPaint.getTextBounds(tickText);
                    mTickTextsWidth[index] = mRect.getWidth();
                }
            }
            invalidate();
        }
        LogUtil.info(TAG, "customTickTexts( String[] tickTextsArr) end");
    }

    /**
     * Collect and custom the color for each of section track.
     *
     * <p>usage :
     *
     * <p>indicatorSeekBar.customSectionTrackColor(new ColorCollector() {
     *
     * <p>public boolean collectSectionTrackColor(int[] colorIntArr) {
     * colorIntArr[0] = getResources().getColor(R.color.color_blue);
     * colorIntArr[1] = getResources().getColor(R.color.color_gray);
     * colorIntArr[2] = Color.parseColor("#FFFF00");
     * ......
     * return true; // True if apply color , otherwise no change.
     * }
     * });
     *
     * @param collector The container for section track's color
     */
    public void customSectionTrackColor(ColorCollector collector) {
        LogUtil.info(TAG, "customSectionTrackColor(ColorCollector collector) begin");
        Color[] colorArray = new Color[mTicksCount - 1 > 0 ? mTicksCount - 1 : 1];
        for (int i = 0; i < colorArray.length; i++) {

            //set the default section color
            colorArray[i] = mBackgroundTrackColor;
        }
        this.mCustomTrackSectionColorResult = collector.collectSectionTrackColor(colorArray);
        this.mSectionTrackColorArray = colorArray;
        invalidate();
        LogUtil.info(TAG, "customSectionTrackColor(ColorCollector collector) end");
    }

    /**
     * Set a new tick marks drawable.
     *
     * @param drawable the drawable for marks,selector drawable is ok.
     *                 selector format:
     */
    public void setTickMarksDrawable(Element drawable) {
        LogUtil.info(TAG, "setTickMarksDrawable(Element drawable) begin");
        this.mTickMarksDrawable = drawable;
        mTickRadius = Math.min(THUMB_MAX_WIDTH, mTickMarksSize) / 2.0f;
        mCustomDrawableMaxHeight = Math.max(mThumbTouchRadius, mTickRadius) * 2.0f;
        initTickMarksPixelMap();
        invalidate();
        LogUtil.info(TAG, "setTickMarksDrawable(Element drawable) end");
    }

    private boolean autoAdjustThumb() {
        LogUtil.info(TAG, "autoAdjustThumb() begin");
        if (mTicksCount < TickSeekBarConstants.INDEX_VALUE_THREE || !mSeekSmoothly) {
            return false;
        }
        if (!mAdjustAuto) {
            return false;
        }
        final int closestIndex = getClosestIndex();
        final float touchUpProgress = mProgress;
        AnimatorValue animator = new AnimatorValue();
        animator.setDuration(TickSeekBarConstants.ANIMATION_DURATION);
        animator.setValueUpdateListener((AnimatorValue animatorValue, float value) -> {
            lastProgress = mProgress;
            if (touchUpProgress - mProgressArr[closestIndex] > 0) {
                mProgress = touchUpProgress - (touchUpProgress - mProgressArr[closestIndex]);
            } else {
                mProgress = touchUpProgress + (mProgressArr[closestIndex] - touchUpProgress);
            }
            refreshThumbCenterXByProgress(mProgress);

            //the auto adjust was happened after user touched up, so from user is false.
            setSeekListener(false);
            invalidate();
        });
        animator.start();
        return true;
    }

    @Override
    public void onDragDown(Component component, DragInfo dragInfo) {
        LogUtil.info(TAG, "onDragDown(Component component, DragInfo dragInfo) begin");
        simulateClick();
    }

    @Override
    public void onDragStart(Component component, DragInfo dragInfo) {
        LogUtil.info(TAG, "onDragStart(Component component, DragInfo dragInfo) begin");
        float mX = dragInfo.startPoint.getPointX();
        if (isTouchSeekBar(mX, dragInfo.startPoint.getPointY())) {
            if ((mOnlyThumbDraggable && !isTouchThumb(mX))) {
                return;
            }
            if (mSeekChangeListener != null) {
                mSeekChangeListener.onStartTrackingTouch(this);
            }
            mIsTouching = true;
            refreshSeekBar(mX);
        }
        LogUtil.info(TAG, "onDragStart(Component component, DragInfo dragInfo) end");
    }

    @Override
    public void onDragUpdate(Component component, DragInfo dragInfo) {
        LogUtil.info(TAG, "onDragUpdate(Component component, DragInfo dragInfo) begin");
        refreshSeekBar((float) dragInfo.xOffset);
    }

    @Override
    public void onDragEnd(Component component, DragInfo dragInfo) {
        LogUtil.info(TAG, "onDragEnd(Component component, DragInfo dragInfo) begin");
        if (mSeekChangeListener != null) {
            mSeekChangeListener.onStopTrackingTouch(this);
        }
        mIsTouching = false;
        if (!autoAdjustThumb()) {
            invalidate();
        }
    }

    @Override
    public void onDragCancel(Component component, DragInfo dragInfo) {
        LogUtil.info(TAG, "onDragCancel(Component component, DragInfo dragInfo) begin");

    }

    @Override
    public boolean onDragPreAccept(Component component, int dragDirection) {
        LogUtil.info(TAG, "onDragPreAccept(Component component, int dragDirection) begin");
        return false;
    }

    /**
     * call this to new a builder with default params.
     *
     * @param context context environment
     * @return Builder
     */
    public static Builder with(Context context) {
        return new Builder(context);
    }

    // For Junit
    public void setDrawTrackColor(Color color) {
        mStrokePaint.setColor(color);
        drawTrackColor = color;
    }

    public Color getDrawTrackColor() {
        return drawTrackColor;
    }

    public void setDrawThumbTextColor(Color color) {
        mStrokePaint.setColor(color);
        drawThumbTextColor = color;
    }

    public Color getDrawThumbTextColor() {
        return drawThumbTextColor;
    }

    public void setDrawTickMarksColor(Color color) {
        mStrokePaint.setColor(color);
        drawTickMarksColor = color;
    }

    public Color getDrawTickMarksColor() {
        return drawTickMarksColor;
    }

    /**
     * set the seek bar's TickTexts color.
     *
     * @param color colorInt
     */
    public void setDrawTickTexts(Color color) {
        mTextPaint.setColor(color);
        drawTickTextsColor = color;
    }

    public Color getDrawTickTextsColor() {
        return drawTickTextsColor;
    }

    /**
     * set the seek bar's Thumb color.
     *
     * @param color colorInt
     */
    public void setDrawThumb(Color color) {
        mStrokePaint.setColor(color);
        drawThumbColor = color;
    }

    public Color getDrawThumbColor() {
        return drawThumbColor;
    }
}