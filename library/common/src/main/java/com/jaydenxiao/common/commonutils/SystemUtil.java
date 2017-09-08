package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.jaydenxiao.common.baseapp.BaseApplication;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by yuchao on 2017/4/27.
 */

public class SystemUtil {

    /**
     * 获取当前版本号
     */
    public static String getCurrentAppVersion() {
        return getCurrentAppVersion(BaseApplication.getAppContext().getApplicationContext());
    }

    /**
     * 获取当前版本号
     */
    public static String getCurrentAppVersion(Context context) {
        String versionName = "";
        try {
            PackageManager pManager = context.getPackageManager();
            PackageInfo pInfo = pManager.getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 获取安装包版本号
     */
    public static String getApkVersion(Context context, String path) {
        String versionName = "";
        try {
            PackageManager pManager = context.getPackageManager();
            PackageInfo info = pManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                versionName = info.versionName;
            }
        } catch (Exception e) {
            versionName = "";
        }
        return versionName;
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获得运营商的名字
     *
     * @param context
     * @return
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }

    /**
     * 返回网络类型
     *
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo) {
            return networkInfo.getTypeName();
        }
        return null;
    }

    /**
     * 获得IP地址
     *
     * @param context
     * @return
     */
    public static String getIP(Context context) {
        String ipAddress = null;
        if ("WIFI".equals(getNetworkType(context))) { // 获得wifi下的ip地址
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            ipAddress = intToIp(wifiInfo.getIpAddress());
        } else { // 获得非wifi下的ip地址
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface
                        .getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (SocketException ex) {
                return null;
            }
        }
        return ipAddress;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     * @创建人 杨培尧
     * @创建时间 2016年7月20日
     * @方法说明 在部分机型上，或者运营商的原因，无法获取到手机号
     * @备注1 【注释人:杨培尧;时间:2016年7月20日;原因:】
     * @备注2 【修改人:杨培尧;时间:2016年7月20日;原因:】
     * @异常
     */
    public static String getPhoneNumber(Context context) {
        String tel = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //String simSerial = tm.getSimSerialNumber();//获得SIM卡的序号
        tel = tm.getLine1Number();//获取本机号码
        return tel;
    }

    /**
     * 获取应用包名
     *
     * @param context
     * @return
     * @创建人 杨培尧
     * @创建时间 2017年1月11日
     * @方法说明 获取应用包名
     * @备注1 【注释人:杨培尧;时间:2016年7月20日;原因:】
     * @备注2 【修改人:杨培尧;时间:2016年7月20日;原因:】
     * @异常
     */
    public static String getApkName(Context context) {
        return context.getApplicationInfo().packageName;
    }

    /**
     * 获取IMEI
     */
    public static String getIMEI(Context context) {
        String imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

        if (imei == null || imei.equals("")) {
            //获取wifi设备的mac码
            imei = getMac(context);
        }

        return imei;
    }

    /**
     * 获取IMEI
     */
    public static String getIMEI() {
        return getIMEI(BaseApplication.getAppContext().getApplicationContext());
    }

    /**
     * 获取mac
     *
     * @param context
     * @return
     */
    private static String getMac(Context context) {
        WifiManager wifi = (WifiManager) (context.getSystemService(Context.WIFI_SERVICE));
        WifiInfo info = wifi.getConnectionInfo();
        //获取wifi设备的mac码，由于服务器不支持带符号的mac所有去掉
        String mac = info.getMacAddress().replace(":", "");
        return mac;
    }

    /**
     * 获取设备SERIAL号
     *
     * @return SERIAL号
     */
    public static String getSERIALFromDevice() {
        String serial = Build.SERIAL;
        return serial;
    }

    /**
     * 获取屏幕密度
     *
     * @return
     */
    public static float getScreenDensity() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.density;
    }

    /**
     * 获取屏幕宽度和高度
     *
     * @return int[0] is width;int[1] is height;
     */
    public static int[] getScreenWidthAndHeight() {
        int[] wh = new int[2];
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        int screenWidth = (int) (widthPixels);
        int screenHeight = (int) (heightPixels);
        wh[0] = screenWidth;
        wh[1] = screenHeight;
        return wh;
    }

    /**
     * 扩充照片预览窗口至半全屏（高度或宽度其中之一为屏幕高度宽度）
     *
     * @param widthAndHeight
     * @param previewDimension
     * @return
     */
    public static Point getCameraPreviewLayoutDimension(int[] widthAndHeight, Camera.Size previewDimension) {
        Point displayDimension = new Point(widthAndHeight[0], widthAndHeight[1]);
        if (previewDimension.height < displayDimension.x) {
            Point dimensions = new Point();
            dimensions.x = displayDimension.x;
            dimensions.y = (previewDimension.width * displayDimension.x) / previewDimension.height;
            return dimensions;
        } else {
            Point dimensions = new Point();
            dimensions.x = previewDimension.height;
            dimensions.y = previewDimension.width;
            return dimensions;
        }
    }

    /**
     * 获取设备类型
     */
    public static String getDeviceType(Context context) {
        if (SystemUtil.isTablet(context)) {
            return "PB";
        }
        return "MO";
    }
}
