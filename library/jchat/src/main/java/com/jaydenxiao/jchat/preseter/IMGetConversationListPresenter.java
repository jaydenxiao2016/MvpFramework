package com.jaydenxiao.jchat.preseter;

import com.jaydenxiao.common.basemvp.BasePresenter;
import com.jaydenxiao.jchat.listener.IMGetConversationListListener;
import com.jaydenxiao.jchat.model.IMGetConversationListModel;
import com.jaydenxiao.jchat.view.IIMGetConversationListView;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;

/**
 * 描述：获取会话列表
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/14
 * 最后修改时间:2016/12/14
 */
public class IMGetConversationListPresenter extends BasePresenter<IIMGetConversationListView> {
   private IMGetConversationListModel mModel;
    @Override
    public void onStart() {
        mModel= new IMGetConversationListModel();
        mModel.setListener(new IMGetConversationListListener() {
            @Override
            public void onSuccess(List<Conversation> list) {
                mView.onSuccess(list);
            }

            @Override
            public void onError(String message) {
                mView.onFailure(message);
            }
        });
    }

    public void getAllConversations() {
        mModel.getConversationList();
    }
}
