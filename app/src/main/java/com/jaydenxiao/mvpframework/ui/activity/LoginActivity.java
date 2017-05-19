package com.jaydenxiao.mvpframework.ui.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.DisplayUtil;
import com.jaydenxiao.common.commonutils.FormatUtil;
import com.jaydenxiao.mvpframework.R;
import com.jaydenxiao.mvpframework.ui.contract.LoginContract;
import com.jaydenxiao.mvpframework.ui.presenter.LoginPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 类名：LoginActivity.java
 * 描述：登录界面
 * 作者：xsf
 * 创建时间：2017/5/18
 * 最后修改时间：2017/5/18
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnLayoutChangeListener {


    @Bind(R.id.met_user_name)
    MaterialEditText metUserName;
    @Bind(R.id.met_password)
    MaterialEditText metPassword;
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.view_gad)
    View viewGad;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.tv_bottom_title)
    TextView tvBottomTitle;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.sl_root)
    ScrollView slRoot;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;

    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;


    @Override
    public int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    public void attachPresenterView() {
        mPresenter.setV(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        SetStatusBarColor(ContextCompat.getColor(this, R.color.black));
        //阀值设置为屏幕高度的1/3
        keyHeight = DisplayUtil.getScreenHeight(this) / 3;
    }

    /**
     * 监听登录
     */
    @OnClick(R.id.bt_login)
    public void onClickLogin() {
        if (checkValid()) {
            mPresenter.LoginRequest(metUserName.getText().toString(), metPassword.getText().toString());
        }
    }

    /**
     * 检验输入合法性
     *
     * @return
     */
    private boolean checkValid() {
        if (!FormatUtil.isMobileNO(metUserName.getText().toString())) {
            metUserName.setError(getString(R.string.str_wrong_phone_tip));
            return false;
        } else if (metPassword.getText().toString().length() < 6) {
            metPassword.setError(getString(R.string.str_wrong_pwd_tip));
            return false;
        } else {
            return true;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        slRoot.addOnLayoutChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        slRoot.removeOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            scaleAnimation(ivLogo.getHeight(), ivLogo.getHeight() / 2, ivLogo);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            scaleAnimation(ivLogo.getHeight(), ivLogo.getHeight() * 2, ivLogo);
        }
    }

    /**
     * logo属性动画缩小放大
     */
    private void scaleAnimation(int from, int to, final View view) {
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.setDuration(300);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = height;
                layoutParams.width = height;
                view.requestLayout();
            }
        });
    }

    @Override
    public void showErrorTip(String msg) {
        startActivity(WorkMainActivity.class);
        finish();
    }

    @Override
    public void loginSuccess() {
        startActivity(WorkMainActivity.class);
        finish();
    }

}
