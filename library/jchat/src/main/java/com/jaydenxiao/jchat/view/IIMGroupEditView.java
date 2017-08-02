package com.jaydenxiao.jchat.view;

import com.jaydenxiao.common.basemvp.BaseView;

import java.security.acl.Group;
import java.util.List;

/**
 * 描述：编辑群View 接口
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/14
 * 最后修改时间:2016/12/14
 */
public interface IIMGroupEditView extends BaseView {

    void onCreateGroupSuccess(long groupId);
    void onAddMembersSuccess(List<Group> list);
    void onFailure(String message);
}
