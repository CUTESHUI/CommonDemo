package com.shui.utils;

import com.shui.config.RestTemplateConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * RestTemplate 远程调用工具类
 */
public class RestTemplateUtils {

    public static RestTemplate geTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
    }

    /**
     * GET请求调用方式
     */
    public static <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return geTemplate().getForEntity(url, responseType, uriVariables);
    }

    /**
     * POST请求调用方式
     */
    public static <T> ResponseEntity<T> postForEntity(String url, HttpHeaders headers, Object requestBody, Class<T> responseType) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return geTemplate().postForEntity(url, requestEntity, responseType);
    }

    /**
     * PUT请求调用方式
     */
    public static <T> ResponseEntity<T> put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return geTemplate().exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
    }

    /**
     * DELETE请求调用方式
     */
    public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return geTemplate().exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
    }

    /**
     * 通用调用方式
     */
    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return geTemplate().exchange(url, method, requestEntity, responseType, uriVariables);
    }
}