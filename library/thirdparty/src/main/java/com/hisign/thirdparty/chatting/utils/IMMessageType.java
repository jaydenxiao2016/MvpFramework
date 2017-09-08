package com.hisign.thirdparty.chatting.utils;

/**
 * 描述：聊天界面消息类型
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：huangyu
 * 版本：V1.0
 * 创建时间：2016-12-27
 * 最后修改时间：2016-12-27
 */
public class IMMessageType {

    // 14种Item的类型
    public static final int TYPE_TOTAL_COUNT = 16;

    // 文本
    public static final int TYPE_RECEIVE_TXT = 0;
    public static final int TYPE_SEND_TXT = 1;

    // 图片
    public static final int TYPE_SEND_IMAGE = 2;
    public static final int TYPE_RECEIVER_IMAGE = 3;

    // 位置
    public static final int TYPE_SEND_LOCATION = 4;
    public static final int TYPE_RECEIVER_LOCATION = 5;

    // 语音
    public static final int TYPE_SEND_VOICE = 6;
    public static final int TYPE_RECEIVER_VOICE = 7;

    // 视频
    public static final int TYPE_SEND_VIDEO = 8;
    public static final int TYPE_RECEIVE_VIDEO = 9;

    // 文件
    public static final int TYPE_SEND_FILE = 10;
    public static final int TYPE_RECEIVE_FILE = 11;

    // 名片
    public static final int TYPE_SEND_CARD = 12;
    public static final int TYPE_RECEIVE_CARD = 13;

    // 群成员变动
    public static final int TYPE_GROUP_CHANGE = 14;

    // 自定义消息
    public static final int TYPE_CUSTOM_TXT = 15;

}
