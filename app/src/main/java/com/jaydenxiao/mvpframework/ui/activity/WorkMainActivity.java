package com.jaydenxiao.mvpframework.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.FormatUtil;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonwidget.RoundedImageView;
import com.jaydenxiao.mvpframework.R;
import com.jaydenxiao.mvpframework.app.AppCache;
import com.jaydenxiao.mvpframework.bean.User;
import com.jaydenxiao.mvpframework.bean.WorkMenuEntity;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * 类名：WorkMainActivity.java
 * 描述：工作台
 * 作者：xsf
 * 创建时间：2017/2/16
 * 最后修改时间：2017/2/16
 */

public class WorkMainActivity extends BaseActivity {
    @Bind(R.id.model_TextView)
    TextView model_TextView;
    @Bind(R.id.img_avatar)
    RoundedImageView imgAvatar;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.irec)
    IRecyclerView irec;
    private CommonRecycleViewAdapter<WorkMenuEntity> adapter;
    private User user;


    @Override
    public int getLayoutId() {
        return R.layout.act_work_main;
    }

    @Override
    public void attachPresenterView() {
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        SetTranslanteBar();
        user = AppCache.getInstance().getUser();
        if (user == null)
            return;
        //设置头像、姓名编号
        ImageLoaderUtils.displayAvatar(this, imgAvatar, user.getIcon());
        tvName.setText(FormatUtil.checkValue(user.getUserName()));
        tvCode.setText(FormatUtil.checkValue(user.getUserId()));
        //初始化适配器
        adapter = new CommonRecycleViewAdapter<WorkMenuEntity>(this, R.layout.item_work_memu, getWorkMenuList()) {
            @Override
            public void convert(final ViewHolderHelper helper, WorkMenuEntity workMenuEntity) {
                helper.setBackgroundRes(R.id.img_memu, workMenuEntity.getIcon());
                helper.setText(R.id.tv_memu_title, workMenuEntity.getTitle());
                if (workMenuEntity.getNotReadCount() > 0) {
                    helper.setVisible(R.id.tv_not_read, true);
                    helper.setText(R.id.tv_not_read, String.valueOf(workMenuEntity.getNotReadCount()));
                } else {
                    helper.setVisible(R.id.tv_not_read, false);
                }
                //点击事件
                helper.setOnClickListener(R.id.ll_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (helper.getmPosition()) {
                            default:
                                startActivity(SettingActivity.class);
                                break;
                        }
                    }
                });

            }
        };
        //设置网格
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        irec.setAnimation(new AlphaAnimation(0, 1));
        irec.setLayoutManager(gridLayoutManager);
        irec.setAdapter(adapter);
    }

    /**
     * 获取菜单数据
     *
     * @return
     */
    private ArrayList<WorkMenuEntity> getWorkMenuList() {
        //塞假数据
        ArrayList<WorkMenuEntity> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    list.add(new WorkMenuEntity("签到" + i, R.drawable.work_menu_notread, 0, SettingActivity.class));
                    break;
                case 1:
                    list.add(new WorkMenuEntity("签到" + i, R.drawable.work_menu_record_ammeter, 0, SettingActivity.class));
                    break;
                case 2:
                    list.add(new WorkMenuEntity("签到" + i, R.drawable.work_menu_dianwang, 0, SettingActivity.class));
                    break;
                case 3:
                    list.add(new WorkMenuEntity("签到" + i, R.drawable.work_memu_check, 0, SettingActivity.class));
                    break;
                case 4:
                    list.add(new WorkMenuEntity("签到" + i, R.drawable.work_menu_download, 0, SettingActivity.class));
                    break;
                case 5:
                    list.add(new WorkMenuEntity("签到" + i, R.drawable.work_menu_upload, 0, SettingActivity.class));
                    break;
                case 6:
                    list.add(new WorkMenuEntity("签到" + i, R.drawable.work_menu_reabck, 0, SettingActivity.class));
                    break;
                case 7:
                    list.add(new WorkMenuEntity("签到" + i, R.drawable.work_munu_settings, 0, SettingActivity.class));
                    break;
            }

        }
        return list;
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showShortToast(getString(R.string.exit_tip));
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
