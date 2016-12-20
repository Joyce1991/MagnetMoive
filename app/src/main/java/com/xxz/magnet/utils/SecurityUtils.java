package com.xxz.magnet.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 加密工具类
 *
 * @Date 15-12-03
 */
public class SecurityUtils {

    public static final String SECRET_KEY = "kyx@#pwd";
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    private final static String TAG = "SecurityUtils";
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    public static byte[] decrypt(byte[] cSrc) {
        int i, h, l, m, n;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (i = 0; i < cSrc.length; i = i + 2) {
            h = (cSrc[i] - 'x');
            l = (cSrc[i + 1] - 'z');
            m = (h << 4);
            n = (l & 0xf);
            out.write(m + n);
        }
        return out.toByteArray();
    }

    /**
     * 获取MD5
     *
     * @param value
     * @return
     */
    public static String getMd5(String value) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(value.getBytes());
            return toHexString(md5.digest());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 指定编码获取MD5
     *
     * @param value
     * @param charset
     * @return
     */
    public static String getMd5(String value, String charset) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(value.getBytes(charset));
            return toHexString(md5.digest());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static byte[] encrypt(byte[] cSrc) {
        byte c;
        int i, h, l;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (i = 0; i < cSrc.length; i++) {
            c = cSrc[i];
            h = (c >> 4) & 0xf;
            l = c & 0xf;
            out.write(h + 'x');
            out.write(l + 'z');
        }
        return out.toByteArray();
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception 异常
     */
    public static String encode(String data) throws Exception {
        return encode(SecurityUtils.SECRET_KEY, data.getBytes());
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception 异常
     */
    public static String encode(String key, String data) throws Exception {
        return encode(key, data.getBytes());
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception 异常
     */
    public static String encode(String key, byte[] data) throws Exception {
        try {

            DESKeySpec dks = new DESKeySpec(key.getBytes());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv;
            iv = new IvParameterSpec(key.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

            byte[] bytes = cipher.doFinal(data);


//      return byte2hex(bytes);
            return new String(Base64.encode(bytes, 0));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static byte[] decode(String key, byte[] data) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取编码后的值
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String decode(String key, String data) {
        byte[] datas;
        String value = null;
        try {
            datas = decode(key, Base64.decode(data.getBytes(), 0));
            value = new String(datas);
        } catch (Exception e) {
            value = "";
        }
        return value;
    }

    /**
     * 获取编码后的值
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decode(String data) {
        byte[] datas;
        String value = null;
        try {
            datas = decode(SECRET_KEY, Base64.decode(data.getBytes(), 0));
            value = new String(datas);
        } catch (Exception e) {
            value = "";
        }
        return value;
    }
}
