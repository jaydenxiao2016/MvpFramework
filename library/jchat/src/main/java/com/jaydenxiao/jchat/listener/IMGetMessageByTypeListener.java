package com.jaydenxiao.jchat.listener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.model.Message;

/**
 * 类名：IMGetMessageByTypeListener
 * 描述：IM获取消息列表监听
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-28
 * 最后修改时间：2016-12-28
 */
public interface IMGetMessageByTypeListener extends IMBaseListener {
    /**
     * 根据类型返回列表
     * @param list
     */
    void onSuccess(List<Message> list);

    /**
     * 根据类型和时间返回map集合
     * @param map
     */
    void onSuccess(HashMap<String, ArrayList<Message>> map);
}