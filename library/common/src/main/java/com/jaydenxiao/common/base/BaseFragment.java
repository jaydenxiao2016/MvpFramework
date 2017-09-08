package com.jaydenxiao.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaydenxiao.common.basemvp.BasePresenter;
import com.jaydenxiao.common.basemvp.BaseView;
import com.jaydenxiao.common.baserx.RxManager;
import com.jaydenxiao.common.commonutils.TUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingDialog;
import com.jaydenxiao.common.commonwidget.pagermanage.PageManager;

import butterknife.ButterKnife;

/**
 * des:基类fragment
 * Created by xsf
 * on 2016.07.12:38
 */

/***************
 * 使用例子
 *********************/
//1.mvp模式
//public class SampleFragment extends BaseFragment<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    public void initView() {
//    }
//}
//2.普通模式
//public class SampleFragment extends BaseFragment {
//    @Override
//    public int getLayoutResource() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment {
    protected View rootView;
    public P mPresenter;
    protected V mView;
    private volatile RxManager mRxManager;
    /**
     * 状态页面管理
     */
    protected PageManager pageManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutId(), container, false);
        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mView=this.attachPresenterView();
        if (mPresenter != null) {
            mPresenter.mContext = this.getContext();
            if(mView!=null){
                mPresenter.attachView(mView);
            }else{
                throw new NullPointerException("presenter不为空时，view不能为空");
            }
        }
        initView(savedInstanceState);
        return rootView;
    }

    /**********************
     * 子类实现 begin
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract V attachPresenterView();

    //初始化view
    public abstract void initView(Bundle savedInstanceState);

    /********************** 子类实现 end*****************************/

    /**
     * 获取rxmanager
     * 管理单个act rxjava生命周期
     *
     * @return
     */
    public RxManager getRxManager() {
        if (mRxManager == null) {
            synchronized (RxManager.class) {
                if (mRxManager == null) {
                    mRxManager = new RxManager();
                }
            }

        }
        return mRxManager;
    }

    /******************************状态页面公开调用方法begin*********************************/
    /**
     * 初始化页面状态切换
     *
     * @param object                 activity或者fragment或者View
     * @param isShowLoadingOrContent
     */
    public void initPagerManager(Object object, boolean isShowLoadingOrContent, final PageManager.ReloadListener reloadListener) {
        pageManager = PageManager.init(object, isShowLoadingOrContent, reloadListener);
    }

    /**
     * 设置状态页面为loading
     *
     * @param emptyMsg
     */
    public void showPagerManagerLoading(CharSequence emptyMsg) {
        if (pageManager != null)
            pageManager.showLoading(emptyMsg);
    }

    public void showPagerManagerLoading() {
        if (pageManager != null)
            pageManager.showLoading();
    }
    /**
     * 修改进度条msg
     *
     * @param msg
     */
    public void udapteProgressMsg(String msg) {
        LoadingDialog.setText(msg);
    }
    /**
     * 设置状态页面为error
     *
     * @param errorMsg
     */
    public void showPagerManagerError(CharSequence errorMsg) {
        if (pageManager != null)
            pageManager.showError(errorMsg);
    }

    public void showPagerManagerError() {
        if (pageManager != null)
            pageManager.showError();
    }

    /**
     * 设置状态页面为显示内容
     */
    public void showPagerManagerContent() {
        if (pageManager != null)
            pageManager.showContent();
    }
    /**
     * 设置状态页面为空
     */
    public void showPagerManagerEmpty() {
        if (pageManager != null)
            pageManager.showEmpty();
    }
    /**
     * 设置状态页面为空
     */
    public void showPagerManagerEmpty(String str) {
        if (pageManager != null)
            pageManager.showEmpty(str);
    }
    /******************************状态页面公开调用方法end*********************************/


    /******************************浮动加载进度条公开调用方法begin*********************************/
    /**
     * 开启浮动加载进度条
     */
    public void showProgressDialog() {
        LoadingDialog.showLoadingDialog(getActivity());
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        LoadingDialog.showLoadingDialog(getActivity(), msg);
    }

    /**
     * 停止浮动加载进度条
     */
    public void cancelLoadingDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /******************************浮动加载进度条公开调用方法end*********************************/

    /**
     * 显示自定义带图片的toast
     *
     * @param text
     */
    public void showCustomToast(String text, int resId) {
        ToastUitl.showCustomToast(text, resId);
    }
    /**
     * 显示短toast
     *
     * @param text
     */
    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }
    /**
     * 显示长toast
     *
     * @param text
     */
    public void showLongToast(String text) {
        ToastUitl.showLong(text);
    }
    /**
     * 自定义时长toast
     *
     * @param text
     */
    public void showLongToast(String text,int duration) {
        ToastUitl.show(text,duration);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mPresenter != null)
            mPresenter.onDestroy();
        if (mRxManager != null)
            mRxManager.clear();
        if (pageManager != null) {
            pageManager = null;
        }
        LoadingDialog.cancelDialogForLoading();
    }


}
