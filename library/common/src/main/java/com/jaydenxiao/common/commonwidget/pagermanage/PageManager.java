package com.jaydenxiao.common.commonwidget.pagermanage;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaydenxiao.common.R;

/**
 * 类名：LoadingDialog.java
 * 描述：内嵌的加载几种状态页面管理
 * 作者：xsf
 * 创建时间：2016/12/1
 * 最后修改时间：2016/12/1
 */
public class PageManager {
    public static final int NO_LAYOUT_ID = 0;
    public static int BASE_LOADING_LAYOUT_ID = R.layout.pager_loading;
    public static int BASE_RETRY_LAYOUT_ID = R.layout.pager_error;
    public static int BASE_EMPTY_LAYOUT_ID = R.layout.pager_empty;


    public PageLayout mLoadingAndRetryLayout;
    private static Context appContext;
    private TextView tvError,tvLoading, tvEmpty;

    private PageManager() {
    }
    public static void initInApp(Context appContext) {
        initInApp(appContext, 0, 0, 0);
    }
    /**
     * 如果需要后续调用自定义空白msg,错误msg字符串的api,则页面中显示该字符串的textview的id必须为tv_msg_empty,tv_msg_error
     *
     * @param appContext
     * @param layoutIdOfEmpty
     * @param layoutIdOfLoading
     * @param layoutIdOfError
     */
    public static void initInApp(Context appContext, int layoutIdOfEmpty, int layoutIdOfLoading, int layoutIdOfError) {
        PageManager.appContext = appContext;
        if (layoutIdOfEmpty > 0) {
            BASE_EMPTY_LAYOUT_ID = layoutIdOfEmpty;
        }

        if (layoutIdOfLoading > 0) {
            BASE_LOADING_LAYOUT_ID = layoutIdOfLoading;
        }

        if (layoutIdOfError > 0) {
            BASE_RETRY_LAYOUT_ID = layoutIdOfError;
        }
    }

