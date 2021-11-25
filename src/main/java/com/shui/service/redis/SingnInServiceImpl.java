package com.shui.service.redis;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.shui.dao.UserSignIntegralDao;
import com.shui.dao.UserSignIntegralLogDao;
import com.shui.domain.entity.UserSignIntegral;
import com.shui.domain.entity.UserSignIntegralLog;
import com.shui.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SingnInServiceImpl {

    /**
     * 人员签到位图，一个位图存一个用户一年的签到状态
     * 两个参数：今年的年份和用户的id
     */
    private final static String USER_SIGN_IN = "UserSign:%d:%d";
    /**
     * 人员补签，一个Hash列表存用户一个月的补签状态,
     * 两个参数：当月的月份和用户的id
     */
    private final static String USER_RETROACTIVE_SIGN_IN = "UserSign:retroactive:%d:%d";
    /**
     * 人员签到总天数
     * 参数是用户的id
     */
    private final static String USER_SIGN_IN_COUNT = "UserSign:count:%d";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserSignUtils userSignUtils;
    @Autowired
    private UserSignIntegralLogDao userSignIntegralLogDao;
    @Autowired
    private UserSignIntegralDao userSignIntegralDao;

    public void signIn(Long userId) {
        LocalDate localDate = LocalDate.now();
        if (userSignUtils.checkSign(userId, localDate)) {
            throw new BaseException("今日已经签到");
        }
        boolean doSignResult = userSignUtils.doSign(userId, localDate);
        if (!doSignResult) {
            long signContinuousCount = userSignUtils.getContinuousSignCount(userId, localDate);
            doSaveUserIntegral(userId, signContinuousCount);
        }
    }

    /**
     * 记录签到、连续签到积分
     */
    private void doSaveUserIntegral(Long userId, long signContinuousCount) {
        // 总积分
        int totalIntegral = 0;

        // 叠加签到次数
        redisTemplate.opsForValue().increment(String.format(USER_SIGN_IN_COUNT, userId));
        UserSignIntegralLog userSignIntegralLog = UserSignIntegralLog.builder()
                .operationTime(new Date())
                .bak("签到")
                .integral(1)
                .integralType(1)
                .userId(userId)
                .build();
        userSignIntegralLogDao.insert(userSignIntegralLog);
        totalIntegral += 1;

        // 连续签到处理
        doSaveContinuousIntegral(totalIntegral, userId, signContinuousCount);

        // 改变总积分
        UserSignIntegral userSignIntegral = userSignIntegralDao.selectOne(new LambdaQueryWrapper<UserSignIntegral>().eq(UserSignIntegral::getUserId, userId));
        if (userSignIntegral == null) {
            userSignIntegral = UserSignIntegral.builder()
                    .integralTotal(totalIntegral)
                    .userId(userId)
                    .build();
            userSignIntegralDao.insert(userSignIntegral);
        } else {
            userSignIntegral.setIntegralTotal(totalIntegral);
            userSignIntegralDao.updateById(userSignIntegral);
        }
    }

    private void doSaveContinuousIntegral(int count, Long userId, long signContinuousCount) {
        // 这个月的最大天数
        int monthLength = LocalDate.now().lengthOfMonth();
        // 初始化最大满足连续签到次数
        int times = 0;
        String[] continuousDays = {"3", "7", "15", "32"};
        // 定义一个数组,存放连续天数满足条件
        // 时间节点是5天,7天,15天,满月,因为满月无法确定天数所以写做32特殊值
        // 如果是满月的情况就会有4次命中连续签到,分别为3,7,15,32
        // 连续签到大于15天的话应该是3次命中,3,7,15
        // ....
        if (signContinuousCount == monthLength) {
            times = 4;
        } else if (signContinuousCount >= 15) {
            times = 3;
        } else if (signContinuousCount >= 7) {
            times = 2;
        } else if (signContinuousCount >= 3) {
            times = 1;
        } else {
            return;
        }

        // 取出库中连续签到的签到记录类型的次数,周期是一个月
//        String startTime = DateUtil.getThisMonthFirstDay().toString();
//        String endTime = DateUtil.getThisMonthLastDay().toString();
        int timesInDb = userSignIntegralLogDao.selectSignByTime(userId, "", "");

        //定义循环,次数为他连续签到次数最大命中次数
        for (int i = 0; i < times; i++) {
            // 当库中存在的条数不为0,则需要-1并跳过这次循环
            // 如这个月签到连续次数为7,之前签到满了5天给了奖励,这里timesInDb查出为1,然后我们不希望出现重复的奖励,timesInDb-1且跳过这次循环,只记录满7天的这一次奖励
            // 注:这样设计是因为补签可能为中间的断层,如签到了1,2,3,4,6,7,8,这样如果补签第5天的时候,就会给用户满5天和满7天的奖励,这样写就可以兼容补签和普通签到的逻辑回路
            if (timesInDb != 0) {
                timesInDb--;
                continue;
            }

            // 获取缓存配置连续签到奖励
            Map<String, Object> configurationHashMap = (Map<String, Object>) redisTemplate.opsForHash().get("UserSign:configuration:continue", signContinuousCount);
            String configuration = (String) configurationHashMap.get(continuousDays[i]);
            if (null != configuration) {
                JSONObject item = JSONObject.parseObject(configuration);
                int giveIntegral = item.getInteger("integral");
                if (giveIntegral != 0) {
                    UserSignIntegralLog.builder()
                            .bak("连续签到" + continuousDays[i])
                            .integral(giveIntegral)
                            .integralType(2)
                            .userId(userId)
                            .build();
                    count += giveIntegral;
                }
            }
        }
    }

    public void signInRetroactive(Long userId, Integer day) {
        LocalDate localDate = LocalDate.now();
        LocalDate retroactiveDate = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), day);

        // 检测是否补签达上限
        String retroactiveKey = String.format(USER_RETROACTIVE_SIGN_IN, localDate.getMonthValue(), userId);
        Set<String> keys = (Set<String>) redisTemplate.opsForHash().get(retroactiveKey, retroactiveDate.getDayOfMonth() + "");
        if (CollectionUtils.isNotEmpty(keys) && keys.size() == 3) {
            throw new BaseException("本月补签次数已达上限");
        }

        // 检查补签积分是否足够,这里就是一个简单的单表查询,用于查询积分是否足够本次消耗
        UserSignIntegral userSignIntegral = userSignIntegralDao.selectOne(new LambdaQueryWrapper<UserSignIntegral>().eq(UserSignIntegral::getUserId, userId));
        Map<String, Object> configurationHashMap = (Map<String, Object>) redisTemplate.opsForHash().get("UserSign:configuration:retroactive", keys.size());
        Integer reduceIntegral = (Integer) configurationHashMap.get("reduceIntegral");
        if (reduceIntegral > userSignIntegral.getIntegralTotal()) {
            throw new BaseException("您的积分不够了");
        }

        // 是否用户今日签到过同时补签日期不可大于今天的日期
        if (!userSignUtils.checkSign(userId, localDate) && localDate.getDayOfMonth() > day) {
            boolean oldResult = userSignUtils.doSign(userId, localDate);
            if (!oldResult) {
                // 补签记录(:月份) 过月清零,过期时间是计算出当前时间的差值,补签次数是一个月一刷新的
                redisTemplate.opsForHash().put(
                        retroactiveKey,
                        retroactiveDate.getDayOfMonth() + "",
                        "1"
                );
                redisTemplate.expire(retroactiveKey, Math.max(retroactiveDate.lengthOfMonth() - localDate.getDayOfMonth(), 1) * 60 * 60 * 24, TimeUnit.SECONDS);

                // 这里就是对积分总表减少.以及对积分记录进行记录.参照下方代码块
                doRemoveUserIntegral(userId, reduceIntegral);
            }
        }
    }

    /**
     * 积分减少并写入积分变动记录
     */
    private void doRemoveUserIntegral(Long userId, int reduceIntegral) {
        UserSignIntegralLog userSignIntegralLog = UserSignIntegralLog.builder()
                .operationTime(new Date())
                .bak("补签")
                .integral(-reduceIntegral)
                .integralType(5)
                .userId(userId)
                .build();
        userSignIntegralLogDao.insert(userSignIntegralLog);

        UserSignIntegral userSignIntegral = userSignIntegralDao.selectOne(new LambdaQueryWrapper<UserSignIntegral>().eq(UserSignIntegral::getUserId, userId));
        userSignIntegral.setIntegralTotal(userSignIntegral.getIntegralTotal() - reduceIntegral);
        userSignIntegralDao.updateById(userSignIntegral);
    }

    public Map<String, Object> signInInfo(Long userId, Integer year, Integer month, Integer day) {
        LocalDate date = LocalDate.of(year, month, day);

        // 查询reids中当前用户补签的hash列表 (hash列表的key为补签的日期,value存在就说明这个日期补签了)
        String retroactiveKey = String.format(USER_RETROACTIVE_SIGN_IN, date.getMonthValue(), userId);
        Set<String> keys = (Set<String>) redisTemplate.opsForHash().get(retroactiveKey, date + "");

//        Map<String, Boolean> signMap = userSignUtils.getSignInfo(userId, date);
        TreeMap<Integer, Integer> signMap = new TreeMap<>();

        List<Long> list = userSignUtils.bitField(userId, date, date.lengthOfMonth(), "0");
        if (list != null && list.size() > 0) {
            // 由低位到高位，为0表示未签，为1表示已签
            long v = list.get(0) == null ? 0 : list.get(0);
            //循环次数为当月的天数
            for (int i = date.lengthOfMonth(); i > 0; i--) {
                LocalDate d = date.withDayOfMonth(i);
                int type = 0;
                if (v >> 1 << 1 != v) {
                    //状态为正常签到
                    type = 1;
                }
                if (keys != null && keys.contains(d.getDayOfMonth() + "")) {
                    //状态为补签
                    type = 2;
                }
                // 返回给前端当月的所有日期,以及签,补签或者未签的状态
                signMap.put(Integer.parseInt(d.format(DateTimeFormatter.ofPattern("dd"))), type);
                v >>= 1;
            }
        }


        Map<String, Object> result = new HashMap<>(2);
        // 用户总积分
        result.put("total", userSignIntegralDao.selectOne(new LambdaQueryWrapper<UserSignIntegral>().eq(UserSignIntegral::getUserId, userId)).getIntegralTotal());
        // 用户今日是否签到
        result.put("todaySignFlag", userSignUtils.checkSign(userId, date) ? 1 : 0);
        // 后端返回日期是为了防止手机端直接修改系统时间导致的问题
        result.put("today", LocalDate.now().getDayOfMonth());
        // 当月的签到情况
        result.put("signCalendar", signMap);
        // 返回给前端这个月的第一天是星期几,方便前端渲染日历图的时候定位
        result.put("firstDayOfWeek", date.getDayOfWeek().getValue());
        // 服务器的当前月份(同上,防止手机端直接修改系统时间)
        result.put("monthValue", date.getMonthValue());
        // 用户当月补签的次数
        result.put("retroactiveCount", keys != null ? keys.size() : 0);
        // 日历部分会有上月的结尾几天的数据,所以这里需要返回给前端上个月共有多少天
        result.put("lengthOfLastMonth", date.minusMonths(1).lengthOfMonth());
        return result;
    }

}
