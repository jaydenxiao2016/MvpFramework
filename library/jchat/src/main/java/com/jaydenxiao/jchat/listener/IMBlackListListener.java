package com.jaydenxiao.jchat.listener;

import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

/**
 * 类名：IMBlackListListener.java
 * 描述：获取黑名单列表
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */

public interface IMBlackListListener extends IMBaseListener {
    void onSuccess(List<UserInfo> list);
}
