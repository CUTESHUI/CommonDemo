package com.shui.aspect.ipinterceptor;

import com.shui.annotation.IpInterceptor;
import com.shui.exception.ErrorCodeEnum;
import com.shui.exception.IpExcetion;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author shui
 */
@Aspect
@Component
@Slf4j
public class IpInterceptorAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Before("@annotation(com.shui.annotation.IpInterceptor)")
    public void requestLimit(JoinPoint joinPoint) {
        long incr = 0;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        IpInterceptor info = getAnnotation(joinPoint);
        if (info == null) {
            throw new IpExcetion(ErrorCodeEnum.GET_ANNOTATION_FAIL.getCode());
        }

        StringBuilder requestUrl = new StringBuilder();
        if (info.isRestful()) {
            String[] urls = request.getRequestURI().split("/");
            for (int i = 1; i < urls.length - info.restfulParamCounts(); i++) {
                requestUrl.append("/").append(urls[i]);
            }
        } else {
            requestUrl.append(request.getRequestURI());
        }

        StringBuilder redisKey = new StringBuilder("REQUEST_LIMIT:").append(requestUrl).append("-").append(request.getRemoteAddr());
        String key = redisKey.toString();

        if (redisTemplate.hasKey(key)) {
            incr = redisTemplate.opsForValue().increment(key);
            if (incr > info.requestCounts()) {
                throw new IpExcetion(ErrorCodeEnum.ACCESS_FREQUENCY_IS_TOO_FAST.getCode());
            }
        } else {
//            redisTemplate.setEnableTransactionSupport(true);
//            redisTemplate.multi();
            redisTemplate.opsForValue().increment(key);
            redisTemplate.expire(key, info.expiresTimeSecond(), TimeUnit.SECONDS);
//            redisTemplate.exec();
            log.debug("IP限速地址KEY：" + key + "——次数：" + incr);
        }
    }


    private IpInterceptor getAnnotation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(IpInterceptor.class);
        }
        return null;
    }

}
