package com.jaydenxiao.jchat;

import android.content.Intent;

import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.jchat.service.ImService;

import cn.jpush.im.android.api.JMessageClient;

/**
 * 类名：JChatApplication.java
 * 描述：
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2017/8/2
 * 最后修改时间：2017/8/2
 */

public class JChatApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.init(getApplicationContext(), true);
        JMessageClient.setDebugMode(true);
        //设置Notification的模式
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
        //开启接收im信息服务
        startService(new Intent(this, ImService.class));
    }
}
