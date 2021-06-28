package com.shui.aspect.request;

import lombok.*;

/**
 * @author shui
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
class RequestErrorInfo {
    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private Exception exception;
}