    /**
     * @param container              必须为activity,fragment或者view.如果是view,则该view对象必须有parent
     * @param reloadListener         点击重试的动作
     * @param isShowLoadingOrContent 第一次是显示loading(true)还是content(false)
     * @return
     */
    public static PageManager init(final Object container, boolean isShowLoadingOrContent, final ReloadListener reloadListener) {
        PageManager manager = new PageManager(container, new PageListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reloadListener.reload();
                    }
                });
            }
        });
        if (isShowLoadingOrContent) {
            manager.showLoading();
        } else {
            manager.showContent();
        }
        return manager;
    }
    /**
     * @param container              必须为activity,fragment或者view.如果是view,则该view对象必须有parent
     * @param reloadListener         点击重试的动作
     * @param loadingMsg             加载中状态的文字提示
     * @param isShowLoadingOrContent 第一次是显示loading(true)还是content(false)
     * @return
     */
    public static PageManager init(final Object container, boolean isShowLoadingOrContent, final CharSequence loadingMsg, final ReloadListener reloadListener) {
        PageManager manager = new PageManager(container, new PageListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reloadListener.reload();
                    }
                });
            }
        });
        if (isShowLoadingOrContent) {
            manager.showLoading(loadingMsg);
        } else {
            manager.showContent();
        }
        return manager;
    }

    /**
     * 显示内容页面
     */
    public void showContent() {
        mLoadingAndRetryLayout.showContent();
    }


    /**
     * 显示loading页面
     */
    public void showLoading() {
        mLoadingAndRetryLayout.showLoading();
    }
    //显示自定义文字的loading信息
    public void showLoading(CharSequence emptyMsg) {
        if (tvLoading != null) {
            if(!TextUtils.isEmpty(emptyMsg)) {
                tvLoading.setText(emptyMsg);
            }
            mLoadingAndRetryLayout.showLoading();
            return;
        }
        ViewGroup view = (ViewGroup) mLoadingAndRetryLayout.getLoadingView();
        tvLoading = (TextView) view.findViewById(R.id.tv_loading);
        if(!TextUtils.isEmpty(emptyMsg)) {
            tvLoading.setText(emptyMsg);
        }
        mLoadingAndRetryLayout.showLoading();
    }


    /**
     * 显示数据为空界面
     */
    public void showEmpty() {
        mLoadingAndRetryLayout.showEmpty();
    }
    /**
     * 显示数据为空自定义文字界面
     */
    public void showEmpty(CharSequence emptyMsg) {
        if (tvEmpty != null) {
            tvEmpty.setText(emptyMsg);
            mLoadingAndRetryLayout.showEmpty();
            return;
        }
        ViewGroup view = (ViewGroup) mLoadingAndRetryLayout.getEmptyView();
        tvEmpty = (TextView) view.findViewById(R.id.tv_msg_empty);
        tvEmpty.setText(emptyMsg);
        mLoadingAndRetryLayout.showEmpty();
    }

    /**
     * 显示网络错误界面
     */
    public void showError() {
        mLoadingAndRetryLayout.showRetry();
    }

    /**
     * 显示网络错误自定义文字界面
     */
    public void showError(CharSequence errorMsg) {

        if (tvError != null) {
            tvError.setText(errorMsg);
            mLoadingAndRetryLayout.showRetry();
            return;
        }
        ViewGroup view = (ViewGroup) mLoadingAndRetryLayout.getRetryView();
        tvError = (TextView) view.findViewById(R.id.tv_msg_error);
        tvError.setText(errorMsg);
        mLoadingAndRetryLayout.showRetry();
    }

    /**
     * 默认回调监听
     */
    public PageListener DEFAULT_LISTENER = new PageListener() {
        @Override
        public void setRetryEvent(View retryView) {

        }
    };

    /**
     * 初始化实例
     * @param activityOrFragmentOrView
     * @param listener
     */
    private PageManager(Object activityOrFragmentOrView, PageListener listener) {
        if (listener == null) listener = DEFAULT_LISTENER;

        ViewGroup contentParent = null;
        Context context;
        if (activityOrFragmentOrView instanceof Activity) {
            Activity activity = (Activity) activityOrFragmentOrView;
            context = activity;
            contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        } else if (activityOrFragmentOrView instanceof Fragment) {
            Fragment fragment = (Fragment) activityOrFragmentOrView;
            context = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());
        } else if (activityOrFragmentOrView instanceof View) {
            View view = (View) activityOrFragmentOrView;
            contentParent = (ViewGroup) (view.getParent());
            context = view.getContext();
        } else {
            throw new IllegalArgumentException("the container's type must be Fragment or Activity or a view which already has a parent ");
        }
        int childCount = contentParent.getChildCount();
        //get contentParent
        int index = 0;
        View oldContent;
        if (activityOrFragmentOrView instanceof View) {
            oldContent = (View) activityOrFragmentOrView;
            for (int i = 0; i < childCount; i++) {
                if (contentParent.getChildAt(i) == oldContent) {
                    index = i;
                    break;
                }
            }
        } else {
            oldContent = contentParent.getChildAt(0);
        }
        contentParent.removeView(oldContent);
        //setup content layout
        PageLayout loadingAndRetryLayout = new PageLayout(context);

        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        contentParent.addView(loadingAndRetryLayout, index, lp);
        loadingAndRetryLayout.setContentView(oldContent);
        // 设置加载中、错误重新尝试、数据为空界面
        setupLoadingLayout(listener, loadingAndRetryLayout);
        setupRetryLayout(listener, loadingAndRetryLayout);
        setupEmptyLayout(listener, loadingAndRetryLayout);
        //回调
        listener.setRetryEvent(loadingAndRetryLayout.getRetryView());
        listener.setLoadingEvent(loadingAndRetryLayout.getLoadingView());
        listener.setEmptyEvent(loadingAndRetryLayout.getEmptyView());
        mLoadingAndRetryLayout = loadingAndRetryLayout;
    }

    /**
     * 设置空界面view
     *
     * @param listener
     * @param loadingAndRetryLayout
     */
    private void setupEmptyLayout(PageListener listener, PageLayout loadingAndRetryLayout) {
        if (listener.isSetEmptyLayout()) {
            int layoutId = listener.generateEmptyLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setEmptyView(layoutId);
            } else {
                loadingAndRetryLayout.setEmptyView(listener.generateEmptyLayout());
            }
        } else {
            if (BASE_EMPTY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setEmptyView(BASE_EMPTY_LAYOUT_ID);
        }
    }

    /**
     * 设置记载中view
     *
     * @param listener
     * @param loadingAndRetryLayout
     */
    private void setupLoadingLayout(PageListener listener, PageLayout loadingAndRetryLayout) {
        if (listener.isSetLoadingLayout()) {
            int layoutId = listener.generateLoadingLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else {
                loadingAndRetryLayout.setLoadingView(listener.generateLoadingLayout());
            }
        } else {
            if (BASE_LOADING_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setLoadingView(BASE_LOADING_LAYOUT_ID);
        }
    }

    /**
     * 设置重新加载view
     *
     * @param listener
     * @param loadingAndRetryLayout
     */
    private void setupRetryLayout(PageListener listener, PageLayout loadingAndRetryLayout) {
        if (listener.isSetRetryLayout()) {
            int layoutId = listener.generateRetryLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else {
                loadingAndRetryLayout.setLoadingView(listener.generateRetryLayout());
            }
        } else {
            if (BASE_RETRY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setRetryView(BASE_RETRY_LAYOUT_ID);
        }
    }


    /**
     * 重新尝试接口
     */
    public interface ReloadListener {
        void reload();
    }
}
