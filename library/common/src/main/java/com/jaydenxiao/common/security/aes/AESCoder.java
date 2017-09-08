package com.jaydenxiao.common.security.aes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESCoder {

    private static final String algorithmStr = "AES/ECB/PKCS5Padding";

    /**
     * 为指定算法生成一个 KeyGenerator 对象。 此类提供（对称）密钥生成器的功能。 密钥生成器是使用此类的某个
     * getInstance 类方法构造的。 KeyGenerator 对象可重复使用，也就是说，在生成密钥后， 可以重复使用同一
     * KeyGenerator 对象来生成进一步的密钥。 生成密钥的方式有两种：与算法无关的方式，以及特定于算法的方式。
     * 两者之间的惟一不同是对象的初始化： 与算法无关的初始化 所有密钥生成器都具有密钥长度 和随机源 的概念。 此
     * KeyGenerator 类中有一个 init 方法，它可采用这两个通用概念的参数。 还有一个只带 keysize 参数的
     * init 方法， 它使用具有最高优先级的提供程序的 SecureRandom 实现作为随机源 （如果安装的提供程序都不提供
     * SecureRandom 实现，则使用系统提供的随机源）。 此 KeyGenerator 类还提供一个只带随机源参数的 inti
     * 方法。 因为调用上述与算法无关的 init 方法时未指定其他参数，
     * 所以由提供程序决定如何处理将与每个密钥相关的特定于算法的参数（如果有）。 特定于算法的初始化
     * 在已经存在特定于算法的参数集的情况下， 有两个具有 AlgorithmParameterSpec 参数的 init 方法。
     * 其中一个方法还有一个 SecureRandom 参数， 而另一个方法将已安装的高优先级提供程序的 SecureRandom
     * 实现用作随机源 （或者作为系统提供的随机源，如果安装的提供程序都不提供 SecureRandom 实现）。
     * 如果客户端没有显式地初始化 KeyGenerator（通过调用 init 方法）， 每个提供程序必须提供（和记录）默认初始化。
     */

    /**
     * 随机生成16位密钥
     *
     * @return
     */
    public static String getKey() {
        return CharacterUtils.getRandomString(16);
    }

    /**
     * 加密方法
     *
     * @param content
     * @param password
     * @return
     */
    private static byte[] encryptByte(String content, String password) {
        try {
            byte[] keyStr = getKey(password);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher = Cipher.getInstance(algorithmStr);// algorithmStr
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// ʼ
            byte[] result = cipher.doFinal(byteContent);
            return result; //
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密方法
     *
     * @param content
     * @param password
     * @return
     */
    private static byte[] decrypt(byte[] content, String password) {
        try {
            byte[] keyStr = getKey(password);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher = Cipher.getInstance(algorithmStr);// algorithmStr
            cipher.init(Cipher.DECRYPT_MODE, key);// ʼ
            byte[] result = cipher.doFinal(content);
            return result; //
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getKey(String password) {
        byte[] rByte = null;
        if (password != null) {
            rByte = password.getBytes();
        } else {
            rByte = new byte[24];
        }
        return rByte;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 加密方法
     *
     * @param content
     * @param password
     * @return
     */
    public static String encrypt(String content, String password) {
        // 加密之后的字节数组,转成16进制的字符串形式输出
        return parseByte2HexStr(encryptByte(content, password));
    }

    /**
     * 解密方法
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) {
        // 解密之前,先将输入的字符串按照16进制转成二进制的字节数组,作为待解密的内容输入
        byte[] b = decrypt(parseHexStr2Byte(content), password);
        return new String(b);
    }

    // 测试用例
    public static void test1() {
        String key = getKey();
        System.out.println("密钥：" + key);
        String content = "hello abcdefggsdfasdfasdf";
        String pStr = encrypt(content, key);
        System.out.println("加密前：" + content);
        System.out.println("加密后:" + pStr);

        String postStr = decrypt(pStr, key);
        System.out.println("解密后：" + postStr);
    }

    public static void main(String[] args) {
        test1();
    }
}
