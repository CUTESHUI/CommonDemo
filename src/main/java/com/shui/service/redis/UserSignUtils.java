package com.shui.service.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class UserSignUtils {

    private Jedis jedis = new Jedis();

    /**
     * 用户签到
     *
     * @param userId 用户ID
     * @param date   日期
     * @return 之前的签到状态
     */
    public boolean doSign(Long userId, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        return jedis.setbit(buildSignKey(userId, date), offset, true);
    }

    /**
     * 检查用户是否签到
     *
     * @param userId 用户ID
     * @param date   日期
     * @return 当前的签到状态
     */
    public boolean checkSign(Long userId, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        return jedis.getbit(buildSignKey(userId, date), offset);
    }

    /**
     * 获取用户签到次数
     *
     * @param userId 用户ID
     * @param date   日期
     * @return 当前的签到次数
     */
    public long getSignCount(Long userId, LocalDate date) {
        return jedis.bitcount(buildSignKey(userId, date));
    }

    /**
     * 获取当月连续签到次数
     *
     * @param userId 用户ID
     * @param date   日期
     * @return 当月连续签到次数
     */
    public long getContinuousSignCount(Long userId, LocalDate date) {
        int signCount = 0;
        String type = String.format("u%d", date.getDayOfMonth());
        List<Long> list = jedis.bitfield(buildSignKey(userId, date), "GET", type, "0");
        if (list != null && list.size() > 0) {
            // 取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = 0; i < date.getDayOfMonth(); i++) {
                if (v >> 1 << 1 == v) {
                    // 低位为0且非当天说明连续签到中断了
                    if (i > 0) break;
                } else {
                    signCount += 1;
                }
                v >>= 1;
            }
        }
        return signCount;
    }

    /**
     * bitField
     *
     * @param userId 用户ID
     * @param date   日期
     * @param offset 偏移
     */
    public List<Long> bitField(Long userId, LocalDate date, Integer limit, String offset) {
        String type = String.format("u%d", limit);
        return jedis.bitfield(buildSignKey(userId, date), "GET", type, offset);
    }

    /**
     * 获取当月首次签到日期
     *
     * @param userId 用户ID
     * @param date   日期
     * @return 首次签到日期
     */
    public LocalDate getFirstSignDate(Long userId, LocalDate date) {
        long pos = jedis.bitpos(buildSignKey(userId, date), true);
        return pos < 0 ? null : date.withDayOfMonth((int) (pos + 1));
    }

    /**
     * 获取当月签到情况
     *
     * @param userId 用户ID
     * @param date   日期
     * @return Key为签到日期，Value为签到状态的Map
     */
    public Map<String, Boolean> getSignInfo(Long userId, LocalDate date) {
        Map<String, Boolean> signMap = new HashMap<>(date.getDayOfMonth());
        String type = String.format("u%d", date.lengthOfMonth());
        List<Long> list = jedis.bitfield(buildSignKey(userId, date), "GET", type, "0");
        if (list != null && list.size() > 0) {
            // 由低位到高位，为0表示未签，为1表示已签
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = date.lengthOfMonth(); i > 0; i--) {
                LocalDate d = date.withDayOfMonth(i);
                signMap.put(formatDate(d, "yyyy-MM-dd"), v >> 1 << 1 != v);
                v >>= 1;
            }
        }
        return signMap;
    }

    private static String formatDate(LocalDate date) {
        return formatDate(date, "yyyy:MM");
    }

    private static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    private static String buildSignKey(Long userId, LocalDate date) {
        return String.format("UserSign:%s:%d", formatDate(date), userId);
    }


    public static void main(String[] args) {
        UserSignUtils demo = new UserSignUtils();
        LocalDate today = LocalDate.now();

        { // doSign
            boolean signed = demo.doSign(1000L, today);
            if (signed) {
                System.out.println("您已签到：" + formatDate(today, "yyyy-MM-dd"));
            } else {
                System.out.println("签到完成：" + formatDate(today, "yyyy-MM-dd"));
            }
        }

        { // checkSign
            boolean signed = demo.checkSign(1000L, today);
            if (signed) {
                System.out.println("您已签到：" + formatDate(today, "yyyy-MM-dd"));
            } else {
                System.out.println("尚未签到：" + formatDate(today, "yyyy-MM-dd"));
            }
        }

        { // getSignCount
            long count = demo.getSignCount(1000L, today);
            System.out.println("本月签到次数：" + count);
        }

        { // getContinuousSignCount
            long count = demo.getContinuousSignCount(1000L, today);
            System.out.println("连续签到次数：" + count);
        }

        { // getFirstSignDate
            LocalDate date = demo.getFirstSignDate(1000L, today);
            System.out.println("本月首次签到：" + formatDate(date, "yyyy-MM-dd"));
        }

        { // getSignInfo
            System.out.println("当月签到情况：");
            Map<String, Boolean> signInfo = new TreeMap<>(demo.getSignInfo(1000L, today));
            for (Map.Entry<String, Boolean> entry : signInfo.entrySet()) {
                System.out.println(entry.getKey() + ": " + (entry.getValue() ? "√" : "-"));
            }
        }
    }

}
