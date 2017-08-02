package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGetNoDisturbGlobalListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.IntegerCallback;

/**
 * 类名：IMGetNoDisturbGlobalModel
 * 描述：获取全局免打扰标识Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */
public class IMGetNoDisturbGlobalModel extends IMBaseModel<IMGetNoDisturbGlobalListener> {
    public void getNoDisturbGlobal(final IMGetNoDisturbGlobalListener listener) {
        setListener(listener);
        JMessageClient.getNoDisturbGlobal(new IntegerCallback() {
            @Override
            public void gotResult(int i, String s, Integer integer) {
                if (listener != null) {
                    if (i == 0) {
                        listener.onSuccess(integer == 1);
                    } else {
                        listener.onError(s);
                    }
                }
            }
        });
    }
}
