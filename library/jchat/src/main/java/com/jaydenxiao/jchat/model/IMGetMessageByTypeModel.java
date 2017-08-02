package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGetMessageByTypeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * 描述：获取消息列表 Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */
public class IMGetMessageByTypeModel extends IMBaseModel<IMGetMessageByTypeListener> {

    /**
     * 获取带时间分类的图片列表
     * @param conversation
     * @return
     */
    public void getImageList(Conversation conversation) {
        getTypeList(conversation, ContentType.image);
    }

    /**
     * 获取带时间分类的文件列表
     * @param conversation
     * @return
     */
    public void getFileList(Conversation conversation) {
        getTypeList(conversation, ContentType.file);
    }


    private void getTypeList(Conversation conversation, ContentType type) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        ArrayList<Message> list = getMessageList(conversation, type);
        Collections.sort(list, new SortByTime());
        HashMap<String, ArrayList<Message>> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String time="";
            try {
                time = format.format(list.get(i).getCreateTime());
            } catch (Exception e) {
                time=format.format(System.currentTimeMillis());
            }
            if(!map.containsKey(time)){
                map.put(time, new ArrayList<Message>());
            }
            map.get(time).add(list.get(i));
        }
        if(null!=map&&0<map.size()){
            mListener.onSuccess(map);
        }else{
            mListener.onError("暂无数据！");
        }
    }


    /**
     * 根据消息类型获取本地消息列表
     *
     * @param conversation
     * @param type
     */
    public void getMessage(Conversation conversation, ContentType type) {
        ArrayList<Message> list = getMessageList(conversation, type);
        if (null != list && 0 < list.size()) {
            mListener.onSuccess(list);
        } else {
            mListener.onError("暂无数据！");
        }
    }

    private ArrayList<Message> getMessageList(Conversation conversation, ContentType type) {
        ArrayList<Message> list = new ArrayList<>();
        if (null != conversation) {
            List<Message> messagelist = conversation.getAllMessage();
            if (null != messagelist && 0 < messagelist.size()) {
                for (int i = 0; i < messagelist.size(); i++) {
                    if (type == messagelist.get(i).getContentType()) {
                        list.add(messagelist.get(i));
                    }
                }
            }
        }
        return list;
    }

    /**
     * 按时间降序排列
     */
    private class SortByTime implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Message m1 = (Message) o1;
            Message m2 = (Message) o2;
            if (m1.getCreateTime() > m2.getCreateTime()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

}
