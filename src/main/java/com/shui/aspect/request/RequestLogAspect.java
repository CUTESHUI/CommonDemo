//package com.shui.aspect.request;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Component
//@Aspect
//@Slf4j
//public class RequestLogAspect {
//
//    @Pointcut("execution(* com.shui.controller..*(..))")
//    public void requestServer() {
//    }
//
//    @Around("requestServer()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        Object result = proceedingJoinPoint.proceed();
//        RequestInfo requestInfo = RequestInfo.builder()
//                .ip(request.getRemoteAddr())
//                .url(request.getRequestURI())
//                .httpMethod(request.getMethod())
//                .classMethod(String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName()))
//                .requestParams(getRequestParamsByProceedingJoinPoint(proceedingJoinPoint))
//                .result(result)
//                .timeCost(System.currentTimeMillis() - start)
//                .build();
//        log.info("Request Info : {}", JSON.toJSONString(requestInfo));
//
//        return result;
//    }
//
//    @AfterThrowing(pointcut = "requestServer()", throwing = "e")
//    public void doAfterThrow(JoinPoint joinPoint, RuntimeException e) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        RequestErrorInfo requestErrorInfo = RequestErrorInfo.builder()
//                .ip(request.getRemoteAddr())
//                .url(request.getRequestURI())
//                .httpMethod(request.getMethod())
//                .classMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()))
//                .requestParams(getRequestParamsByJoinPoint(joinPoint))
//                .exception(e)
//                .build();
//        log.error("Error Request Info : {}", JSON.toJSONString(requestErrorInfo));
//    }
//
//    private Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
//        //参数名
//        String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
//        //参数值
//        Object[] paramValues = proceedingJoinPoint.getArgs();
//        for (int i = 0; i < paramValues.length; i++) {
//            Object paramValue = paramValues[i];
//            if (paramValue instanceof HttpServletRequestWrapper) {
//                paramValues[i] = "HttpServletRequest";
//            }
//        }
//
//        return buildRequestParam(paramNames, paramValues);
//    }
//
//    private Map<String, Object> getRequestParamsByJoinPoint(JoinPoint joinPoint) {
//        //参数名
//        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
//        //参数值
//        Object[] paramValues = joinPoint.getArgs();
//
//        return buildRequestParam(paramNames, paramValues);
//    }
//
//    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
//        Map<String, Object> requestParams = new HashMap<>();
//        for (int i = 0; i < paramNames.length; i++) {
//            Object value = paramValues[i];
//
//            //如果是文件对象
//            if (value instanceof MultipartFile) {
//                MultipartFile file = (MultipartFile) value;
//                value = file.getOriginalFilename();
//            }
//
//            requestParams.put(paramNames[i], value);
//        }
//
//        return requestParams;
//    }
//}
