package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMCreateGroupListener;
import com.jaydenxiao.jchat.utils.HandleImStatusUtil;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.CreateGroupCallback;
import cn.jpush.im.android.api.model.Conversation;

/**
 * 描述：创建群组Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：张绪飞
 * 创建时间:2016/12/26
 * 最后修改时间:2016/12/26
 */
public class IMCreateGroupModel extends IMBaseModel<IMCreateGroupListener> {
    /**
     * 创建群组，群组创建成功后，创建者会默认包含在群成员中
     * @param groupName 群名称
     * @param groupDesc 群描述
     * @param listener 结果回调
     */
    public void createGroup(String groupName, String groupDesc, IMCreateGroupListener listener) {
        setListener(listener);
        JMessageClient.createGroup(groupName, groupDesc, new CreateGroupCallback() {
        
            @Override
            public void gotResult(int status, String responseMsg, long groupId) {
                if (status == 0) { // 0	Success	成功
                    //创建群会话
                    Conversation.createGroupConversation(groupId);
                    if (mListener != null) {
                        mListener.onSuccess(groupId);
                    }
                } else {
                    if (mListener != null) {
                        mListener.onError(HandleImStatusUtil.onHandle(status));
                    }
                }
            }
        });
    }
}
