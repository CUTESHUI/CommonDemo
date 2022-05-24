package com.shui.config.mongo;

import java.lang.annotation.*;

/**
 * Mongo数据源切换
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MongoDataSource {

    String value() default "";

}