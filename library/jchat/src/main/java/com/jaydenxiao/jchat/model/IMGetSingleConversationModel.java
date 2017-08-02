package com.jaydenxiao.jchat.model;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * 描述：获取单聊会话信息Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */
public class IMGetSingleConversationModel extends IMBaseModel {

    /**
     * 获取单聊会话信息，默认获取本appkey下username的单聊会话
     *
     * @param targetName 目标用户名
     * @return 根据参数匹配得到的单聊会话对象
     */
    public Conversation getSingleConversation(String targetName) {
        Conversation singleConversation = JMessageClient.getSingleConversation(targetName);
        if (singleConversation == null) {
            singleConversation = Conversation.createSingleConversation(targetName);
        }
        return singleConversation;
    }

    /**
     * 获取单聊会话信息，默认获取本appkey下username的单聊会话
     *
     * @param targetKey 目标用户名
     * @return 根据参数匹配得到的单聊会话对象
     */
    public Conversation getSingleConversation(String targetId, String targetKey) {
        Conversation singleConversation = JMessageClient.getSingleConversation(targetId, targetKey);
        if (singleConversation == null) {
            singleConversation = Conversation.createSingleConversation(targetId);
        }
        return singleConversation;
    }

}
