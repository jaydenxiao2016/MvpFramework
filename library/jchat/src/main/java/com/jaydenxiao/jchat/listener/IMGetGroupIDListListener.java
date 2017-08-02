package com.jaydenxiao.jchat.listener;

import java.util.List;

/**
 * 描述：获取群组列表回调
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public interface IMGetGroupIDListListener extends IMBaseListener {
    void onSuccess(List<Long> groupIDList);
}
