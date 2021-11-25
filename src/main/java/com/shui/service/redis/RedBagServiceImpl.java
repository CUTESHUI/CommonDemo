package com.shui.service.redis;

import com.shui.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author shui
 */
@Slf4j
@Service
public class RedBagServiceImpl {

    private static final String RED_BAG_ID_KEY = "RedBag:Id";
    private static final String RED_BAG_ID_USER_KEY = "RedBag:Id:User:Hash";
    private static final String RED_BAG_KEY = "RedBag:List";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String sendRedBag(Integer total, Integer count) {
        // 1、拆分金额
        Integer[] redBags = splitRedBag(total, count);

        // 2、生成红包唯一Id
        Long redBagId = redisTemplate.opsForValue().increment(RED_BAG_ID_KEY);
        String redBagKey = RED_BAG_KEY + ":" + redBagId;

        // 3、放到redis
        redisTemplate.opsForList().leftPushAll(redBagKey, redBags);
        redisTemplate.expire(redBagKey, 5, TimeUnit.MINUTES);
        return "总额为：" + total + "的红包已经拆分完" + "，红包Id为：" + redBagKey;
    }

    public String grabRedBag(Long redBagId, Long userId) {
        // 检查redBagId是否存在
        String redBagKey = RED_BAG_KEY + ":" + redBagId;
        if (!redisTemplate.hasKey(redBagKey)) {
            // 抢完红包，删除红包对应的用户
            String redBagIdUserKey = RED_BAG_ID_USER_KEY + ":" + redBagId;
            if (redisTemplate.hasKey(redBagIdUserKey)) {
                redisTemplate.delete(redBagIdUserKey);
                throw new BaseException("红包Id为：" + redBagKey + "已经抢完了");
            }
            throw new BaseException("红包Id为：" + redBagKey + "不存在");
        }

        // 检查redBagId的userId是否抢过
        String redBagIdUserKey = RED_BAG_ID_USER_KEY + ":" + redBagId;
        Object user = redisTemplate.opsForHash().get(redBagIdUserKey, userId.toString());
        if (user != null) {
            throw new BaseException("用户：" + userId + "已经抢过红包了");
        }

        Object redBag = redisTemplate.opsForList().leftPop(redBagKey);
        if (redBag != null) {
            redisTemplate.opsForHash().put(redBagIdUserKey, userId.toString(), redBag);
            return "用户：" + userId + "抢到了红包，金额为：" + redBag;
        }

        return "红包已经抢完了";
    }

    private Integer[] splitRedBag(Integer total, Integer count) {
        int use = 0;
        Integer[] redBags = new Integer[count];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                redBags[i] = total - use;
            } else {
                // 剩下的红包 / 剩下的人 = 平均
                int avg = (total - use) * 2 / (count - i);
                // 平均再随机
                redBags[i] = 1 + random.nextInt(avg - 1);
            }
            use += redBags[i];
        }
        return redBags;
    }

}
