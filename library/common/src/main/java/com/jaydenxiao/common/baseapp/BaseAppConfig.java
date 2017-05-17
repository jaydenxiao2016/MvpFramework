package com.jaydenxiao.common.baseapp;


import android.os.Environment;

import java.io.File;

/**
 * ************************************************************************
 * **                              _oo0oo_                               **
 * **                             o8888888o                              **
 * **                             88" . "88                              **
 * **                             (| -_- |)                              **
 * **                             0\  =  /0                              **
 * **                           ___/'---'\___                            **
 * **                        .' \\\|     |// '.                          **
 * **                       / \\\|||  :  |||// \\                        **
 * **                      / _ ||||| -:- |||||- \\                       **
 * **                      | |  \\\\  -  /// |   |                       **
 * **                      | \_|  ''\---/''  |_/ |                       **
 * **                      \  .-\__  '-'  __/-.  /                       **
 * **                    ___'. .'  /--.--\  '. .'___                     **
 * **                 ."" '<  '.___\_<|>_/___.' >'  "".                  **
 * **                | | : '-  \'.;'\ _ /';.'/ - ' : | |                 **
 * **                \  \ '_.   \_ __\ /__ _/   .-' /  /                 **
 * **            ====='-.____'.___ \_____/___.-'____.-'=====             **
 * **                              '=---='                               **
 * ************************************************************************
 * **                        佛祖保佑      镇类之宝                         **
 * ************************************************************************
 *
 */
public class BaseAppConfig {

    public static final String DEBUG_TAG = "logger";// LogCat的标记
    /**
     * 图片服务器地址
     */
    public static final String IMAGE_SERVER="";
    /**
     * 根路径
     */
    public static final String ROOT_PATH = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "EasyFramework" + File.separator : BaseApplication.getAppContext().getCacheDir() + File.separator;
    /**
     * log路径
     */
    public static final String LOG_PATH = ROOT_PATH + "Log" + File.separator;
    /**
     * 此文件如果存在,就开启被捕获异常的日志输出
     */
    public static final String CAUGHT_EXCEPTION_FILE = ROOT_PATH + "/LogForCaughtException";
}
