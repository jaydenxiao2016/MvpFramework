package com.jaydenxiao.jchat.view;

import com.jaydenxiao.common.basemvp.BaseView;

import java.util.List;

import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 描述：添加好友请求界面View 接口
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2016/12/14
 * 最后修改时间:2016/12/14
 */
public interface IIMGroupCardView extends BaseView {

    void onGetGroupInfoSuccess(GroupInfo groupInfo);
    void onGetGroupMembersSuccess(List<UserInfo> list);
    void onUpdateGroupNameSuccess(String newName);
    void onUpdateGroupAvatarSuccess(String avatar);
    void onExitGroupSuccess();
    void onRemoveGroupMemberSuccess();
    void onUpdateNickNameSuccess(String nickNmae);
    void onFailure(String message);
}
