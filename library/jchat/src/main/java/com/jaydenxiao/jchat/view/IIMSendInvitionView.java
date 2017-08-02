package com.jaydenxiao.jchat.view;


import com.jaydenxiao.common.basemvp.BaseView;

/**
 * 描述：添加好友请求界面View 接口
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/14
 * 最后修改时间:2016/12/14
 */
public interface IIMSendInvitionView extends BaseView {
    void onSuccess(String message);

    void onFailure(String message);
}
