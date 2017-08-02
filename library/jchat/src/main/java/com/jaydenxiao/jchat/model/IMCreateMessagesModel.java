package com.jaydenxiao.jchat.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import cn.jpush.im.android.api.exceptions.JMFileSizeExceedException;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * 描述：创建消息Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：程林
 * 创建时间:2016/12/26
 * 最后修改时间:2016/12/26
 */
public class IMCreateMessagesModel {
    /**
     * 创建一条单聊文本消息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param username 聊天对象用户名
     * @param text     文本内容
     * @return 消息对象
     */
    public static Message createSingleTextMessage(String username, String text) {
        return Conversation.createSingleConversation(username).createSendTextMessage(text);
    }

    /**
     * 创建一条群聊文本信息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param groupID 群组的groupID
     * @param text    文本内容
     * @return 消息对象
     */
    public static Message createGroupTextMessage(long groupID, String text) {
        return Conversation.createGroupConversation(groupID).createSendTextMessage(text);
    }

    /**
     * 创建一条单聊图片信息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param username  聊天对象的用户名
     * @param imageFile 图片文件
     * @return 消息对象
     * @throws FileNotFoundException
     */
    public static Message createSingleImageMessage(String username, File imageFile) throws FileNotFoundException {
        return Conversation.createSingleConversation(username).createSendImageMessage(imageFile);
    }

    /**
     * 创建一条群聊图片信息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param groupID   群组的groupID
     * @param imageFile 图片文件
     * @return 消息对象
     * @throws FileNotFoundException
     */
    public static Message createGroupImageMessage(long groupID, File imageFile) throws FileNotFoundException {
        return Conversation.createGroupConversation(groupID).createSendImageMessage(imageFile);
    }

    /**
     * 创建一条单聊语音信息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param username  聊天对象的用户名
     * @param voiceFile 语音文件
     * @param duration  语音文件时长
     * @return 消息对象
     * @throws FileNotFoundException
     */
    public static Message createSingleVoiceMessage(String username, File voiceFile, int duration) throws FileNotFoundException {
        return Conversation.createSingleConversation(username).createSendVoiceMessage(voiceFile, duration);
    }

    /**
     * 创建一条群聊语音信息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param groupID   群组groupID
     * @param voiceFile 语音文件
     * @param duration  语音文件时长
     * @return 消息对象
     * @throws FileNotFoundException
     */
    public static Message createGroupVoiceMessage(long groupID, File voiceFile, int duration) throws FileNotFoundException {
        return Conversation.createGroupConversation(groupID).createSendVoiceMessage(voiceFile, duration);
    }

    /**
     * 创建一条单聊地理位置消息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param username  聊天对象的用户名
     * @param latitude  纬度信息
     * @param longitude 经度信息
     * @param scale     地图缩放比例
     * @param address   详细地址信息
     * @return 消息对象
     */
    public static Message createSingleLocationMessage(String username, double latitude, double longitude, int scale, String address) {
        return Conversation.createSingleConversation(username).createLocationMessage(latitude, longitude, scale, address);
    }

    /**
     * 创建一条群聊地理位置消息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param groupId   群组groupID
     * @param latitude  纬度信息
     * @param longitude 经度信息
     * @param scale     地图缩放比例
     * @param address   详细地址信息
     * @return 消息对象
     */
    public static Message createGroupLocationMessage(long groupId, double latitude, double longitude, int scale, String address) {
        return Conversation.createGroupConversation(groupId).createLocationMessage(latitude, longitude, scale, address);
    }

    /**
     * 创建一条单聊file消息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param userName 聊天对象的用户名
     * @param file     发送的文件
     * @param fileName 指定发送的文件名称,如果不填或为空，则默认使用文件原名。
     * @return 消息对象
     * @throws FileNotFoundException
     */
    public static Message createSingleFileMessage(String userName, File file, String fileName) throws FileNotFoundException, JMFileSizeExceedException {
        return Conversation.createSingleConversation(userName).createSendFileMessage(file, fileName);
    }

    /**
     * 创建一条群聊file消息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
     * 快捷的创建一条消息。其他的情况下推荐使用
     * 接口来创建消息
     *
     * @param groupID  群组groupID
     * @param file     发送的文件
     * @param fileName 指定发送的文件名称,如果不填或为空，则默认使用文件原名。
     * @return 消息对象
     * @throws FileNotFoundException
     */
    public static Message createGroupFileMessage(long groupID, File file, String fileName) throws FileNotFoundException, JMFileSizeExceedException {
        return Conversation.createGroupConversation(groupID).createSendFileMessage(file, fileName);
    }

    /**
     * 创建一条单聊自定义消息
     *
     * @param username  聊天对象username
     * @param valuesMap 包含自定义键值对的map.
     * @return 消息对象
     */
    public static Message createSingleCustomMessage(String username,
                                                    Map<String, String> valuesMap) {
        return Conversation.createSingleConversation(username).createSendCustomMessage(valuesMap);
    }

    /**
     * 创建一条群聊自定义消息
     *
     * @param groupID   群组groupID
     * @param valuesMap 包含了自定义键值对的map
     * @return 消息对象
     */
    public static Message createGroupCustomMessage(long groupID,
                                                   Map<String, String> valuesMap) {
        return Conversation.createGroupConversation(groupID).createSendCustomMessage(valuesMap);
    }
}
