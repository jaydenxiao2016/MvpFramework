package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGetConversationListListener;

import cn.jpush.im.android.api.JMessageClient;

/**
 * 描述：获取回话列表 Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */
public class IMGetConversationListModel extends IMBaseModel<IMGetConversationListListener>{
    /**
     * 从本地数据库中获取会话列表，默认按照会话的最后一条消息的时间，降序排列
     * @return 会话列表
     */
    public void getConversationList() {
        mListener.onSuccess(JMessageClient.getConversationList());
    }
    
    /**
     * 从本地数据库中获取会话列表,默认不排序
     * @return 会话列表
     */
    public void getConversationListByDefault() {
        mListener.onSuccess(JMessageClient.getConversationListByDefault());
    }
}
