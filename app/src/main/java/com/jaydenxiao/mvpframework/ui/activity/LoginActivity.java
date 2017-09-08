package com.jaydenxiao.mvpframework.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.FormatUtil;
import com.jaydenxiao.common.commonutils.SoftKeyBoardStateHelper;
import com.jaydenxiao.common.commonwidget.StatusBarCompat;
import com.jaydenxiao.mvpframework.R;
import com.jaydenxiao.mvpframework.ui.contract.LoginContract;
import com.jaydenxiao.mvpframework.ui.presenter.LoginPresenter;
import com.nineoldandroids.animation.ValueAnimator;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 类名：LoginActivity.java
 * 描述：登录界面
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2017/5/18
 * 最后修改时间：2017/5/18
 */

public class LoginActivity extends BaseActivity<LoginContract.View,LoginPresenter> implements LoginContract.View{


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

    private SoftKeyBoardStateHelper mSoftKeyBoardStateHelper;
    private SoftKeyBoardStateHelper.SoftKeyboardStateListener mKeyboardStateListener;


    @Override
    public int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    public LoginContract.View attachPresenterView() {
        return this;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarCompat.setTransparentForImageView(this,null);
        mSoftKeyBoardStateHelper=new SoftKeyBoardStateHelper(slRoot);
        mSoftKeyBoardStateHelper.addSoftKeyboardStateListener(mKeyboardStateListener=new SoftKeyBoardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                scaleAnimation(ivLogo.getHeight(), ivLogo.getHeight() / 2, ivLogo);
            }

            @Override
            public void onSoftKeyboardClosed() {
                scaleAnimation(ivLogo.getHeight(), ivLogo.getHeight() * 2, ivLogo);
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSoftKeyBoardStateHelper.removeSoftKeyboardStateListener(mKeyboardStateListener);
    }
}
