package com.shui.service.redis;

import com.shui.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shui
 */
@Slf4j
@Service
public class LotteryServiceImpl {

    private static final String LOTTERY_KEY = "Lottery:Set";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String lottery() {
        String lotteryValue = redisTemplate.opsForSet().randomMember(LOTTERY_KEY).toString();
        if (StringUtils.isBlank(lotteryValue)) {
            throw new BaseException("抽奖繁忙，请重试");
        }
        int lotteryValuePrefix = Integer.parseInt(lotteryValue.substring(0, 1));

        String res;
        switch (lotteryValuePrefix) {
            case 1:
                res = "恭喜获得一等奖";
                break;
            case 2:
                res = "恭喜获得二等奖";
                break;
            case 3:
                res = "恭喜获得三等奖";
                break;
            default: res = "谢谢参与";
        }
        return res;
    }

//    @PostConstruct
    public void setLotteryWarehouse2Redis() {
        if (redisTemplate.hasKey(LOTTERY_KEY)) {
            log.info("redis中已经有初始化数据");
            return;
        }
        List<String> lotteryDataList = initLotteryData();
        lotteryDataList.stream().peek(data -> redisTemplate.opsForSet().add(LOTTERY_KEY, data)).collect(Collectors.toList());
        log.info("redis中初始化抽奖信息");
    }

    private List<String> initLotteryData() {
        List<String> lotteryDataList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            lotteryDataList.add("1#" + i);
        }

        for (int i = 0; i < 2; i++) {
            lotteryDataList.add("2#" + i);
        }

        for (int i = 0; i < 3; i++) {
            lotteryDataList.add("3#" + i);
        }

        for (int i = 0; i < 4; i++) {
            lotteryDataList.add("9#" + i);
        }
        return lotteryDataList;
    }


}
