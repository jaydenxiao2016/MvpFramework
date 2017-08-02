package com.jaydenxiao.jchat.model;

import com.jaydenxiao.jchat.listener.IMRemoveGroupMembersListener;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 描述：移除群组成员Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public class IMRemoveGroupMembersModel extends IMBaseModel<IMRemoveGroupMembersListener> {

    /**
     * 移除群组成员
     *
     * @param groupID      群组id
     * @param userNameList 成员列表
     * @param listener     结果回调
     */
    public void removeGroupMembers(long groupID, List<String> userNameList, final IMRemoveGroupMembersListener listener) {
        setListener(listener);
        JMessageClient.removeGroupMembers(groupID, userNameList, new BasicCallback() {
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
