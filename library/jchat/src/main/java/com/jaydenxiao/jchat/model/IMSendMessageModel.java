package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMSendMessaListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.ProgressUpdateCallback;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

/**
 * 描述：IM发送消息Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：杨培尧
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */

public class IMSendMessageModel extends IMBaseModel<IMSendMessaListener>{

    /**
     * IM发送信息
     * @param message
     * @param listener
     */
    public void sendMessage(Message message, IMSendMessaListener listener){
        setListener(listener);
        if(null==message){
            mListener.onError("信息数据异常！");
            return;
        }
        if(ContentType.file==message.getContentType()|| ContentType.image==message.getContentType()||
                ContentType.video==message.getContentType()|| ContentType.voice==message.getContentType()){
            message.setOnContentUploadProgressCallback(new ProgressUpdateCallback() {
                @Override
                public void onProgressUpdate(double v) {
                    mListener.onProgress(v);
                }
            });
        }
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    mListener.onSuccess(s);
                }else{
                    mListener.onError(s);
                }
            }
        });
        JMessageClient.sendMessage(message);
    }


}
