package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMUpdateGroupNameListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 描述：更新群组名称Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public class IMUpdateGroupNameModel extends IMBaseModel<IMUpdateGroupNameListener> {

    /**
     * 更新群组名称
     *
     * @param groupID   群组id
     * @param groupName 群组名称
     * @param listener  结果回调
     */
    public void updateGroupName(long groupID, String groupName, final IMUpdateGroupNameListener listener) {
        setListener(listener);
        JMessageClient.updateGroupName(groupID, groupName, new BasicCallback() {
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
