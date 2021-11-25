package com.shui.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 幂等
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 幂等操作的唯一标识，使用spring el表达式 用#来引用方法参数
     */
    String key() default "";

    /**
     * 有效期 默认：1 有效期要大于程序执行时间，否则请求还是可能会进来
     */
    int expireTime() default 1;

    /**
     * 时间单位 默认：s
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 提示信息，可自定义
     */
    String info() default "重复请求，请稍后重试";

    /**
     * 是否在业务完成后删除key true:删除 false:不删除
     */
    boolean delKey() default false;

}
