package com.jaydenxiao.jchat.model;

import cn.jpush.im.android.api.JMessageClient;

/**
 * 描述：删除群组会话Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */
public class IMDeleteGroupConversationModel extends IMBaseModel{
    /**
     * 删除群聊的会话，同时删除掉本地聊天记录
     * @param groupId 群组id
     * @return 是否删除成功
     */
    public static boolean deleteGroupConversation(long groupId) {
        return JMessageClient.deleteGroupConversation(groupId);
    }

}
