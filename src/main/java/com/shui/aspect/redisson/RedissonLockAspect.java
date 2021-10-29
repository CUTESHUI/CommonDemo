package com.shui.aspect.redisson;

import com.shui.annotation.RedissonLock;
import com.shui.component.RedissonTemplate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class RedissonLockAspect {

    @Autowired
    private RedissonTemplate redissonTemplate;

    @Pointcut("@annotation(com.shui.annotation.RedissonLock)")
    public void redissonLockPoint() {
    }

    @Around("redissonLockPoint()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        RedissonLock annotation = getAnnotation(proceedingJoinPoint);
        if (annotation == null) {
            return null;
        }
        try {
            boolean lock = redissonTemplate.tryLock(annotation.lockName(), annotation.waitTimeout(), TimeUnit.SECONDS);
            if (lock) {
                return proceedingJoinPoint.proceed();
            } else {
                return null;
            }
        } finally {
            redissonTemplate.unlock(annotation.lockName());
        }
    }

    private RedissonLock getAnnotation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(RedissonLock.class);
        }
        return null;
    }
}
