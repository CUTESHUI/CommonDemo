package com.shui.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shui.common.Result;
import com.shui.exception.ErrorReponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;

import java.util.ArrayList;

@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 是否支持advice功能
     * true 支持，false 不支持
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 对返回的数据进行处理
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 这里需要过滤掉swagger的相关返回:
        // UiConfiguration：{domain}/swagger-resources/configuration/ui
        // ArrayList：{domain}/swagger-resources
        // Json：{domain}/v2/api-docs
        if (o instanceof Json || o instanceof UiConfiguration || (o instanceof ArrayList && ((ArrayList) o).get(0) instanceof SwaggerResource)) {
            return o;
        }
        if (o instanceof String) {
            return objectMapper.writeValueAsString(new Result<>().ok(o));
        }
        if (o instanceof ErrorReponse) {
            return o;
        }
        if (o instanceof Result) {
            return o;
        }
        return new Result<>().ok(o);
    }
}