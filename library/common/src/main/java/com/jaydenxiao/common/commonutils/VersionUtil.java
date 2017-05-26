package com.jaydenxiao.common.commonutils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jaydenxiao.common.R;
import com.jaydenxiao.common.baseapp.BaseApplication;

/**
 * 类名：VersionUtil.java
 * 描述：获取版本号
 * 作者：xsf
 * 创建时间：2017/3/2
 * 最后修改时间：2017/3/2
 */

public class VersionUtil {

    /**
     * 获取版本号
     *
     * @return 当前应用的版本名字
     */
    public static String getVersion() {
        try {
            PackageManager manager = BaseApplication.getAppContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getAppContext().getPackageName(), 0);
            String version = info.versionName;
            return BaseApplication.getAppContext().getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return BaseApplication.getAppContext().getString(R.string.can_not_find_version_name);
        }
    }

    /**
     * 获取版本号
     * @return
     */
    public static int getVersionCode() {
        try {
            PackageManager manager = BaseApplication.getAppContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getAppContext().getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
