package com.jaydenxiao.common.basemvp;

import android.content.Context;

import com.jaydenxiao.common.baserx.RxManager;

/**
 * des:基类presenter
 * Created by xsf
 * on 2016.07.11:55
 */
public abstract class BasePresenter<T> {
    public Context mContext;
    public T mView;
    public RxManager mRxManage = new RxManager();

    public void setV(T v) {
        this.mView = v;
        if (mView != null) {
            onStart();
        }
    }
    public abstract void onStart();
    public void onDestroy() {
        mRxManage.clear();
        mView = null;
    }
}
