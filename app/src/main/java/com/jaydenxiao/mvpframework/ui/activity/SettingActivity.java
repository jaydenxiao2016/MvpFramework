package com.jaydenxiao.mvpframework.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basemvp.BaseView;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonwidget.RoundedImageView;
import com.jaydenxiao.common.commonwidget.StatusBarCompat;
import com.jaydenxiao.common.commonwidget.WaveView;
import com.jaydenxiao.mvpframework.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类名：SettingActivity.java
 * 描述：设置
 * 作者：xsf
 * 创建时间：2017/5/19
 * 最后修改时间：2017/5/19
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.wave_view)
    WaveView waveView;
    @Bind(R.id.img_logo)
    RoundedImageView imgLogo;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_code)
    TextView tvCode;

    @Override
    public int getLayoutId() {
        return R.layout.act_setting_main;
    }

    @Override
    public BaseView attachPresenterView() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarCompat.setTransparentForImageView(this,null);
        //设置头像
        ImageLoaderUtils.displayAvatar(this, imgLogo, "");
    }

    @OnClick(R.id.iv_back)
    public void clickBack() {
        finish();
    }

}
