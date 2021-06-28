package com.shui.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestControllerAdvice
public class HandleControllerException {
    private final static Logger LOGGER = LoggerFactory.getLogger(HandleControllerException.class);

    @ExceptionHandler(Exception.class)
    public ErrorReponse handleException(Exception ex, HttpServletRequest request){
        LOGGER.error(ex.getMessage(), ex);
        return ErrorReponse.builder()
                .code(1001)
                .method(request.getMethod())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .date(new Date())
                .errorTypeName(ex.getClass().getName())
                .build();
    }

    @ExceptionHandler(BaseException.class)
    public ErrorReponse handleBaseException(BaseException ex, HttpServletRequest request){
        LOGGER.error(ex.getMessage(), ex);
        return ErrorReponse.builder()
                .code(ex.getCode())
                .method(request.getMethod())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .date(new Date())
                .errorTypeName(ex.getClass().getName())
                .build();
    }

    @ExceptionHandler(IpExcetion.class)
    public ErrorReponse handleBaseException(IpExcetion ex, HttpServletRequest request){
        LOGGER.error(ex.getMessage(), ex);
        return ErrorReponse.builder()
                .code(ex.getCode())
                .method(request.getMethod())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .date(new Date())
                .errorTypeName(ex.getClass().getName())
                .build();
    }
}
