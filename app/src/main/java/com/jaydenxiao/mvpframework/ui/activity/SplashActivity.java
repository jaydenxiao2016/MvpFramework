package com.jaydenxiao.mvpframework.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basemvp.BaseView;
import com.jaydenxiao.common.baserx.RxSchedulers;
import com.jaydenxiao.mvpframework.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;


/**
 * 类名：SplashActivity.java
 * 描述：
 * 作者：xsf
 * 创建时间：2017/1/11
 * 最后修改时间：2017/1/11
 */

public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    public BaseView attachPresenterView() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决android 程序按下home键后 到后台直接退出了。再次点击桌面图标打开是新开的，不会回到上次浏览的页面
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //延时2s打开主界面
        getRxManager().add(Observable.timer(2, TimeUnit.SECONDS).compose(RxSchedulers.<Long>io_main()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                startActivity(LoginActivity.class);
                finish();
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放windowBackground资源
        getWindow().setBackgroundDrawable(null);
    }
}
