package com.jaydenxiao.jchat.preseter;


import com.jaydenxiao.common.basemvp.BasePresenter;
import com.jaydenxiao.jchat.listener.IMSendInvitionListener;
import com.jaydenxiao.jchat.model.IMSendInvitionModel;

import cn.jpush.im.android.api.JMessageClient;

/**
 * 描述：发送好友添加请求Presenter
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：杨培尧
 * 创建时间:2016/12/14
 * 最后修改时间:2016/12/14
 */
public class IMSendInvitionPresenter extends BasePresenter<IMSendInvitionListener> {

    private IMSendInvitionModel mModel;

    @Override
    public void onStart() {
        mModel = new IMSendInvitionModel();
    }

    public void sendVerify(String targetUsername, String content) {
        mModel.sendVerify(targetUsername, JMessageClient.getMyInfo().getAppKey(), content);
        mModel.setListener(new IMSendInvitionListener() {
            @Override
            public void onSuccess() {
                mView.onSuccess();
            }

            @Override
            public void onError(String message) {
                mView.onError(message);
            }
        });
    }
}
