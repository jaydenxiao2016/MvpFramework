package com.jaydenxiao.common.security.rsa;

import android.app.Application;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA安全编码组件
 *
 * @author 程林
 * @version 1.0
 */
public abstract class RSACoderForAndroid extends RSACoder {

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  私钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        return RSACoder.decryptByPrivateKey(data, key, true);
    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  私钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, byte[] key) throws Exception {
        return RSACoder.decryptByPrivateKey(data, key, true);
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  公钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
        return RSACoder.decryptByPublicKey(data, key, true);
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  公钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, byte[] key) throws Exception {
        return RSACoder.decryptByPublicKey(data, key, true);
    }


    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key  公钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
        return RSACoder.encryptByPublicKey(data, key, true);
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key  公钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, byte[] key) throws Exception {
        return RSACoder.encryptByPublicKey(data, key, true);
    }

    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key  私钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        return RSACoder.encryptByPrivateKey(data, key, true);
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryByPrivateKey(String data, byte[] key) throws Exception {
        return RSACoder.encryByPrivateKey(data, key, true);
    }

    /**
     * 获取公钥
     *
     * @param path
     * @param app
     * @return
     * @throws IOException
     */
    public static byte[] getPublicKeyForAndroid(String path, Application app) throws IOException {
        RSAPublicKey publicKey;

        AssetManager am = app.getAssets();
        InputStream is = am.open(path);
        ObjectInputStream ois = null;
        try {
            /** 将文件中的公钥对象读出 */
            ois = new ObjectInputStream(is);
            publicKey = (RSAPublicKey) ois.readObject();
            return publicKey.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ois.close();
        }
        return null;
    }

    /**
     * 获取私钥
     *
     * @param path
     * @param app
     * @return
     * @throws IOException
     */
    public static byte[] getPrivateKeyAndroid(String path, Application app) throws IOException {
        RSAPrivateKey privateKey;
        AssetManager am = app.getAssets();
        InputStream is = am.open(path);
        ObjectInputStream ois = null;
        try {
            /** 将文件中的私钥对象读出 */
            ois = new ObjectInputStream(is);
            privateKey = (RSAPrivateKey) ois.readObject();
            return privateKey.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ois.close();
        }
        return null;
    }
}
