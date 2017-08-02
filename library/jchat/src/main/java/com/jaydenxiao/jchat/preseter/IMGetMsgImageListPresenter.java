package com.jaydenxiao.jchat.preseter;


import com.jaydenxiao.common.basemvp.BasePresenter;
import com.jaydenxiao.jchat.listener.IMGetMessageByTypeListener;
import com.jaydenxiao.jchat.model.IMGetMessageByTypeModel;
import com.jaydenxiao.jchat.view.IIMGetMsgImageListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * 描述：获取会话图片列表
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/14
 * 最后修改时间:2016/12/14
 */
public class IMGetMsgImageListPresenter extends BasePresenter<IIMGetMsgImageListView> {
    private IMGetMessageByTypeModel mModel;

    @Override
    public void onStart() {
        mModel = new IMGetMessageByTypeModel();
        mModel.setListener(new IMGetMessageByTypeListener() {
            @Override
            public void onSuccess(List<Message> list) {
                mView.onSuccess(list);
            }

            @Override
            public void onSuccess(HashMap<String, ArrayList<Message>> map) {
                mView.onSuccess(map);
            }

            @Override
            public void onError(String message) {
                mView.onFailure(message);
            }
        });
    }

    /**
     * 获取图片消息列表
     *
     * @param conversation
     */
    public void getImageList(Conversation conversation) {
        if (null != conversation) {
            mModel.getImageList(conversation);
        }
    }

    /**
     * 获取文件消息列表
     *
     * @param conversation
     */
    public void getFileList(Conversation conversation) {
        if (null != conversation) {
            mModel.getFileList(conversation);

        }
    }

}
