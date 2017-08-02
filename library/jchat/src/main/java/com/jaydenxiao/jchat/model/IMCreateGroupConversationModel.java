package com.jaydenxiao.jchat.model;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * 描述：
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-29
 * 最后修改时间：2016-12-29
 */
public class IMCreateGroupConversationModel extends IMBaseModel {

    /**
     * 获取群聊消息
     *
     * @param groupId 群聊id
     */
    public Conversation getGroupConversation(long groupId) {
        Conversation conversation = JMessageClient.getGroupConversation(groupId);
        if (conversation == null) {
            conversation = Conversation.createGroupConversation(groupId);
        }
        return conversation;
    }

}
