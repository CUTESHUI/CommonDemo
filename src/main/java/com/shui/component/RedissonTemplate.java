package com.shui.component;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class RedissonTemplate {

    private final RedissonClient redissonClient;

    /**
     * 锁前缀
     */
    private static final String DEFAULT_LOCK_NAME = "LOCK_";

    public RedissonTemplate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 加锁（可重入），会一直等待获取锁，不会中断
     */
    public boolean lock(String lockName, long timeout) {
        checkRedissonClient();
        RLock lock = getLock(lockName);
        try {
            if (timeout != -1) {
                // timeout:超时时间   TimeUnit.SECONDS：单位
                lock.lock(timeout, TimeUnit.SECONDS);
            } else {
                lock.lock();
            }
            log.debug(" get lock success ,lockKey:{}", lockName);
            return true;
        } catch (Exception e) {
            log.error(" get lock fail,lockKey:{}, cause:{} ", lockName, e.getMessage());
            return false;
        }
    }

    /**
     * 可中断锁
     */
    public boolean tryLock(String lockName, long waitTimeout, TimeUnit unit) {
        checkRedissonClient();
        RLock lock = getLock(lockName);
        try {
            boolean res = lock.tryLock(waitTimeout, unit);
            if (!res) {
                log.debug(" get lock fail ,lockKey:{}", lockName);
                return false;
            }
            log.debug(" get lock success ,lockKey:{}", lockName);
            return true;
        } catch (Exception e) {
            log.error(" get lock fail,lockKey:{}, cause:{} ",
                    lockName, e.getMessage());
            return false;
        }
    }

    /**
     * 解锁
     */
    public void unlock(String lockName) {
        checkRedissonClient();
        try {
            RLock lock = getLock(lockName);
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.debug("key：{}，unlock success", lockName);
            } else {
                log.debug("key：{}，没有加锁或者不是当前线程加的锁 ", lockName);
            }
        } catch (Exception e) {
            log.error("key：{}，unlock error,reason:{}", lockName, e.getMessage());
        }
    }


    private RLock getLock(String lockName) {
        String key = DEFAULT_LOCK_NAME + lockName;
        return redissonClient.getLock(key);
    }


    private void checkRedissonClient() {
        if (null == redissonClient) {
            log.error(" redissonClient is null ,please check redis instance ! ");
            throw new RuntimeException("redissonClient is null ,please check redis instance !");
        }
        if (redissonClient.isShutdown()) {
            log.error(" Redisson instance has been shut down !!!");
            throw new RuntimeException("Redisson instance has been shut down !!!");
        }
    }
}
