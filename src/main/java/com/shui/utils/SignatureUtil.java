package com.shui.utils;

import java.security.MessageDigest;
import java.util.Arrays;

public class SignatureUtil {
    private static final String accessToken = "123456";

    public static String getSignature(String timestamp, String nonce) {
        String[] str = new String[]{accessToken, timestamp, nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuilder builder = new StringBuilder();
        for (String s : str) {
            builder.append(s);
        }
        //进行sha1加密
        SHA1 sha1 = new SHA1();
        return sha1.encode(builder.toString());
    }

    public static class SHA1 {
        private final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        private String getFormattedText(byte[] bytes) {
            int len = bytes.length;
            StringBuilder buf = new StringBuilder(len * 2);
            for (byte aByte : bytes) {
                buf.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
                buf.append(HEX_DIGITS[aByte & 0x0f]);
            }
            return buf.toString();
        }

        String encode(String str) {
            if (str == null) return null;
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        String signature = getSignature("1231123123", "12312312");
        System.out.println(signature);
    }
}
