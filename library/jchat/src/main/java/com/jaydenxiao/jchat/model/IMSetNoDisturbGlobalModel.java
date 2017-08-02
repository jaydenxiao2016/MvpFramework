package com.jaydenxiao.jchat.model;

import com.jaydenxiao.jchat.listener.IMSetNoDisturbGlobalListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 类名：IMSetNoDisturbGlobalModel
 * 描述：全局免打扰设置Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */
public class IMSetNoDisturbGlobalModel extends IMBaseModel<IMSetNoDisturbGlobalListener> {
    /**
     * 全局免打扰设置
     *
     * @param enable   是否启用
     * @param listener 回调
     */
    public void setNoDisturbGlobal(boolean enable, final IMSetNoDisturbGlobalListener listener) {
        setListener(listener);
        JMessageClient.setNoDisturbGlobal(enable ? 1 : 0, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (listener != null) {
                    if (i == 0) {
                        listener.onSuccess();
                    } else {
                        listener.onError(s);
                    }
                }
            }
        });
    }
}
