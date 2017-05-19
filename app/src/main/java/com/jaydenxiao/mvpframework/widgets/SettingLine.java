package com.jaydenxiao.mvpframework.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaydenxiao.mvpframework.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名：SettingLine.java
 * 描述：设置的行布局
 * 作者：xsf
 * 创建时间：2017/5/19
 * 最后修改时间：2017/5/19
 */

public class SettingLine extends FrameLayout {
    @Bind(R.id.img_left)
    ImageView imgLeft;
    @Bind(R.id.tv_left_text)
    TextView tvLeftText;
    @Bind(R.id.img_arrow)
    ImageView imgArrow;
    @Bind(R.id.tv_right_text)
    TextView tvRightText;
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    //左边
    private int drawableLeft;
    private boolean drawableLeftVisible;
    private String leftText;
    private int leftTextColor;
    private float leftTextSize;
    private boolean leftTextVisible;
    //右边
    private String rightText;
    private int rightTextColor;
    private float rightTextSize;
    private boolean rightTextVisible;

    public SettingLine(Context context) {
        super(context);
        init(null);
    }

    public SettingLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SettingLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SettingLine(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.item_setting, this);
        ButterKnife.bind(this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.settingLine);
        //左边
        drawableLeft = typedArray.getResourceId(R.styleable.settingLine_drawableLeft, R.drawable.icon_personal_setting);
        drawableLeftVisible = typedArray.getBoolean(R.styleable.settingLine_drawableLeftVisible, false);
        leftText = typedArray.getString(R.styleable.settingLine_leftText);
        leftTextColor = typedArray.getColor(R.styleable.settingLine_leftTextColor, ContextCompat.getColor(getContext(), R.color.light_gray));
        leftTextSize = typedArray.getDimension(R.styleable.settingLine_leftTextSize, 14);
        leftTextVisible = typedArray.getBoolean(R.styleable.settingLine_leftTextVisible, true);
        //右边
        rightText = typedArray.getString(R.styleable.settingLine_rightText);
        rightTextColor = typedArray.getColor(R.styleable.settingLine_rightTextColor, ContextCompat.getColor(getContext(), R.color.light_gray));
        rightTextSize = typedArray.getDimension(R.styleable.settingLine_rightTextSize, 14);
        rightTextVisible = typedArray.getBoolean(R.styleable.settingLine_rightTextVisible, false);

        setValue();

        typedArray.recycle();
    }

    private void setValue() {
        imgLeft.setImageResource(drawableLeft);
        imgLeft.setVisibility(drawableLeftVisible ? View.VISIBLE : View.GONE);
        tvLeftText.setText(leftText);
        tvLeftText.setTextColor(leftTextColor);
        tvLeftText.setTextSize(leftTextSize);
        tvLeftText.setVisibility(leftTextVisible ? View.VISIBLE : View.GONE);

        tvRightText.setText(rightText);
        tvRightText.setTextColor(rightTextColor);
        tvRightText.setTextSize(rightTextSize);
        tvRightText.setVisibility(rightTextVisible ? View.VISIBLE : View.GONE);
    }
    public void setDrawableLeftVisible(boolean drawableLeftVisible){
        imgLeft.setVisibility(drawableLeftVisible ? View.VISIBLE : View.GONE);
    }
    public void setDrawableLeft(int drawableLeft){
        imgLeft.setImageResource(drawableLeft);
    }

    public void setLeftText(String leftText){
        tvLeftText.setText(leftText);
    }
    public void setLeftTextColor(int leftTextColor){
        tvLeftText.setTextColor(leftTextColor);
    }
    public void setLeftTextSize(int leftTextSize){
        tvLeftText.setTextSize(leftTextSize);
    }
    public void setLeftTextVisible(boolean leftTextVisible){
        tvLeftText.setVisibility(leftTextVisible ? View.VISIBLE : View.GONE);
    }

    public void setRightText(String rightText){
        tvRightText.setText(rightText);
    }
    public void setRightTextColor(int leftTextColor){
        tvRightText.setTextColor(leftTextColor);
    }
    public void setRightTextSize(int rightTextSize){
        tvRightText.setTextSize(rightTextSize);
    }
    public void setRightTextVisible(boolean rightTextVisible){
        tvRightText.setVisibility(rightTextVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
    }
}
