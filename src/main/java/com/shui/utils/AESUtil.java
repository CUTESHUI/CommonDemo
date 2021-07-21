package com.shui.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

public class AESUtil {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String AES_NAME = "AES";
    /**
     * 加密模式
     */
    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";
    /**
     * 密钥
     */
    private static final String KEY = "8b34unxr3sn5etio";
    /**
     * 偏移量
     */
    private static final String IV = "21xdygyjxvny9yz0";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 加密
     */
    public String encrypt(String content) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
            result = cipher.doFinal(content.getBytes(CHARSET_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(result);
    }

    /**
     * 解密
     */
    public String decrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
            return new String(cipher.doFinal(Base64.decodeBase64(content)), CHARSET_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public static void main(String[] args) {
        AESUtil aes = new AESUtil();
        String contents = "121456465";
        String encrypt = aes.encrypt(contents);
        System.out.println("加密后:" + encrypt);
        String decrypt = aes.decrypt(encrypt);
        System.out.println("解密后:" + decrypt);
    }
}
