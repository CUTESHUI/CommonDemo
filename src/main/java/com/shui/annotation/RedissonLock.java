package com.shui.annotation;

import java.lang.annotation.*;

/**
 * @author shui
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {

    /**
     * 锁名字
     */
    String lockName() default "";

    /**
     * 锁过期时间，单位秒
     */
    long expireTimeout() default 30L;

    /**
     * 锁等待时间，单位秒
     */
    long waitTimeout() default 30L;
}
