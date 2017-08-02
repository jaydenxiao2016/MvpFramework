package com.jaydenxiao.jchat.listener;

import cn.jpush.im.android.api.model.GroupInfo;

/**
 * 描述：获取群组信息回调
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public interface IMGroupInfoListener extends IMBaseListener {
    void onSuccess(GroupInfo group);
}
