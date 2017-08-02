package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMAcceptInvitionListener;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;

/**
 * 类名：IMAcceptInvitionModel.java
 * 描述：拒绝加为好友
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */

public class IMDeclineInvitionModel extends IMBaseModel<IMAcceptInvitionListener> {
    /**
     * 拒绝加为好友
     *
     * @param userName
     * @param targetAppKey
     * @param reason
     * @param imAcceptInvitionListener
     */
    public void acceptInvitation(String userName, String targetAppKey, String reason, IMAcceptInvitionListener imAcceptInvitionListener) {
        //设置回调监听
        setListener(imAcceptInvitionListener);
        ContactManager.declineInvitation(userName, targetAppKey, reason, new BasicCallback() {
            @Override
            public void gotResult(int status, String desc) {
                if(mListener!=null) {
                    if (status == 0) {
                        mListener.onSuccess();
                    } else {
                        mListener.onError(desc);
                    }
                }
            }
        });
    }
}
