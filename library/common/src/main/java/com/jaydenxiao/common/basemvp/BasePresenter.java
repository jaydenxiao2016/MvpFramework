package com.jaydenxiao.common.basemvp;

import android.content.Context;

import com.jaydenxiao.common.baserx.RxManager;

/**
 * des:基类presenter
 * Created by xsf
 * on 2016.07.11:55
 */
public abstract class BasePresenter<V extends BaseView> {
    public Context mContext;
    public V mView;
    public RxManager mRxManage = new RxManager();

    public void attachView(V v) {
        this.mView = v;
        if (mView != null) {
            onStart();
        }
    }
    public abstract void onStart();
    public void onDestroy() {
        mRxManage.clear();
        mView = null;
        mContext=null;
    }
}
