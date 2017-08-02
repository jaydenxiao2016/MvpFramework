package com.jaydenxiao.jchat.preseter;

import com.jaydenxiao.common.basemvp.BasePresenter;
import com.jaydenxiao.jchat.listener.IMAddGroupMembersListener;
import com.jaydenxiao.jchat.listener.IMGetGroupMembersListener;
import com.jaydenxiao.jchat.listener.IMRemoveGroupMembersListener;
import com.jaydenxiao.jchat.model.IMAddGroupMembersModel;
import com.jaydenxiao.jchat.model.IMGetGroupMembersModel;
import com.jaydenxiao.jchat.model.IMRemoveGroupMembersModel;
import com.jaydenxiao.jchat.view.IMIMemberManageView;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

/**
 * 描述：
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 创建时间:2017/01/03
 * 最后修改时间:2017/01/03
 */
public class IMMemberManagePresenter extends BasePresenter<IMIMemberManageView> {
    private IMGetGroupMembersModel imGetGroupMembersModel;
    private IMAddGroupMembersModel imAddGroupMembersModel;
    private IMRemoveGroupMembersModel imRemoveGroupMembersModel;
    /**
     * 已有群成员
     */
    private ArrayList<Group> groupCreatorEntityList;

    @Override
    public void onStart() {
        imGetGroupMembersModel = new IMGetGroupMembersModel();
        imAddGroupMembersModel = new IMAddGroupMembersModel();
        imRemoveGroupMembersModel = new IMRemoveGroupMembersModel();
    }

    /**
     * 获取群成员列表
     */
    public void getFriendList(final long groupId) {
        imGetGroupMembersModel.getGroupMembers(groupId, new IMGetGroupMembersListener() {
            @Override
            public void onSuccess(List<UserInfo> list) {
                mView.showMembersResult(groupCreatorEntityList);
            }

            @Override
            public void onError(String message) {
                mView.showFailure(message);
            }
        });
    }

    /**
     * 添加群成员
     *
     * @param groupID
     * @param userNameList
     */
    public void addGroupMembers(long groupID, final List<Group> userNameList) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < userNameList.size(); i++) {
            if (!checkIfNotContainUser(userNameList.get(i).getName())) {
                strings.add(userNameList.get(i).getName());
            }
        }
        imAddGroupMembersModel.addGroupMembers(groupID, strings, new IMAddGroupMembersListener() {
            @Override
            public void onSuccess() {
                mView.showAddMembersSuccess();
            }

            @Override
            public void onError(String message) {
                mView.showFailure(message);
            }
        });
    }

    /**
     * 移除好友(只有群主才能操作)
     *
     * @param groupID
     */
    public void removeGroupMember(final long groupID, List<Group> UserInfos) {
        List<String> userStrings = new ArrayList<>();
        if (UserInfos != null && UserInfos.size() > 0) {
            for (int i = 0; i < UserInfos.size(); i++) {
                    userStrings.add(UserInfos.get(i).getName());
            }
        }
        imRemoveGroupMembersModel.removeGroupMembers(groupID, userStrings, new IMRemoveGroupMembersListener() {
            @Override
            public void onSuccess() {
                //重新获取群成员
                getFriendList(groupID);
            }

            @Override
            public void onError(String message) {
                mView.showFailure(message);
            }
        });
    }

    /**
     * 添加成员时检查是否存在该群成员
     *
     * @param targetID 要添加的用户
     * @return 返回是否存在该用户
     */
    private boolean checkIfNotContainUser(String targetID) {
        boolean result = false;
        return result;
    }


}
