package com.jaydenxiao.jchat.listener;


import cn.jpush.im.android.api.model.UserInfo;

/**
 * 类名：IMGetUserInfoListener
 * 描述：IM获取用户信息回调
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：杨培尧
 * 版本：V1.0
 * 创建时间：2016-12-6
 * 最后修改时间：2016-12-6
 */
public interface IMGetUserInfoListener extends IMBaseListener {
    void onSuccess(UserInfo userinfo);
    void onIMSuccess(UserInfo userinfo);
}
