package com.jaydenxiao.jchat.view;

import com.jaydenxiao.common.basemvp.BaseView;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;

/**
 * 描述：会话列表界面View 接口
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/14
 * 最后修改时间:2016/12/14
 */
public interface IIMGetConversationListView extends BaseView {
    void onSuccess(List<Conversation> list);

    void onFailure(String message);
}
