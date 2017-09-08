package com.jaydenxiao.common.security.rsa;

import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.baseapp.SysLogBiz;

/**
 * 类名：RSAEncyptUtil.java
 * 描述：获取公钥
 * 公司：北京海鑫科鑫高科技股份有限公司
 * 作者：xsf
 * 创建时间：2017/9/8
 * 最后修改时间：2017/9/8
 */

public class RSAEncyptUtil {
    /**
     * 缓存的公钥
     */
    private static byte[] publicKey = new byte[0];
    /**
     * 指定公钥存放文件
     */
    private static String PUBLIC_KEY_FILE = "PublicKey.txt";

    /**
     * 获取通过公钥加密的私钥信息
     * @param srcData
     * @return
     */
    public static String getKeyByRSAandAESEncypt(String srcData) {
        String result = "-1";
        try {
            if (publicKey.length == 0) {
                publicKey = RSACoderForAndroid.getPublicKeyForAndroid(PUBLIC_KEY_FILE, BaseApplication.getAppContext());
            }
            result = RSACoderForAndroid.encryptByPublicKey(srcData, publicKey);
        } catch (Exception e) {
            SysLogBiz.saveCrashInfoFile(SysLogBiz.getExceptionInfo(e));
        }
        return result;
    }
}
