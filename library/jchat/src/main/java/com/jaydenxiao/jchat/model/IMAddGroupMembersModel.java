package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMAddGroupMembersListener;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 描述：加群组成员Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public class IMAddGroupMembersModel extends IMBaseModel<IMAddGroupMembersListener> {

    /**
     * 加群组成员
     *
     * @param groupID      群组id
     * @param userNameList 成员列表
     * @param listener     结果回调
     */
    public void addGroupMembers(long groupID, List<String> userNameList, final IMAddGroupMembersListener listener) {
        setListener(listener);
        JMessageClient.addGroupMembers(groupID, userNameList, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (listener != null) {
                    if (responseCode == 0) {
                        listener.onSuccess();
                    } else {
                        listener.onError(responseMessage);
                    }
                }
            }
        });
    }

    /**
     * 加群组成员（实现跨应用添加）
     *
     * @param groupID      群组id
     * @param appKey       应用id
     * @param userNameList 成员列表
     * @param listener     结果回调
     */
    public void addGroupMembers(long groupID, String appKey, List<String> userNameList, final IMAddGroupMembersListener listener) {
        setListener(listener);
        JMessageClient.addGroupMembers(groupID, appKey, userNameList, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (listener != null) {
                    if (responseCode == 0) {
                        listener.onSuccess();
                    } else {
                        listener.onError(responseMessage);
                    }
                }
            }
        });
    }

}
