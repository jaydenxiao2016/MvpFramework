package com.jaydenxiao.mvpframework.ui.contract;

import com.jaydenxiao.common.basemvp.BaseModel;
import com.jaydenxiao.common.basemvp.BasePresenter;
import com.jaydenxiao.common.basemvp.BaseView;
import com.jaydenxiao.mvpframework.bean.User;

import rx.Observable;

/**
 * des:登录
 * Created by xsf
 * on 2016.09.16:52
 */

public interface LoginContract {
    interface Model extends BaseModel {
        Observable<User> login(String userName, String password);
    }

    interface View extends BaseView {
        void loginSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void LoginRequest(String userName, String password);
    }
}
