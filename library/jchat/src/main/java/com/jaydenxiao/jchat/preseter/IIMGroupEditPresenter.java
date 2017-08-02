package com.jaydenxiao.jchat.preseter;

import com.jaydenxiao.common.basemvp.BasePresenter;
import com.jaydenxiao.jchat.listener.IMAddGroupMembersListener;
import com.jaydenxiao.jchat.listener.IMCreateGroupListener;
import com.jaydenxiao.jchat.model.IMAddGroupMembersModel;
import com.jaydenxiao.jchat.model.IMCreateGroupModel;
import com.jaydenxiao.jchat.view.IIMGroupEditView;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名：IIMGroupCardPresenter.java
 * 描述：群名片
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2017/1/17
 * 最后修改时间：2017/1/17
 */

public class IIMGroupEditPresenter extends BasePresenter<IIMGroupEditView> {
    private IMCreateGroupModel imGetGroupMembersModel;
    private IMAddGroupMembersModel imAddGroupMembersModel;
    private List<Group> groupCreatorEntityListLocal;

    @Override
    public void onStart() {
        imGetGroupMembersModel = new IMCreateGroupModel();
        imAddGroupMembersModel = new IMAddGroupMembersModel();
    }

    /**
     * 创建群
     */
    public void createGroup(String groupName, String groupDesc) {
        imGetGroupMembersModel.createGroup(groupName, groupDesc, new IMCreateGroupListener() {
            @Override
            public void onSuccess(long groupId) {
                mView.onCreateGroupSuccess(groupId);
            }

            @Override
            public void onError(String message) {
                mView.onFailure("创建群失败");
            }
        });
    }

    /**
     * 添加群成员
     *
     * @param groupID
     * @param groupCreatorEntityList
     */
    public void addGroupMembers(long groupID, final List<Group> groupCreatorEntityList) {
        List<String> userStrings = new ArrayList<>();
        if (groupCreatorEntityList != null && groupCreatorEntityList.size() > 0) {
            for (int i = 0; i < groupCreatorEntityList.size(); i++) {
                if (!checkIfNotContainUser(groupCreatorEntityList.get(i).getName())) {
                    userStrings.add(groupCreatorEntityList.get(i).getName());
                }
            }
        }
        imAddGroupMembersModel.addGroupMembers(groupID, userStrings, new IMAddGroupMembersListener() {
            @Override
            public void onSuccess() {
                groupCreatorEntityListLocal = groupCreatorEntityList;
                mView.onAddMembersSuccess(groupCreatorEntityList);
            }

            @Override
            public void onError(String message) {
                mView.onFailure("添加群成员失败");
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
        if (groupCreatorEntityListLocal != null) {
            for (Group groupCreatorEntity : groupCreatorEntityListLocal) {
                if (groupCreatorEntity.getName().equals(targetID)) {
                    result = true;
                    break;
                }
            }
            return result;
        }
        return result;
    }

}
