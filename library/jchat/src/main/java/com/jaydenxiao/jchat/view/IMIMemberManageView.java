package com.jaydenxiao.jchat.view;

import com.jaydenxiao.common.basemvp.BaseView;

import java.security.acl.Group;
import java.util.ArrayList;

/**
 * 描述：
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2017/01/03
 * 最后修改时间:2017/01/03
 */
public interface IMIMemberManageView extends BaseView{
    void showFailure(String message);
    void showMembersResult(ArrayList<Group> membersEntityList);
    void showAddMembersSuccess();
}
