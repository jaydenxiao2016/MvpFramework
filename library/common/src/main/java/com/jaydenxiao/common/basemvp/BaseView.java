package com.jaydenxiao.common.basemvp;

/**
 * des:baseview
 * Created by xsf
 * on 2016.07.11:53
 */
public interface BaseView {
    /*******
     * 内嵌加载
     *******/
    void showLoading(String title);

    void showContent();

    void showErrorTip(String msg);

    void showEmptyTip(String msg);
}
