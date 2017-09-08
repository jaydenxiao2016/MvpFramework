package com.jaydenxiao.mvpframework.ui.model;


import com.jaydenxiao.mvpframework.api.ApiServiceManager;
import com.jaydenxiao.mvpframework.bean.User;
import com.jaydenxiao.mvpframework.ui.contract.LoginContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * des:登录
 * Created by xsf
 * on 2016.09.17:28
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<User> login(String userName, String password) {
        return ApiServiceManager.getInstance().login(userName, password)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onDestroy() {

    }
}
