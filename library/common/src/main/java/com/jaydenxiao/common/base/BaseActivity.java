package com.jaydenxiao.common.base;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.jaydenxiao.common.R;
import com.jaydenxiao.common.baseapp.AppManager;
import com.jaydenxiao.common.basemvp.BasePresenter;
import com.jaydenxiao.common.baserx.RxManager;
import com.jaydenxiao.common.commonutils.TUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingDialog;
import com.jaydenxiao.common.commonwidget.StatusBarCompat;
import com.jaydenxiao.common.commonwidget.pagermanage.PageManager;

import butterknife.ButterKnife;

/**
 * 基类
 */

/***************
 * 使用例子
 *********************/
//1.mvp模式
//public class SampleActivity extends BaseActivity<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
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
//public class SampleActivity extends BaseActivity {
//    @Override
//    public int getLayoutId() {
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
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    public T mPresenter;
    public Context mContext;
    private volatile RxManager mRxManager;
    /**
     * 状态页面管理
     */
    protected PageManager pageManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        this.attachPresenterView();
        this.initView(savedInstanceState);
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        SetStatusBarColor();
    }


    /*********************** 子类实现 begin *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void attachPresenterView();

    //初始化view
    public abstract void initView(Bundle savedInstanceState);

    /********************** 子类实现 end*****************************/


    /******************************状态栏着色公开调用方法begin*********************************/
    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.main_color));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }

    /******************************状态页面公开调用方法end*********************************/


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
        LoadingDialog.showLoadingDialog(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        LoadingDialog.showLoadingDialog(this, msg);
    }

    /**
     * 停止浮动加载进度条
     */
    public void cancelLoadingDialog() {
        LoadingDialog.cancelDialogForLoading();
    }
    /**
     * 修改进度条msg
     *
     * @param msg
     */
    public void udapteProgressMsg(String msg) {
        LoadingDialog.setText(msg);
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
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        if (mRxManager != null)
            mRxManager.clear();
        if (pageManager != null) {
            pageManager = null;
        }
        LoadingDialog.cancelDialogForLoading();
        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
