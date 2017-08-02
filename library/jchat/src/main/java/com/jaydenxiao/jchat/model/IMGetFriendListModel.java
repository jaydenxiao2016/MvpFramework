package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGetFriendListListener;

import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 类名：IMGetFriendListModel
 * 描述：获取好友列表Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */
public class IMGetFriendListModel extends IMBaseModel<IMGetFriendListListener> {
    public void getFriendList(final IMGetFriendListListener listener) {
        setListener(listener);
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                if (listener != null) {
                    if (i == 0) {
                        //保存到内存缓存
                        IMFriendListModel.getInstance().setFriendList(list);
                        listener.onSuccess(list);
                    } else {
                        listener.onError(s);
                    }
                }
            }
        });
    }
}
