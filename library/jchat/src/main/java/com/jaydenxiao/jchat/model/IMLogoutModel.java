package com.jaydenxiao.jchat.model;


import cn.jpush.im.android.api.JMessageClient;

/**
 * 描述：IM登出Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */

public class IMLogoutModel extends IMBaseModel{

    /**
     * IM登出
     */
    public void logout(){
        JMessageClient.logout();
    }



}
