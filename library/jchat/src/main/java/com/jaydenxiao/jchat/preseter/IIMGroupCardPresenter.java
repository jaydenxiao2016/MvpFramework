package com.jaydenxiao.jchat.preseter;

import com.jaydenxiao.common.basemvp.BasePresenter;
import com.jaydenxiao.jchat.listener.IMExitGroupListener;
import com.jaydenxiao.jchat.listener.IMGetGroupMembersListener;
import com.jaydenxiao.jchat.listener.IMGroupInfoListener;
import com.jaydenxiao.jchat.listener.IMRemoveGroupMembersListener;
import com.jaydenxiao.jchat.listener.IMSetNoDisturbGlobalListener;
import com.jaydenxiao.jchat.listener.IMUpdateGroupDescriptionListener;
import com.jaydenxiao.jchat.listener.IMUpdateGroupNameListener;
import com.jaydenxiao.jchat.model.IMExitGroupModel;
import com.jaydenxiao.jchat.model.IMGetGroupInfoModel;
import com.jaydenxiao.jchat.model.IMGetGroupMembersModel;
import com.jaydenxiao.jchat.model.IMRemoveGroupMembersModel;
import com.jaydenxiao.jchat.model.IMSetGroupNoDisturbGlobalModel;
import com.jaydenxiao.jchat.model.IMUpdateGroupDescriptionModel;
import com.jaydenxiao.jchat.model.IMUpdateGroupNameModel;
import com.jaydenxiao.jchat.view.IIMGroupCardView;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 类名：IIMGroupCardPresenter.java
 * 描述：群名片
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2017/1/17
 * 最后修改时间：2017/1/17
 */

public class IIMGroupCardPresenter extends BasePresenter<IIMGroupCardView> {
    private IMGetGroupInfoModel imGetGroupInfoModel;
    private IMGetGroupMembersModel imGetGroupMembersModel;
    private IMUpdateGroupNameModel imUpdateGroupNameModel;
    private IMExitGroupModel imExitGroupModel;
    private IMSetGroupNoDisturbGlobalModel imSetGroupNoDisturbGlobalModel;
    private IMRemoveGroupMembersModel imRemoveGroupMembersModel;
    private IMUpdateGroupDescriptionModel imUpdateGroupDescriptionModel;
    /**
     * 群成员
     */
    private List<UserInfo> userInfoList;

    @Override
    public void onStart() {
        imGetGroupInfoModel = new IMGetGroupInfoModel();
        imGetGroupMembersModel = new IMGetGroupMembersModel();
        imUpdateGroupNameModel = new IMUpdateGroupNameModel();
        imExitGroupModel = new IMExitGroupModel();
        imSetGroupNoDisturbGlobalModel = new IMSetGroupNoDisturbGlobalModel();
        imRemoveGroupMembersModel = new IMRemoveGroupMembersModel();
        imUpdateGroupDescriptionModel = new IMUpdateGroupDescriptionModel();
    }

    /**
     * 获取群信息
     */
    public void getGroupInfo(long groupID) {
        imGetGroupInfoModel.getGroupInfo(groupID, new IMGroupInfoListener() {
            @Override
            public void onSuccess(GroupInfo group) {
                mView.onGetGroupInfoSuccess(group);
            }

            @Override
            public void onError(String message) {
                mView.onFailure("获取群信息失败");
            }
        });
    }

    /**
     * 获取群成员
     */
    public void getGroupMembers(long groupID) {
        imGetGroupMembersModel.getGroupMembers(groupID, new IMGetGroupMembersListener() {
            @Override
            public void onSuccess(List<UserInfo> list) {
                userInfoList = list;
                mView.onGetGroupMembersSuccess(list);
            }

            @Override
            public void onError(String message) {
                mView.onFailure("获取群成员失败");
            }
        });
    }

