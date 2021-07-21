package com.shui.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 字符串压缩工具类
 */
public class StringCompressUtils {

    /**
     * 字符串压缩
     */
    public static String compress(String source) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPOutputStream gos = new GZIPOutputStream(bos)) {
            gos.write(source.getBytes(StandardCharsets.ISO_8859_1));
            gos.close();
            return bos.toString("ISO-8859-1");
        } catch (IOException var3) {
            return source;
        }
    }

    /**
     * 字符串解压缩
     */
    public static String uncompress(String source) {
        if (source != null && source.length() != 0) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ByteArrayInputStream bis = new ByteArrayInputStream(source.getBytes(StandardCharsets.ISO_8859_1));
                 GZIPInputStream gis = new GZIPInputStream(bis)) {
                byte[] data = new byte[1024];

                for (int len = gis.read(data); len != -1; len = gis.read(data)) {
                    bos.write(data, 0, len);
                }
                return bos.toString();
            } catch (IOException var6) {
                return source;
            }
        } else {
            return source;
        }
    }

    public static void main(String[] args) {
        String originalStr = "ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggfdgfdgf"
                + "ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg"
                + "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggABCdef123中文~!@#$%^&*()_+{fd}"
                + ";/1111111111111111111111111AAAAAAAAAAAJDLFJDLFJDLFJLDFFFFJEIIIIIIIIIIFJJJJJJJJJJJJALLLLLLLLLLLLLLLLLLLLLLdddddddddddddddddfdfdfdfdfdfdd"
                + "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddessdfrdfdfd"
                + "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"
                + "ddddddddddddddddddddddddddddddddddddddddddddddddddddddgfgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggf"
                + "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"
                + "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"
                + "ddddddddddddddddddddddddddddddddddddddddddddggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "LLppppppppppppppppppppppppppppppppppppppppp===========================------------------------------iiiiiiiiiiiiiiiiiiiiiiiggggggggggggg";

        System.out.println("原始的字符串长度:" + originalStr.length());
        String compressStr = compress(originalStr);
        System.out.println("压缩的字符串长度:" + compressStr.length());
        String uncompress = uncompress(compressStr);
        System.out.println("解压后的字符串长度:" + uncompress.length());
        //字符串压缩率
        float rate = (float) compressStr.length() / uncompress.length();
        System.out.println("字符串压缩率：" + rate);
    }
}
