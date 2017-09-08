package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGetUserInfoListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 描述：IM获取用户信息Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */

public class IMGetUserInfoModel extends IMBaseModel<IMGetUserInfoListener>{

    /**
     * IM获取用户信息
     * @param name
     * @param listener
     */
    public void getUserInfo(final String name, IMGetUserInfoListener listener){
        setListener(listener);
    }

    /**
     * 获取极光当前本地用户
     * @return
     */
    public UserInfo getCurrentUserInfo(){
        return JMessageClient.getMyInfo();
    }

}
