package com.shui.exception;

import lombok.Data;


@Data
public class BaseException extends RuntimeException {

    private int code;
    private String message;

    public BaseException(int code) {
        this.code = code;
        this.message = ErrorCodeEnum.findMessageByCode(code);
    }

    public BaseException(String message) {
        super(message);
        this.code = ErrorCodeEnum.SYSTEM_ERROR.getCode();
        this.message = message;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
