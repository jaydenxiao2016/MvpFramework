package com.jaydenxiao.common.commonwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import com.jaydenxiao.common.R;


/**
 * 小红点view(有显示数字、显示红点（不带数字）、显示文字三种模式)
 */
public class BadgeView extends TextView {

    /**
     * 小红点在target view 的位置
     */
    public static final int POSITION_TOP_LEFT = 1;
    public static final int POSITION_TOP_RIGHT = 2;
    public static final int POSITION_BOTTOM_LEFT = 3;
    public static final int POSITION_BOTTOM_RIGHT = 4;
    public static final int POSITION_CENTER = 5;

    /**
     * 默认小红点的间距
     */
    private static final int DEFAULT_MARGIN_DIP = 5;
    private static final int DEFAULT_LR_PADDING_DIP = 5;
    /**
     * 默认target view 和小红点的间距
     */
    private static final int DEFAULT_BADGE_MARGIN_REDPOINT = 5;
    /**
     * 默认小红点在target view 的顶部右边
     */
    private static final int DEFAULT_POSITION = POSITION_TOP_RIGHT;
    /**
     * 默认小红点不显示数字时圆点大小
     */
    private static final int DEFAULT_RED_POINT = 15;
    /**
     * 默认小红点的字体为白色
     */
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private static Animation fadeIn;
    private static Animation fadeOut;

    private Context context;
    private View target;

    private int badgePosition;
    private int badgeMarginH;
    private int badgeMarginV;
    private int badgeMarginRedPoint;
    private int redPointDip;

    private boolean isShown;
    private int targetTabIndex;
    /**
     * 是否显示圆点,不显示数字
     */
    private boolean isPoint = false;
    /**
     * 当前显示数字
     */
    private int num = 0;
    /**
     * 当前显示文字
     */
    private String text = "";

    public BadgeView(Context context) {
        this(context, (AttributeSet) null, android.R.attr.textViewStyle);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    /**
     * Constructor -
     * <p>
     * create a new BadgeView instance attached to a target {@link View}.
     *
     * @param context context for this view.
     * @param target  the View to attach the badge to.
     */
    public BadgeView(Context context, View target) {
        this(context, null, android.R.attr.textViewStyle, target, 0);
    }

    /**
     * Constructor -
     * <p>
     * create a new BadgeView instance attached to a target {@link TabWidget}
     * tab at a given index.
     *
     * @param context context for this view.
     * @param target  the TabWidget to attach the badge to.
     * @param index   the position of the tab within the target.
     */
    public BadgeView(Context context, TabWidget target, int index) {
        this(context, null, android.R.attr.textViewStyle, target, index);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null, 0);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyle, View target, int tabIndex) {
        super(context, attrs, defStyle);
        init(context, target, tabIndex);
    }


    private void init(Context context, View target, int tabIndex) {

        this.context = context;
        this.target = target;
        this.targetTabIndex = tabIndex;

        // apply defaults
        badgePosition = DEFAULT_POSITION;
        badgeMarginH = dipToPixels(DEFAULT_MARGIN_DIP);
        badgeMarginV = badgeMarginH;
        badgeMarginRedPoint = dipToPixels(DEFAULT_BADGE_MARGIN_REDPOINT);
        redPointDip = dipToPixels(DEFAULT_RED_POINT);

        setTypeface(Typeface.DEFAULT_BOLD);
        int paddingPixels = dipToPixels(DEFAULT_LR_PADDING_DIP);
        setPadding(paddingPixels, 0, paddingPixels, 0);
        setTextColor(DEFAULT_TEXT_COLOR);

        setSpecialAttr();

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(200);

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(200);
        isShown = false;
        if (this.target != null) {
            applyTo(this.target);
        }
    }

