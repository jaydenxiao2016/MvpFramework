package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMBlackListListener;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetBlacklistCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 类名：IMDelUserFromBlackModel.java
 * 描述：获取黑名单列表
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */

public class IMGetBlackListModel extends IMBaseModel<IMBlackListListener> {
    /**
     * 获取黑名单列表
     *
     * @param imBlackListListener
     */
    public void getBlackList(IMBlackListListener imBlackListListener) {
        //设置回调监听
        setListener(imBlackListListener);
        JMessageClient.getBlacklist(new GetBlacklistCallback() {
            @Override
            public void gotResult(int status, String des, List<UserInfo> list) {
                if(mListener!=null) {
                    if (status == 0) {
                        mListener.onSuccess(list);
                    } else {
                        mListener.onError(des);
                    }
                }
            }
        });
    }

}