    /**
     * 修改群名字
     */
    public void updateGroupNmae(long groupID, final String groupName) {
        imUpdateGroupNameModel.updateGroupName(groupID, groupName, new IMUpdateGroupNameListener() {
            @Override
            public void onSuccess() {
                mView.onUpdateGroupNameSuccess(groupName);
            }

            @Override
            public void onError(String message) {
                mView.onFailure("修改群名字失败");
            }
        });
    }

    /**
     * 修改群头像名字
     */
    public void updateGroupAvatar(long groupID, final String groupAvatar) {
        imUpdateGroupDescriptionModel.updateGroupDescription(groupID, groupAvatar, new IMUpdateGroupDescriptionListener() {
            @Override
            public void onSuccess() {
                mView.onUpdateGroupAvatarSuccess(groupAvatar);
            }

            @Override
            public void onError(String message) {
                mView.onFailure(message);
            }
        });
    }

    /**
     * 消息免打扰
     *
     * @param groupInfo
     * @param state
     */
    public void setNodisturb(GroupInfo groupInfo, boolean state) {
        imSetGroupNoDisturbGlobalModel.setNoDisturbGlobal(groupInfo, state, new IMSetNoDisturbGlobalListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String message) {
                mView.onFailure(message);
            }
        });
    }

    /**
     * 退出群
     *
     * @param groupID
     */
    public void exitGroup(long groupID) {
        imExitGroupModel.exitGroup(groupID, new IMExitGroupListener() {
            @Override
            public void onSuccess() {
                mView.onExitGroupSuccess();
            }

            @Override
            public void onError(String message) {
                mView.onFailure("退出群组失败");
            }
        });
    }

    /**
     * 移除好友(只有群主才能操作)
     *
     * @param groupID
     */
    public void removeGroupMember(long groupID, List<UserInfo> UserInfos) {
        List<String> userStrings = new ArrayList<>();
        if (UserInfos != null && UserInfos.size() > 0) {
            for (int i = 0; i < UserInfos.size(); i++) {
                if (!checkIfNotContainUser(UserInfos.get(i).getUserName()) ) {
                    userStrings.add(UserInfos.get(i).getUserName());
                }
            }
        }
        imRemoveGroupMembersModel.removeGroupMembers(groupID, userStrings, new IMRemoveGroupMembersListener() {
            @Override
            public void onSuccess() {
                mView.onRemoveGroupMemberSuccess();
            }

            @Override
            public void onError(String message) {
                mView.onFailure(message);
            }
        });
    }

    /**
     * 解散群
     *
     * @param groupID
     */
    public void dissolveGroup(final long groupID, List<UserInfo> UserInfos) {
        List<String> userStrings = new ArrayList<>();
        if (UserInfos != null && UserInfos.size() > 0) {
            for (int i = 0; i < UserInfos.size(); i++) {
                if (!checkIfNotContainUser(UserInfos.get(i).getUserName())) {
                    userStrings.add(UserInfos.get(i).getUserName());
                }
            }
        }
        if (userStrings.size() > 0) {
            //移除所有群成员后自己再退出群
            imRemoveGroupMembersModel.removeGroupMembers(groupID, userStrings, new IMRemoveGroupMembersListener() {
                @Override
                public void onSuccess() {
                    exitGroup(groupID);
                }

                @Override
                public void onError(String message) {
                    mView.onFailure(message);
                }
            });
        } else {
            exitGroup(groupID);
        }
    }

    /**
     * 更新昵称
     *
     * @param nickName
     */
    public void updateNickName(final String nickName) {
        UserInfo myUserInfo = JMessageClient.getMyInfo();
        myUserInfo.setNickname(nickName);
        JMessageClient.updateMyInfo(UserInfo.Field.nickname, myUserInfo, new BasicCallback() {
            @Override
            public void gotResult(final int status, final String desc) {
                if (status == 0) {
                    mView.onUpdateNickNameSuccess(nickName);
                } else {
                    mView.onFailure(desc);
                }
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
        if (userInfoList != null) {
            for (UserInfo userInfo : userInfoList) {
                if (userInfo.getUserName().equals(targetID))
                    return false;
            }
            return true;
        }
        return true;
    }

}
