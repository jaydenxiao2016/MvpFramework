package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGetGroupIDListListener;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;

/**
 * 描述：获取群组列表Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public class IMGetGroupIDListModel extends IMBaseModel<IMGetGroupIDListListener> {

    /**
     * 获取群组列表信息
     *
     * @param listener 结果回调
     */
    public void getGroupIDList(final IMGetGroupIDListListener listener) {
        setListener(listener);
        JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, List<Long> groupIDList) {
                if (listener != null) {
                    if (responseCode == 0) {
                        listener.onSuccess(groupIDList);
                    } else {
                        listener.onError(responseMessage);
                    }
                }
            }
        });
    }

}
