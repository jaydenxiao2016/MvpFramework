package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.jaydenxiao.common.baseapp.BaseApplication;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 描述：设别信息Util类
 * 公司：北京海鑫科金高科技股份有限公司
 * 作者：程林
 * 创建时间:2016/12/8
 * 最后修改时间:2016/12/8
 */
public class DeviceInfoUtil {
    /**
     * 获取mac地址
     * @return
     */
    public static String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            return "";
        }
        if (TextUtils.isEmpty(macSerial)) {
            macSerial=getMachineHardwareAddress();
        }
        if (TextUtils.isEmpty(macSerial)) {
            macSerial=getMacOrIMEI();
        }
        //去掉“：”
        return macSerial.replace(":", "");
    }

    /**
     * android6.0以上获取mac地址
     * @return
     */
    private static String getMachineHardwareAddress(){
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (Exception e) {
            return "";
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if(hardWareAddress == null) continue;
            } catch (Exception e) {
                break;
            }
        }
        if(iF != null && iF.getName().equals("wlan0")){
            hardWareAddress = hardWareAddress.replace(":","");
        }
        return hardWareAddress ;
    }

    private static String getMacOrIMEI(){
        WifiManager wifi = (WifiManager) (BaseApplication.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE));
        WifiInfo info = wifi.getConnectionInfo();
        //获取wifi设备的mac码，由于服务器不支持带符号的mac所有去掉
        String mac = info.getMacAddress().replace(":", "");
        if(TextUtils.isEmpty(mac)){
            mac = ((TelephonyManager) BaseApplication.getAppContext().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        }
        return mac;
    }

    /***
     * byte转为String
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes){
        if (bytes == null || bytes.length == 0) {
            return null ;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }
}
