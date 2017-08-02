package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGroupInfoListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;

/**
 * 描述：获取群组详情Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public class IMGetGroupInfoModel extends IMBaseModel<IMGroupInfoListener> {

    /**
     * 获取群组详情
     *
     * @param groupId  群ID
     * @param listener 结果回调
     */
    public void getGroupInfo(long groupId, final IMGroupInfoListener listener) {
        setListener(listener);
        JMessageClient.getGroupInfo(groupId, new GetGroupInfoCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, GroupInfo group) {
                if (listener != null) {
                    if (responseCode == 0) {
                        listener.onSuccess(group);
                    } else {
                        listener.onError(responseMessage);
                    }
                }
            }
        });
    }

}
