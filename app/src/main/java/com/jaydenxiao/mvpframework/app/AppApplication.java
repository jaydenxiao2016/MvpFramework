package com.jaydenxiao.mvpframework.app;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.baseapp.CrashHandler;
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
        if (!BuildConfig.LOG_DEBUG)  {//release模式
            CrashHandler crashHandlerBiz = CrashHandler.getInstance();
            crashHandlerBiz.init(getApplicationContext());
        }
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);

        // 初始化百度地图
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
