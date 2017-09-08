package com.jaydenxiao.mvpframework.ui.presenter;


import com.jaydenxiao.common.baserx.RxSubscriber;
import com.jaydenxiao.mvpframework.app.AppCache;
import com.jaydenxiao.mvpframework.bean.User;
import com.jaydenxiao.mvpframework.ui.contract.LoginContract;
import com.jaydenxiao.mvpframework.ui.model.LoginModel;


/**
 * des:登录presenter
 * Created by xsf
 * on 2016.09.16:56
 */

public class LoginPresenter extends LoginContract.Presenter {

    private LoginModel loginModel;

    @Override
    public void onStart() {
        loginModel = new LoginModel();
    }

    @Override
    public void LoginRequest(String userName, final String password) {
        mRxManage.add(loginModel.login(userName, password).subscribe(new RxSubscriber<User>(mContext,
                true, "登录中") {
            @Override
            protected void _onNext(User commonResponse) {
                AppCache.getInstance().setUser(commonResponse);
                if (commonResponse != null) {
                    mView.loginSuccess();
                }
            }

            @Override
            protected void _onError(String message) {
                AppCache.getInstance().setUser(new User());
                mView.showErrorTip(message);
            }
        }));
    }

}

