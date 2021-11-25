package com.shui.service.redis;

import com.shui.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class BlackUserServiceImpl {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String BLACK_USER_KEY = "BlackUser:Set";

    public String addBlackUser(Long userId) {
        if (redisTemplate.opsForSet().isMember(BLACK_USER_KEY, userId)) {
            throw new BaseException("用户：" + userId + "已经存在了");
        }
        redisTemplate.opsForSet().add(BLACK_USER_KEY, userId);
        return "添加黑名单用户成功";
    }

    public String removeBlackUser(Long userId) {
        if (!redisTemplate.opsForSet().isMember(BLACK_USER_KEY, userId)) {
            throw new BaseException("用户：" + userId + "不存在");
        }
        redisTemplate.opsForSet().remove(BLACK_USER_KEY, userId);
        return "移除黑名单用户成功";
    }

//    @PostConstruct
    public void initBlackUserData() {
        // 从db取
        for (long i = 0; i < 10; i++) {
            redisTemplate.opsForSet().add(BLACK_USER_KEY, i);
        }
        log.info("初始化黑名单数据成功");
    }
}
