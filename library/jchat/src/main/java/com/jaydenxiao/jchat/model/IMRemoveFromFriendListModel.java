package com.jaydenxiao.jchat.model;

import com.jaydenxiao.jchat.listener.IMRemoveFromFriendListListener;

import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 类名：IMRemoveFromFriendListModel
 * 描述：删除好友Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */
public class IMRemoveFromFriendListModel extends IMBaseModel<IMRemoveFromFriendListListener> {
    public void removeFromFriendList(UserInfo userInfo, final IMRemoveFromFriendListListener listener) {
        setListener(listener);
        if (userInfo == null) {
            if (listener != null) {
                listener.onError("UserInfo为null");
            }
        } else {
            userInfo.removeFromFriendList(new BasicCallback() {
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
}
