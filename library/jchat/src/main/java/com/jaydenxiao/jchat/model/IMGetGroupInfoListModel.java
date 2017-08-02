package com.jaydenxiao.jchat.model;


import com.jaydenxiao.jchat.listener.IMGroupInfoListListener;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;

/**
 * 描述：获取群组详情List Model
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：xsf
 * 版本：V1.0
 * 创建时间：2016-12-26
 * 最后修改时间：2016-12-26
 */
public class IMGetGroupInfoListModel extends IMBaseModel<IMGroupInfoListListener> {
    /**
     * 计算获取群信息次数
     */
    public int num = 0;

    private static volatile IMGetGroupInfoListModel instance = null;

    private List<GroupInfo> groupList;

    public static IMGetGroupInfoListModel getInstance() {
        if (instance == null) {
            synchronized (IMGetGroupInfoListModel.class) {
                if (instance == null) {
                    instance = new IMGetGroupInfoListModel();
                }
            }
        }
        return instance;
    }

    public List<GroupInfo> getGroupList() {
        return groupList;
    }

    /**
     * 获取群组详情
     *
     * @param listener 结果回调
     */
    public void getGroupInfoList(final IMGroupInfoListListener listener) {
        setListener(listener);
        num = 0;
        JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
            @Override
            public void gotResult(int i, String s, final List<Long> list) {
                if (i == 0) {
                    final List<GroupInfo> groupInfoList = new ArrayList<GroupInfo>();

                    for (int j = 0; j < list.size(); j++) {
                        JMessageClient.getGroupInfo(list.get(j), new GetGroupInfoCallback() {
                            @Override
                            public void gotResult(int i, String s, GroupInfo groupInfo) {
                                if (i == 0) {
                                    groupInfoList.add(groupInfo);
                                }
                                num++;
                                if (num == list.size()) {
                                    groupList = groupInfoList;
                                    listener.onSuccess(groupInfoList);
                                }
                            }
                        });
                    }
                } else {
                    listener.onError(s);
                }
            }
        });
    }
}
