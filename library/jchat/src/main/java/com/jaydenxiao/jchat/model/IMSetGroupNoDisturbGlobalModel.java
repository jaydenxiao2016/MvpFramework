package com.jaydenxiao.jchat.model;

import com.jaydenxiao.jchat.listener.IMSetNoDisturbGlobalListener;

import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 类名：IMSetNoDisturbGlobalModel
 * 描述：设置免打扰,1为将群聊设为免打扰,0为移除免打扰
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */
public class IMSetGroupNoDisturbGlobalModel extends IMBaseModel<IMSetNoDisturbGlobalListener> {
    /**
     * 全局免打扰设置
     *
     * @param enable   是否启用
     * @param listener 回调
     */
    public void setNoDisturbGlobal(GroupInfo groupInfo, boolean enable, final IMSetNoDisturbGlobalListener listener) {
        setListener(listener);
        groupInfo.setNoDisturb(enable ? 1 : 0, new BasicCallback() {
            @Override
            public void gotResult(int status, String desc) {
                if (status == 0) {
                    mListener.onSuccess();
                    //设置失败,恢复为原来的状态
                } else {
                    mListener.onError(desc);
                }
            }
        });
    }
}
