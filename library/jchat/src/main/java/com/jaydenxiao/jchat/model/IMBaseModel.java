package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMBaseListener;

/**
 * 描述：IM基类Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/24
 * 最后修改时间:2016/12/24
 */

public abstract class IMBaseModel <L extends IMBaseListener>{

    public L mListener;

    public void setListener(L listener) {
        this.mListener = listener;
    }



}
