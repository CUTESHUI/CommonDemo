package com.shui.annotation;

import java.lang.annotation.*;

/**
 * @author shui
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IpInterceptor {

    /**
     * 限制某时间段内可以访问的次数
     */
    long requestCounts() default 100;

    /**
     * 限制访问的某一个时间段，单位为秒
     */
    int expiresTimeSecond() default 60;

    /**
     * 接口是否是restful风格
     * 因为Restful风格请求参数是直接跟在请求地址后，由于每次传递的参数不同,也就无法判断真正的请求地址，由此需要标注
     */
    boolean isRestful() default false;

    /**
     * restful风格参数个数
     */
    int restfulParamCounts() default 0;
}
