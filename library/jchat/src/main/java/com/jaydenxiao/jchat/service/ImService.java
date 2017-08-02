package com.jaydenxiao.jchat.service;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jaydenxiao.common.baseapp.AppManager;
import com.jaydenxiao.jchat.R;
import com.jaydenxiao.jchat.model.IMEventModel;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 描述：常驻服务，用于获取Im的回调
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/19
 * 最后修改时间:2016/12/19
 */

public class ImService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.registerEventReceiver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 消息接收
     * @param event
     */
    public void onEventMainThread(MessageEvent event){
        Message msg = event.getMessage();
        setUnread(msg);
        switch (msg.getContentType()){
            case text:
                //处理文字消息
                IMEventModel.getInstance().textMessage(msg);
                break;
            case image:
                //处理图片消息
                IMEventModel.getInstance().imageMessage(msg);
                break;
            case voice:
                //处理语音消息
                IMEventModel.getInstance().voiceMessage(msg);
                break;
            case custom:
                //处理自定义消息
                IMEventModel.getInstance().customMessage(msg);
                break;
            case file:
                IMEventModel.getInstance().fileMessage(msg);
                break;
            case eventNotification:
                //处理事件提醒消息
                EventNotificationContent eventNotificationContent = (EventNotificationContent)msg.getContent();
                if(msg.getTargetType().name().equals("group")){
                    setGroupVerify(msg);
                }
                switch (eventNotificationContent.getEventNotificationType()){
                    case group_member_added:
                        //群成员加群事件
                        IMEventModel.getInstance().groupMemberAdded(msg);
                        break;
                    case group_member_removed:
                        //群成员被踢事件
                        IMEventModel.getInstance().groupMemberRemoved(msg);
                        break;
                    case group_member_exit:
                        //群成员退群事件
                        IMEventModel.getInstance().groupMemberExit(msg);
                        break;
                }
                break;
        }
    }

    /**
     * 登录状态变更接收
     * @param event
     */
    public void onEventMainThread(LoginStateChangeEvent event){
        LoginStateChangeEvent.Reason reason = event.getReason();//获取变更的原因
        UserInfo myInfo = event.getMyInfo();//获取当前被登出账号的信息
        String details = null;
        switch (reason) {
            case user_password_change:
                //用户密码在服务器端被修改
                IMEventModel.getInstance().passwordChange(myInfo);
                details = getString(R.string.str_offline_password);
                break;
            case user_logout:
                //用户换设备登录
                IMEventModel.getInstance().userLogout(myInfo);
                details = getString(R.string.str_offline_device);
                break;
            case user_deleted:
                //用户被删除
                IMEventModel.getInstance().userDeleted(myInfo);
                details = getString(R.string.str_offline_deleted);
                break;
        }
        showLogout(details);
    }

    /**
     * 联系人消息接收
     * @param event
     */
    public void onEventMainThread(ContactNotifyEvent event) {
        switch (event.getType()) {
            case invite_received://收到好友邀请
                IMEventModel.getInstance().inviteReceived(event);
                break;
            case invite_accepted://对方接收了你的好友邀请
                IMEventModel.getInstance().inviteAccepted(event);
                break;
            case invite_declined://对方拒绝了你的好友邀请
                IMEventModel.getInstance().inviteDeclined(event);
                break;
            case contact_deleted://对方将你从好友中删除
                IMEventModel.getInstance().contactDeleted(event);
                break;
            default:
                break;
        }
    }

    /**
     * 刷新会话列表
     * @param event
     */
    public void onEventMainThread(ConversationRefreshEvent event){
        IMEventModel.getInstance().conversationRefreshEvent(event);
    }

    /**
     * 登出提示
     * @param details
     */
    private void showLogout(String details) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(AppManager.getAppManager().currentActivity());
        dialog.setTitle(R.string.str_offline_prompt)
                .setMessage(details)
                .setPositiveButton(R.string.str_setting_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppManager.getAppManager().AppExit(ImService.this,false);
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 收到消息处理
     * @param notificationClickEvent 通知点击事件
     */
    public void onEventMainThread(NotificationClickEvent notificationClickEvent) {
        if (null == notificationClickEvent) {
            return;
        }
        Message msg = notificationClickEvent.getMessage();
//        if (msg != null) {
//            ConversationType type = msg.getTargetType();
//            Intent intent = new Intent(getApplicationContext(), IMChatActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (ConversationType.single.equals(type)) {
//                intent.putExtra(ImConstants.TARGET_ID, ((UserInfo) msg.getTargetInfo()).getUserName());
//                intent.putExtra(ImConstants.TARGET_APP_KEY, msg.getTargetAppKey());
//                intent.putExtra(ImConstants.CONV_TITLE, ((UserInfo) msg.getTargetInfo()).getNickname());
//                startActivity(intent);
//            } else if (ConversationType.group.equals(type)) {
//                intent.putExtra(ImConstants.GROUP_ID, ((GroupInfo) msg.getTargetInfo()).getGroupID());
//                intent.putExtra(ImConstants.MEMBERS_COUNT, ((GroupInfo) msg.getTargetInfo()).getGroupMembers().size());
//                intent.putExtra(ImConstants.FROM_CREATE_GROUP, false);
//                intent.putExtra(ImConstants.CONV_TITLE, ((GroupInfo) msg.getTargetInfo()).getGroupName());
//                intent.putExtra(ImConstants.DRAFT, "");//草稿
//                startActivity(intent);
//            }
//        }
    }

    /**
     * 设置会话未读数据
     * @param message
     */
    private void setUnread(Message message){
        if(null!=message){
            String readid="";
            if(message.getTargetType().equals(ConversationType.single)){
                readid=""+((UserInfo)message.getTargetInfo()).getUserID();
            }else if(message.getTargetType().equals(ConversationType.group)){
                readid=""+((GroupInfo)message.getTargetInfo()).getGroupID();
            }
        }
    }
    /**
     * 设置群组验证数据
     * @param msg
     */
    private void setGroupVerify(Message msg){
        if(null!=msg){
            EventNotificationContent event= (EventNotificationContent) msg.getContent();
        }
    }
}

