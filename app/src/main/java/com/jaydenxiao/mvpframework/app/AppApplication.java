package com.jaydenxiao.mvpframework.app;

import com.jaydenxiao.common.baseapp.*;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.mvpframework.BuildConfig;


/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //捕获异常
        if (!BuildConfig.LOG_DEBUG&&!AppConfig.isTest)  {//release模式
            CrashHandler crashHandlerBiz = CrashHandler.getInstance();
            crashHandlerBiz.init(getApplicationContext());
        }
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);
    }
}
