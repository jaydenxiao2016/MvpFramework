package com.jaydenxiao.common.baserx;

import android.app.Activity;
import android.content.Context;

import com.jaydenxiao.common.R;
import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.commonutils.NetWorkUtils;
import com.jaydenxiao.common.commonwidget.LoadingDialog;

import java.lang.ref.SoftReference;

import rx.Subscriber;

/**
 * des:订阅封装
 * Created by xsf
 * on 2016.09.10:16
 */
/********************使用例子********************/
/*_apiService.login(mobile, verifyCode)
        .//省略
        .subscribe(new RxSubscriber<User user>(mContext,false) {
@Override
public void _onNext(User user) {
        // 处理user
        }

@Override
public void _onError(String msg) {
        ToastUtil.showShort(mActivity, msg);
        });*/
public abstract class RxSubscriber<T> extends Subscriber<T> {
    //弱引用防止内存泄露
    private SoftReference<Context> mActivity;
    private String msg;
    private boolean showDialog=true;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog= true;
    }
    public void hideDialog() {
        this.showDialog= true;
    }

    public RxSubscriber(Context context, boolean showDialog,String msg) {
        this.mActivity=new SoftReference<Context>(context);
        this.msg = msg;
        this.showDialog=showDialog;
    }
    public RxSubscriber(Context context) {
        this(context, true,BaseApplication.getAppContext().getString(R.string.loading));
    }
    public RxSubscriber(Context context,boolean showDialog) {
        this(context, showDialog,BaseApplication.getAppContext().getString(R.string.loading));
    }

    @Override
    public void onCompleted() {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                LoadingDialog.showLoadingDialog((Activity) mActivity.get(),msg);
            } catch (Exception e) {
            }
        }
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    @Override
    public void onError(Throwable e) {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading();
        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }
        //其它
        else {
            //LogUtils.loge("error from api:",e.getMessage());
            _onError(e!=null?e.getMessage().toString():BaseApplication.getAppContext().getString(R.string.no_net));
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
