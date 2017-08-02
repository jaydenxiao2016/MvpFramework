package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMExitGroupListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 描述：退出群组Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public class IMExitGroupModel extends IMBaseModel<IMExitGroupListener> {

    /**
     * 退出群组
     *
     * @param groupId  群组id
     * @param listener 结果回调
     */
    public void exitGroup(final long groupId, final IMExitGroupListener listener) {
        setListener(listener);
        JMessageClient.exitGroup(groupId, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (listener != null) {
                    if (responseCode == 0) {
                        //删除群组聊天
                        JMessageClient.deleteGroupConversation(groupId);
                        listener.onSuccess();
                    } else {
                        listener.onError(responseMessage);
                    }
                }
            }
        });
    }

}
