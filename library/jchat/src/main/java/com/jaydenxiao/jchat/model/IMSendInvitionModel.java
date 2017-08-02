package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMSendInvitionListener;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;

/**
 * 类名：IMAddUserToBlackModel.java
 * 描述：发送好友请求
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */

public class IMSendInvitionModel extends IMBaseModel<IMSendInvitionListener> {

    /**
     * 发送好友请求
     * @param targetUsername
     * @param targetAppKey
     * @param reason
     */
    public void sendVerify(String targetUsername, String targetAppKey,String  reason) {
        ContactManager.sendInvitationRequest(targetUsername, targetAppKey, reason,new BasicCallback() {
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
