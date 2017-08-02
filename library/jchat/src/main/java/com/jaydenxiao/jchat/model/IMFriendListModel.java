package com.jaydenxiao.jchat.model;

import android.text.TextUtils;

import com.jaydenxiao.jchat.listener.IMGetFriendListListener;
import com.jaydenxiao.jchat.listener.IMIsFriendListener;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 类名：IMFriendListModel
 * 描述：好友列表Model管理
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016/12/26
 * 最后修改时间：2016/12/26
 */
public class IMFriendListModel {

    private static volatile IMFriendListModel instance = null;
    /**
     * 所有好友集合集合
     */
    private List<UserInfo> mAllFriendList;

    private IMFriendListModel() {
        initData();
    }

    public static IMFriendListModel getInstance() {
        if (instance == null) {
            synchronized (IMFriendListModel.class) {
                if (instance == null) {
                    instance = new IMFriendListModel();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化方法
     */
    private void initData() {
        //获取好友列表
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                mAllFriendList = list;
            }
        });
    }

    /**
     * 设置好友列表
     *
     * @return
     */
    public synchronized void setFriendList(List<UserInfo> list) {
        this.mAllFriendList = list;
    }

    /**
     * 获取好友列表
     *
     * @return
     */
    public synchronized void getFriendList(final IMGetFriendListListener callback) {
        if (mAllFriendList == null) {
            ContactManager.getFriendList(new GetUserInfoListCallback() {
                @Override
                public void gotResult(int i, String s, List<UserInfo> list) {
                    if (i == 0) {
                        mAllFriendList = list;
                        callback.onSuccess(list);
                    } else {
                        callback.onError(s);
                    }
                }
            });
        } else {
            callback.onSuccess(mAllFriendList);
        }
    }

    /**
     * 添加好友
     */
    public synchronized void addFriend(UserInfo UserInfo) {
        if (mAllFriendList == null) {
            initData();
        }
        if (mAllFriendList != null) {
            mAllFriendList.add(UserInfo);
        }
    }

    /**
     * 添加好友
     */
    public synchronized void addFriendAt(UserInfo UserInfo, int index) {
        if (mAllFriendList == null) {
            initData();
        }
        if (mAllFriendList != null) {
            mAllFriendList.add(index, UserInfo);
        }
    }

    /**
     * 移除好友
     */
    public synchronized void removeFriend(UserInfo userInfo) {
        if (mAllFriendList == null) {
            initData();
        }
        if (mAllFriendList != null) {
            mAllFriendList.remove(userInfo);
        }
    }

    /**
     * 移除好友
     */
    public synchronized void removeFriendAt(int index) {
        if (mAllFriendList == null) {
            initData();
        }
        if (mAllFriendList != null) {
            mAllFriendList.remove(index);
        }
    }

    /**
     * 更新好友
     */
    public synchronized void updateFriend(UserInfo userInfo) {
        if (mAllFriendList == null) {
            initData();
        }
        if (mAllFriendList != null && userInfo != null) {
            for (int i = 0; i < mAllFriendList.size(); i++) {
                if (userInfo.getUserID() == mAllFriendList.get(i).getUserID()) {
                    mAllFriendList.set(i, userInfo);
                    break;
                }
            }
        }
    }

    /**
     * 清空好友
     */
    public synchronized void clear() {
        if (mAllFriendList == null) {
            initData();
        }
        if (mAllFriendList != null) {
            mAllFriendList.clear();
        }
    }

    /**
     * 通过用户账户名称，检查是否为好友关系
     *
     * @param userAccount 用户账户名称
     * @return true 为好友，false 非好友
     */
    public synchronized void isFriend(final String userAccount, final IMIsFriendListener listener) {
        if (mAllFriendList == null) {
            ContactManager.getFriendList(new GetUserInfoListCallback() {
                @Override
                public void gotResult(int i, String s, List<UserInfo> list) {
                    if (i == 0) {
                        mAllFriendList = list;
                        callBack(userAccount, listener);
                    } else {
                        listener.onError(s);
                    }
                }
            });
        } else {
            callBack(userAccount, listener);
        }
    }

    private void callBack(String userAccount, final IMIsFriendListener listener) {
        if (isFriend(userAccount) != null) {
            listener.onSuccess(isFriend(userAccount));
        } else {
            listener.onFailed();
        }
    }

    private UserInfo isFriend(String userAccount) {
        if (mAllFriendList != null) {
            for (UserInfo userInfo : mAllFriendList) {
                if (userInfo.getUserName().equals(userAccount)) {
                    return userInfo;
                }
            }
        }
        return null;
    }

    /**
     * 刷新同步好友列表数据
     */
    public void refreshFriendList() {
        mAllFriendList=null;
        initData();
    }

    /**
     * 搜索过滤好友
     */
    public void filterData(final String filterStr, final IMGetFriendListListener callback) {
        if (mAllFriendList == null) {
            ContactManager.getFriendList(new GetUserInfoListCallback() {
                @Override
                public void gotResult(int i, String s, List<UserInfo> list) {
                    if (i == 0) {
                        mAllFriendList = list;
                        callback.onSuccess(filter(filterStr));
                    } else {
                        callback.onError(s);
                    }
                }
            });
        } else {
            callback.onSuccess(filter(filterStr));
        }
    }

    /**
     * 执行过滤方法
     *
     * @param filterStr
     */
    private List<UserInfo> filter(String filterStr) {
        List<UserInfo> filterDateList = new ArrayList<UserInfo>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mAllFriendList;
        } else {
            filterDateList.clear();
            for (UserInfo entry : mAllFriendList) {
                String name = entry.getNickname();
                String userId = String.valueOf(entry.getUserID());
                if (name.contains(filterStr) || userId.contains(filterStr)) {
                    filterDateList.add(entry);
                }
            }
        }
        return filterDateList;
    }
}

