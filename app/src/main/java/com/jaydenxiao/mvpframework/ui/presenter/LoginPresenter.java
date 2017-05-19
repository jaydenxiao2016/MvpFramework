package com.jaydenxiao.mvpframework.ui.presenter;


import com.jaydenxiao.common.baserx.RxSubscriber;
import com.jaydenxiao.mvpframework.app.AppCache;
import com.jaydenxiao.mvpframework.bean.CommonResponse;
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
        mRxManage.add(loginModel.login(userName, password).subscribe(new RxSubscriber<CommonResponse>(mContext,
                true, "登录中") {
            @Override
            protected void _onNext(CommonResponse commonResponse) {
                saveUser();
                if (commonResponse != null && commonResponse.isSuccess()) {
                    mView.loginSuccess();
                } else {
                    mView.showErrorTip(commonResponse.getMsg());
                }
            }

            @Override
            protected void _onError(String message) {
                saveUser();
                mView.showErrorTip(message);
            }
        }));
    }
    private void saveUser(){
        User user = new User();
        user.setUserName("张三");
        user.setUserId("131233");
        AppCache.getInstance().setUser(user);
    }
}

