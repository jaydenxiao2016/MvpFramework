package com.jaydenxiao.jchat.listener;


/**
 * 类名：IMSendMessaListener
 * 描述：IM发送信息回调
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-6
 * 最后修改时间：2016-12-6
 */
public interface IMSendMessaListener extends IMBaseListener {
    void onSuccess(String message);
    void onProgress(double v);
}
