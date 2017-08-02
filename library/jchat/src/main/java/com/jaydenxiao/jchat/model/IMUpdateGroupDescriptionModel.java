package com.jaydenxiao.jchat.model;

import com.jaydenxiao.jchat.listener.IMUpdateGroupDescriptionListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 描述：更新群组详情Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public class IMUpdateGroupDescriptionModel extends IMBaseModel<IMUpdateGroupDescriptionListener> {

    /**
     * 更新群组详情
     *
     * @param groupID   群组id
     * @param groupDesc 群组描述
     * @param listener  结果回调
     */
    public void updateGroupDescription(long groupID, String groupDesc, final IMUpdateGroupDescriptionListener listener) {
        setListener(listener);
        JMessageClient.updateGroupDescription(groupID, groupDesc, new BasicCallback() {
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
