package com.jaydenxiao.common.commonwidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaydenxiao.common.R;


public class NormalTitleBar extends RelativeLayout {

    private ImageView ivRight;
    public TextView ivBack,tvTitle, tvRight;
    private RelativeLayout rlCommonTitle;
    private Context context;
    private View viewGad;

    public NormalTitleBar(Context context) {
        super(context, null);
        this.context = context;
    }

    public NormalTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.bar_normal, this);
        ivBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.image_right);
        rlCommonTitle = (RelativeLayout) findViewById(R.id.common_title);
        viewGad=findViewById(R.id.view_gad);
    }

    /**
     * 管理返回按钮
     */
    public void setBackVisibility(boolean visible) {
        if (visible) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧字符串
     * @param visiable
     */
    public void setTvLeftVisiable(boolean visiable){
        if (visiable){
            ivBack.setVisibility(View.VISIBLE);
        }else{
            ivBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧字符串
     * @param tvLeftText
     */
    public void setTvLeft(String tvLeftText){
        ivBack.setText(tvLeftText);
    }


    /**
     * 管理标题
     */
    public void setTitleVisibility(boolean visible) {
        if (visible) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    public void setTitleText(String string) {
        tvTitle.setText(string);
    }

    public void setTitleText(int string) {
        tvTitle.setText(string);
    }

    /**
     * 设置标题的文字位置
     * @param gravity
     */
    public void setTitleTextGravity(int gravity) {
        tvTitle.setGravity(gravity);
    }

    public void setTitleDrawableLeft(int id) {
        Drawable drawable= ResourcesCompat.getDrawable(getResources(),id, null);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(drawable,null,null,null);
    }
    public void setTitleDrawableRight(int id) {
        Drawable drawable= ResourcesCompat.getDrawable(getResources(),id, null);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(null,null,drawable,null);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(ContextCompat.getColor(context,color));
    }
    /*
    * 点击事件
    */
    public void setOnTitleListener(OnClickListener listener) {
        tvTitle.setOnClickListener(listener);
    }

    /**
     * 右图标
     */
    public void setRightImagVisibility(boolean visible) {
        ivRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightImagSrc(int id) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(id);
    }

    /**
     * 获取右按钮
     * @return
     */
    public View getRightImage() {
        return ivRight;
    }

    /**
     * 左图标
     *
     * @param id
     */
    public void setLeftImagSrc(int id) {
        Drawable drawable= ResourcesCompat.getDrawable(getResources(),id, null);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ivBack.setCompoundDrawables(drawable,null,null,null);
    }
    /**
     * 左文字
     *
     * @param str
     */

    public void setLeftTitle(String str) {
        ivBack.setText(str);
    }
    public void setLeftTitleColor(int color) {
        ivBack.setTextColor(ContextCompat.getColor(context,color));
    }
    /**
     * 右标题
     */
    public void setRightTitleVisibility(boolean visible) {
        tvRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
    public void setRightTitleColor(int color) {
        tvRight.setTextColor(ContextCompat.getColor(context,color));
    }
    public void setRightTitle(String text) {
        tvRight.setText(text);
    }

    /*
     * 点击事件
     */
    public void setOnBackListener(OnClickListener listener) {
        ivBack.setOnClickListener(listener);
    }

    public void setOnRightImagListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setOnRightTextListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    /**
     * 标题背景颜色
     *
     * @param color
     */
    public void setBackGroundColor(int color) {
        rlCommonTitle.setBackgroundColor(ContextCompat.getColor(context,color));
    }
    public Drawable getBackGroundDrawable() {
        return rlCommonTitle.getBackground();
    }

    /**
     * 底部白线是否显示
     * @param visible
     */
    public void setViewGadVisible(boolean visible){
        viewGad.setVisibility(visible?View.VISIBLE:View.GONE);
    }


}
