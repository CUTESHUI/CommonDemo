package com.shui.aspect.idempotent;

import com.shui.annotation.Idempotent;
import com.shui.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 幂等切面
 */
@Slf4j
@Aspect
public class IdempotentAspect {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private static final LocalVariableTableParameterNameDiscoverer DISCOVERER = new LocalVariableTableParameterNameDiscoverer();
    private static final ThreadLocal<Map<String, Object>> THREAD_CACHE = ThreadLocal.withInitial(HashMap::new);

    private static final String R_MAP_CACHE_KEY = "idempotent";
    private static final String IDEMPOTENT_KEY = "key";
    private static final String IDEMPOTENT_DELETE_KEY = "deleteKey";

    @Autowired
    private Redisson redisson;

    @Pointcut("@annotation(com.wzkj.common.annotation.Idempotent)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void beforePointCut(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (!method.isAnnotationPresent(Idempotent.class)) {
            return;
        }
        Idempotent idempotent = method.getAnnotation(Idempotent.class);

        String key;

        // 若没有配置 幂等 标识编号，则使用 url + 参数列表作为区分
        if (!StringUtils.hasLength(idempotent.key())) {
            String url = request.getRequestURL().toString();
            String argString = Arrays.asList(joinPoint.getArgs()).toString();
            key = url + argString;
        } else {
            key = resolver(idempotent, joinPoint);
        }

        long expireTime = idempotent.expireTime();
        String info = idempotent.info();
        TimeUnit timeUnit = idempotent.timeUnit();

        RMapCache<String, Object> mapCache = redisson.getMapCache(R_MAP_CACHE_KEY);
        String value = LocalDateTime.now().toString().replace("T", " ");
        Object v1;
        if (null != mapCache.get(key)) {
            throw new BaseException(info);
        }
        synchronized (this) {
            v1 = mapCache.putIfAbsent(key, value, expireTime, timeUnit);
            if (null != v1) {
                throw new BaseException(info);
            } else {
                log.info("[idempotent]:has stored key={},value={},expireTime={}{},now={}", key, value, expireTime, timeUnit, LocalDateTime.now().toString());
            }
        }

        Map<String, Object> map = THREAD_CACHE.get();
        map.put(IDEMPOTENT_KEY, key);
        map.put(IDEMPOTENT_DELETE_KEY, idempotent.delKey());
    }

    @After("pointCut()")
    public void afterPointCut(JoinPoint joinPoint) {
        Map<String, Object> map = THREAD_CACHE.get();
        if (CollectionUtils.isEmpty(map)) {
            return;
        }

        RMapCache<Object, Object> mapCache = redisson.getMapCache(R_MAP_CACHE_KEY);
        if (mapCache.size() == 0) {
            return;
        }

        String key = map.get(IDEMPOTENT_KEY).toString();
        boolean delKey = (boolean) map.get(IDEMPOTENT_DELETE_KEY);

        if (delKey) {
            mapCache.fastRemove(key);
            log.info("[idempotent]:has removed key={}", key);
        }
        THREAD_CACHE.remove();
    }

    /**
     * 解析spring el表达式 用#来引用方法参数
     */
    private String resolver(Idempotent idempotent, JoinPoint point) {
        Object[] arguments = point.getArgs();
        String[] params = DISCOVERER.getParameterNames(getMethod(point));
        StandardEvaluationContext context = new StandardEvaluationContext();

        if (params != null && params.length > 0) {
            for (int len = 0; len < params.length; len++) {
                context.setVariable(params[len], arguments[len]);
            }
        }

        Expression expression = PARSER.parseExpression(idempotent.key());
        return expression.getValue(context, String.class);
    }

    /**
     * 根据切点解析方法信息
     */
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return method;
    }

}
