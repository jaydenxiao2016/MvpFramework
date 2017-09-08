package com.hisign.thirdparty.tools;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFileTools {

    /**
     * 是否日志输出
     */
    public static boolean isDebug=true;
    /**
     * sdk日志根目录
     */
    public static String SDK_ROOT_PATH=Environment.getExternalStorageDirectory().getPath()+"/HisignMpp/CSIPADMiniFree/HisignNetSDKLog/";
    /**
     * sdk日志待压缩文件目录
     */
    public final static String LogReport="LogReport/";
    /**
     * sdk日志本地文件目录
     */
    public final static String LogSave="LogSave/";
    /**
     * sdk日志压缩包文件目录
     */
    public final static String LogZip="LogZip/";

    public LogFileTools() {
    }

    /**
     *@方法说明  保存协议数据
     *@param tag
     *@param msg
     *@param flag 区别存储标志，0为除上报案件之外的其他协议日志，1为上报案件日志,2位错误日志
     */
    public static void LogNetwork(String tag, String msg, int flag) {
        if(isDebug) {
            Log.e(tag, msg);
            String path = "";
            switch(flag) {
                case 0:
                    path = SDK_ROOT_PATH + "LogReport/" + "request_else/";
                    break;
                case 1:
                    path = SDK_ROOT_PATH + "LogReport/" + "request_case/";
                    break;
                case 2:
                    path = SDK_ROOT_PATH + "LogReport/" + "response_error/";
            }

            if(mkdirsLogFiles(path)) {
                saveLog(tag, msg, path);
            }
        }

    }

    /**
     * 通用保存日志到sd卡指定路径
     * @param tag
     * @param msg
     * @param path
     */
    public static void saveLogFile(String tag, String msg, String path) {
        if(isDebug) {
            Log.e(tag, msg);
            if(mkdirsLogFiles(path)) {
                saveLog(tag, msg, path);
            }
        }

    }

    private static boolean mkdirsLogFiles(String path) {
        boolean mkdirsed = false;
        File mypath = new File(path);
        if(mypath.exists()) {
            mkdirsed = true;
        } else {
            mkdirsed = mypath.mkdirs();
        }

        return mkdirsed;
    }

    private static boolean saveLog(String tag, String msg, String path) {
        File myfile = null;
        FileOutputStream fos = null;

        try {
            String e = (new SimpleDateFormat("yyyy-MM-dd__HH.mm.ssSSS")).format(new Date());
            String name = e + tag + ".txt";
            myfile = new File(path + name);
            if(!TextUtils.isEmpty(msg)) {
                myfile.createNewFile();
            }

            fos = new FileOutputStream(myfile);
            msg = tag + "\n" + msg + "\n";
            fos.write(msg.toString().getBytes("GBK"));
        } catch (Exception var13) {
            ;
        } finally {
            ;
        }

        try {
            fos.close();
            return true;
        } catch (Exception var12) {
            return false;
        }
    }
}
