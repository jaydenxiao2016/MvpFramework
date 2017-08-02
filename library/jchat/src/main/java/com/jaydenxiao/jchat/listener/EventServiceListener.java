package com.jaydenxiao.jchat.listener;

import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 描述：聊天消息监听器
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间()2016/12/26
 * 最后修改时间()2016/12/26
 */
public interface EventServiceListener {
    /**
     * 处理文字消息
     *
     * @param message 消息实体
     */
    void textMessage(Message message);

    /**
     * 处理图片消息
     *
     * @param message 消息实体
     */
    void imageMessage(Message message);

    /**
     * 处理语音消息
     *
     * @param message 消息实体
     */
    void voiceMessage(Message message);

    /**
     * 处理文件消息
     *
     * @param message 消息实体
     */
    void fileMessage(Message message);

    /**
     * 处理自定义消息
     *
     * @param message 消息实体
     */
    void customMessage(Message message);

    /**
     * 群成员加群事件
     *
     * @param message 消息实体
     */
    void groupMemberAdded(Message message);

    /**
     * 群成员被踢事件
     *
     * @param message 消息实体
     */
    void groupMemberRemoved(Message message);

    /**
     * 群成员退群事件
     *
     * @param message 消息实体
     */
    void groupMemberExit(Message message);

    /**
     * 用户密码在服务器端被修改
     *
     * @param info 用户信息
     */
    void passwordChange(UserInfo info);

    /**
     * 用户换设备登录
     *
     * @param info 用户信息
     */
    void userLogout(UserInfo info);

    /**
     * 用户被删除
     *
     * @param info 用户信息
     */
    void userDeleted(UserInfo info);

    /**
     * 收到好友邀请
     *
     * @param event 事件信息
     */
    void inviteReceived(ContactNotifyEvent event);

    /**
     * 对方接收了你的好友邀请
     *
     * @param event 事件信息
     */
    void inviteAccepted(ContactNotifyEvent event);

    /**
     * 对方拒绝了你的好友邀请
     *
     * @param event 事件信息
     */
    void inviteDeclined(ContactNotifyEvent event);

    /**
     * 对方将你从好友中删除
     *
     * @param event 事件信息
     */
    void contactDeleted(ContactNotifyEvent event);

    /**
     * 刷新会话列表
     *
     * @param event 事件信息
     */
    void conversationRefreshEvent(ConversationRefreshEvent event);
}
