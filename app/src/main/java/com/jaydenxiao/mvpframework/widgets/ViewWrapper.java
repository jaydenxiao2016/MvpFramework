package com.jaydenxiao.mvpframework.widgets;

import android.view.View;

/**
 * 类名：ViewWrapper.java
 * 描述：
 * 作者：xsf
 * 创建时间：2017/5/19
 * 最后修改时间：2017/5/19
 */

public class ViewWrapper {
    private View target;

    public ViewWrapper(View target) {
        this.target = target;
    }

    public int getWidth() {
        return target.getLayoutParams().width;
    }

    public void setWidth(int width) {
        target.getLayoutParams().width = width;
        target.requestLayout();
    }
    public int getHeight(){
        return target.getLayoutParams().height;
    }
    public void setHeight(int height){
        target.getLayoutParams().height=height;
        target.requestLayout();
    }
}
