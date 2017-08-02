package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMLoginListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 描述：IM登录Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */

public class IMLoginModel extends IMBaseModel<IMLoginListener>{

    /**
     *  IM登录
     * @param userName
     * @param password
     * @param listener
     */
    public void login(String userName,String password,IMLoginListener listener){
        setListener(listener);
        JMessageClient.login(userName, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    mListener.onSuccess();
                }else{
                    mListener.onError(s);
                }
            }
        });
    }
}
