package com.jaydenxiao.jchat.listener;


import java.util.List;

import cn.jpush.im.android.api.model.Conversation;

/**
 * 类名：IMGetConversationListListener
 * 描述：IM获取会话列表监听
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-28
 * 最后修改时间：2016-12-28
 */
public interface IMGetConversationListListener extends IMBaseListener {
    void onSuccess(List<Conversation> list);
}