    /**
     * 如果编译版本大于5.0要给小红点控件加上elevation和translationZ属性，否则如果target view是button的话，小红点会被挡住，5.0
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setSpecialAttr() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(dipToPixels(1));
            setTranslationZ(dipToPixels(1));
            if (target != null && target instanceof Button) {
                target.setStateListAnimator(null);
            }
        }
    }

    /**
     * 在target view头上加上小红点
     *
     * @param target
     */
    private void applyTo(View target) {
        LayoutParams lp = target.getLayoutParams();
        ViewParent parent = target.getParent();
        FrameLayout container = new FrameLayout(context);
        if (target instanceof TabWidget) {
            // set target to the relevant tab child container
            target = ((TabWidget) target).getChildTabViewAt(targetTabIndex);
            this.target = target;
            ((ViewGroup) target).addView(container,
                    new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            this.setVisibility(View.GONE);
            container.addView(this);
        } else {
            // TODO verify that parent is indeed a ViewGroup
            ViewGroup group = (ViewGroup) parent;
            int index = group.indexOfChild(target);
            group.removeView(target);
            group.addView(container, index, lp);
            container.addView(target);

            this.setVisibility(View.GONE);
            container.addView(this);
            group.invalidate();

        }

    }

    /**
     * 显示红点（不带数字）
     */
    public void showRedPoint(int num) {
        this.num = num;
        this.isPoint = true;
        setPoint();
        if (getBackground() == null) {
            setBackgroundResource(R.drawable.badge_rectangle_bg);
        }
        applyLayoutParams();
        this.setVisibility(View.VISIBLE);
        isShown = true;
    }

    /**
     * 显示带数字红点
     *
     * @param num
     */
    public void showNum(int num) {
        this.isPoint = false;
        this.num = num;
        if (num < 1) {//空内容
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            if (isPoint) {
                setPoint();
            } else {
                setText(num > 99 ? "99+" : String.valueOf(num));
            }
        }
        if (getBackground() == null) {
            setBackgroundResource(R.drawable.badge_rectangle_bg);
        }
        applyLayoutParams();
        this.setVisibility(View.VISIBLE);
        isShown = true;
    }

    /**
     * 显示文字
     *
     * @param text
     */
    public void showString(String text) {
        this.isPoint = false;
        this.num = 1;
        this.text = text;
        if (TextUtils.isEmpty(text)) {//空内容
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            setText(text);
        }
        if (getBackground() == null) {
            setBackgroundResource(R.drawable.badge_rectangle_bg);
        }
        applyLayoutParams();
        this.setVisibility(View.VISIBLE);
        isShown = true;
    }

    /**
     * 隐藏
     */
    public void hide() {
        this.setVisibility(View.GONE);
        isShown = false;
    }

    /**
     * 不改变数字切换隐藏/显示
     */
    public void toggle() {
        if (isShown) {
            hide();
        } else {
            if (!TextUtils.isEmpty(text)) {
                showString(text);
            } else {
                if (isPoint) {
                    showRedPoint(num);
                } else {
                    showNum(num);
                }
            }
        }
    }

    /**
     * 设置小红点背景为圆点
     */
    private BadgeView setPoint() {
        setBackgroundResource(R.drawable.badge_point_by);
        setWidth(redPointDip);
        setHeight(redPointDip);
        badgeMarginRedPoint = redPointDip / 5;
        badgeMarginH = 0;
        badgeMarginV = 0;
        return this;
    }


    /**
     * 设置LayoutParams属性
     */
    private void applyLayoutParams() {

        //设置小红点自身的间距
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        switch (badgePosition) {
            case POSITION_TOP_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.TOP;
                lp.setMargins(badgeMarginH, badgeMarginV, 0, 0);
                break;
            case POSITION_TOP_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.TOP;
                lp.setMargins(0, badgeMarginV, badgeMarginH, 0);
                break;
            case POSITION_BOTTOM_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                lp.setMargins(badgeMarginH, 0, 0, badgeMarginV);
                break;
            case POSITION_BOTTOM_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                lp.setMargins(0, 0, badgeMarginH, badgeMarginV);
                break;
            case POSITION_CENTER:
                lp.gravity = Gravity.CENTER;
                lp.setMargins(0, 0, 0, 0);
                break;
            default:
                break;
        }
        setLayoutParams(lp);

        //设置target和小红点间的间距
        if (target != null) {
            FrameLayout.LayoutParams lpTarget = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lpTarget.setMargins(badgeMarginRedPoint, badgeMarginRedPoint, badgeMarginRedPoint, badgeMarginRedPoint);
            target.setLayoutParams(lpTarget);
        }
    }

    /**
     * Returns the target View this badge has been attached to.
     */
    public View getTarget() {
        return target;
    }

    /**
     * Is this badge currently visible in the UI?
     */
    @Override
    public boolean isShown() {
        return isShown;
    }

    public BadgeView setBadgePosition(int badgePosition) {
        this.badgePosition = badgePosition;
        return this;
    }

    /**
     * 设置小红点的间距
     *
     * @param badgeMargin
     */
    public BadgeView setBadgeMargin(int badgeMargin) {
        this.badgeMarginH = badgeMargin;
        this.badgeMarginV = badgeMargin;
        return this;
    }

    /**
     * 设置小红点的间距
     *
     * @param horizontal
     * @param vertical
     */
    public BadgeView setBadgeMargin(int horizontal, int vertical) {
        this.badgeMarginH = horizontal;
        this.badgeMarginV = vertical;
        return this;
    }

    /**
     * 设置target view和小红点间的间距
     *
     * @param badgeMarginRedPoint
     */
    public BadgeView setBadgeMarginRedPoint(int badgeMarginRedPoint) {
        this.badgeMarginRedPoint = badgeMarginRedPoint;
        return this;
    }

    /**
     * 设置小红点如果不显示数字时的圆点大小
     *
     * @param redPointDip
     */
    public BadgeView setRedPointCircleDP(int redPointDip) {
        this.redPointDip = redPointDip;
        return this;
    }

    /**
     * 设置小红点背景
     *
     * @param badgeRes the badge background color.
     */
    public BadgeView setBadgeBackgroundRes(@DrawableRes int badgeRes) {
        setBackgroundResource(badgeRes);
        return this;
    }

    /**
     * dp转px
     *
     * @param dip
     * @return
     */
    private int dipToPixels(int dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 获取未读条数
     * 默认显示红点至少有一条未读消息
     *
     * @return
     */
    public int getNotReadCount() {
        return num == 0 && getVisibility() == View.VISIBLE ? 1 : num;
    }

}