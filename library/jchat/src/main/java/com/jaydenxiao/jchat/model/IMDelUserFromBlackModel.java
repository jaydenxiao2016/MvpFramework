package com.jaydenxiao.jchat.model;

import com.jaydenxiao.jchat.listener.IMBlackListener;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 类名：IMDelUserFromBlackModel.java
 * 描述：从黑名单移除
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */

public class IMDelUserFromBlackModel extends IMBaseModel<IMBlackListener> {
    /**
     * 把单个用户从黑名单移除
     * @param userName
     * @param targetAppKey
     * @param imBlackListenerListener
     */
    public void delUserFromBlack(String userName, String targetAppKey, IMBlackListener imBlackListenerListener) {
        //设置回调监听
        setListener(imBlackListenerListener);
        List<String> list = new ArrayList<String>();
        list.add(userName);
        JMessageClient.delUsersFromBlacklist(list, targetAppKey, new BasicCallback() {
            @Override
            public void gotResult(int status, String desc) {
                if(mListener!=null) {
                    if (status == 0) {
                        mListener.onSuccess();
                    } else {
                        mListener.onError(desc);
                    }
                }
            }
        });
    }

    /**
     * 把多个用户从黑名单移除
     * @param list
     * @param targetAppKey
     * @param imBlackListenerListener
     */
    public void delUserFromBlack(List<String> list, String targetAppKey, IMBlackListener imBlackListenerListener) {
        //设置回调监听
        setListener(imBlackListenerListener);
        JMessageClient.delUsersFromBlacklist(list, targetAppKey, new BasicCallback() {
            @Override
            public void gotResult(int status, String desc) {
                if(mListener!=null) {
                    if (status == 0) {
                        mListener.onSuccess();
                    } else {
                        mListener.onError(desc);
                    }
                }
            }
        });
    }
}

