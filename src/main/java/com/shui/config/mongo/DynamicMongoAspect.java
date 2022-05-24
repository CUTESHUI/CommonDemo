package com.shui.config.mongo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Mongo数据源切换切面
 */
@Slf4j
@Aspect
@Component
public class DynamicMongoAspect {

    @Pointcut("@annotation(com.shui.config.mongo.MongoDataSource)")
    public void mongoDataSource() {
    }


    @Before("mongoDataSource()")
    public void before(JoinPoint point) {
        try {
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Method method = point.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            MongoDataSource mongoDataSource = method.getAnnotation(MongoDataSource.class);
            MongoContext.setMongoDbFactory(mongoDataSource.value());
        } catch (Exception e) {
            log.error("==========>前置数据源切换异常", e);
        }
    }


    @After("mongoDataSource()")
    public void after(JoinPoint point) {
        try {
            MongoContext.removeMongoDbFactory();
        } catch (Exception e) {
            log.error("==========>后置数据源切换异常", e);
        }
    }

}