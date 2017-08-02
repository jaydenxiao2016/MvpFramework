package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.EventServiceListener;

import java.util.ArrayList;

import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 描述：聊天消息监听管理类
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/26
 * 最后修改时间:2016/12/26
 */
public class IMEventModel {
    private static volatile IMEventModel instance = null;
    /**
     * 监听列表
     */
    private ArrayList<EventServiceListener> listeners = new ArrayList<EventServiceListener>();

    public static IMEventModel getInstance() {
        if (instance == null) {
            synchronized (IMEventModel.class) {
                if (instance == null) {
                    instance = new IMEventModel();
                }
            }
        }
        return instance;
    }

    public void registerMessageEvent(EventServiceListener listener) {
        if (listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
    }

    public void unRegisterMessageEvent(EventServiceListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    /**
     * 文字消息通知
     *
     * @param message
     */
    public void textMessage(Message message) {
        for (EventServiceListener data : listeners) {
            data.textMessage(message);
        }
    }

    /**
     * 处理图片消息
     *
     * @param message
     */
    public void imageMessage(Message message) {
        for (EventServiceListener data : listeners) {
            data.imageMessage(message);
        }
    }

    /**
     * 处理语音消息
     *
     * @param message
     */
    public void voiceMessage(Message message) {
        for (EventServiceListener data : listeners) {
            data.voiceMessage(message);
        }
    }

    /**
     * 处理文件消息
     *
     * @param message
     */
    public void fileMessage(Message message) {
        for (EventServiceListener data : listeners) {
            data.fileMessage(message);
        }
    }

    /**
     * 处理自定义消息
     *
     * @param message
     */
    public void customMessage(Message message) {
        for (EventServiceListener data : listeners) {
            data.customMessage(message);
        }
    }

    /**
     * 群成员加群事件
     *
     * @param message
     */
    public void groupMemberAdded(Message message) {
        for (EventServiceListener data : listeners) {
            data.groupMemberAdded(message);
        }
    }

    /**
     * 群成员被踢事件
     *
     * @param message
     */
    public void groupMemberRemoved(Message message) {
        for (EventServiceListener data : listeners) {
            data.groupMemberRemoved(message);
        }
    }

    /**
     * 群成员退群事件
     *
     * @param message
     */
    public void groupMemberExit(Message message) {
        for (EventServiceListener data : listeners) {
            data.groupMemberExit(message);
        }
    }

    /**
     * 群成员退群事件
     *
     * @param info
     */
    public void passwordChange(UserInfo info) {
        for (EventServiceListener data : listeners) {
            data.passwordChange(info);
        }
    }

    /**
     * 群成员退群事件
     *
     * @param info
     */
    public void userLogout(UserInfo info) {
        for (EventServiceListener data : listeners) {
            data.userLogout(info);
        }
    }

    /**
     * 群成员退群事件
     *
     * @param info
     */
    public void userDeleted(UserInfo info) {
        for (EventServiceListener data : listeners) {
            data.userDeleted(info);
        }
    }

    /**
     * 对方接收了你的好友邀请
     *
     * @param event
     */
    public void inviteAccepted(ContactNotifyEvent event) {
        for (EventServiceListener data : listeners) {
            data.inviteAccepted(event);
        }
    }

    /**
     * 对方拒绝了你的好友邀请
     *
     * @param event
     */
    public void inviteDeclined(ContactNotifyEvent event) {
        for (EventServiceListener data : listeners) {
            data.inviteDeclined(event);
        }
    }

    /**
     * 收到好友邀请
     *
     * @param event
     */
    public void inviteReceived(ContactNotifyEvent event) {
        for (EventServiceListener data : listeners) {
            data.inviteReceived(event);
        }
    }

    /**
     * 对方将你从好友中删除
     *
     * @param event
     */
    public void contactDeleted(ContactNotifyEvent event) {
        for (EventServiceListener data : listeners) {
            data.contactDeleted(event);
        }
    }

    /**
     * 刷新会话列表
     * @param event
     */
    public void conversationRefreshEvent(ConversationRefreshEvent event) {
        for (EventServiceListener data : listeners) {
            data.conversationRefreshEvent(event);
        }
    }
}
