package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGetGroupMembersListener;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupMembersCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 描述：获取群组成员列表Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public class IMGetGroupMembersModel extends IMBaseModel<IMGetGroupMembersListener> {

    /**
     * 获取群组成员列表
     *
     * @param groupID  群组id
     * @param listener 结果回调
     */
    public void getGroupMembers(long groupID, final IMGetGroupMembersListener listener) {
        setListener(listener);
        JMessageClient.getGroupMembers(groupID, new GetGroupMembersCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, List<UserInfo> list) {
                if (listener != null) {
                    if (responseCode == 0) {
                        listener.onSuccess(list);
                    } else {
                        listener.onError(responseMessage);
                    }
                }
            }
        });
    }

}
