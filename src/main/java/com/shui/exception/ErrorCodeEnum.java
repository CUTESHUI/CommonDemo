package com.shui.exception;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public enum ErrorCodeEnum {

    /**
     * 系统错误，请联系管理员
     */
    SYSTEM_ERROR(1001, "系统错误，请联系管理员"),
    GET_ANNOTATION_FAIL(500, "注解信息获取失败"),
    ACCESS_FREQUENCY_IS_TOO_FAST(403, "访问频率太快"),
    VALID_BIND_EXCEPTION(2001, "校验错误"),
    VALID_VALIDATION_EXCEPTION(2002, "校验错误"),
    VALID_METHOD_ARGUMENT_NOT_VALID_EXCEPTION(2003, "校验错误");

    private final int code;
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过错误码查找其中文含义
     */
    public static String findMessageByCode(int code) {
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            if (value.code == code) {
                return value.message;
            }
        }
        return "未找到ErrorCode对应错误";
    }

}
