package com.jaydenxiao.jchat.model;

import cn.jpush.im.android.api.JMessageClient;

/**
 * 描述：删除单聊会话Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */
public class IMDeleteSingleConversationModel extends IMBaseModel{
    /**
     * 删除单聊的会话，同时删除掉本地聊天记录
     * @param targetName 目标用户用户名
     * @return 是否删除成功
     */
    public static boolean deleteSingleConversation(String targetName) {
        return JMessageClient.deleteSingleConversation(targetName);
    }
}
