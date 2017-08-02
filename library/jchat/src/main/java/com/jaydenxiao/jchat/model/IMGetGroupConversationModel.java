package com.jaydenxiao.jchat.model;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * 描述：获取群组会话信息Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */
public class IMGetGroupConversationModel extends IMBaseModel{
    /**
     * 获取群组会话信息
     * @param groupId 目标群的群id
     * @return 根据参数匹配得到的群聊会话对象
     */
    public Conversation getGroupConversation(long groupId) {
        return JMessageClient.getGroupConversation(groupId);
    }
}
