package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGetNoDisturblistListener;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetNoDisurbListCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 类名：IMGetNoDisturblistModel
 * 描述：获取免打扰列表Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */
public class IMGetNoDisturblistModel extends IMBaseModel<IMGetNoDisturblistListener> {
    public void getNoDisturblist(final IMGetNoDisturblistListener listener) {
        setListener(listener);
        JMessageClient.getNoDisturblist(new GetNoDisurbListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> userInfos, List<GroupInfo> groupInfos) {
                if (listener != null) {
                    if (i == 0) {
                        listener.onSuccess(userInfos, groupInfos);
                    } else {
                        listener.onError(s);
                    }
                }
            }
        });
    }
}
