package com.shui.component;

import com.shui.exception.BaseException;
import com.shui.exception.ErrorCodeEnum;
import com.shui.exception.ErrorReponse;
import com.shui.exception.IpExcetion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Date;


@RestControllerAdvice
public class HandleControllerException {
    private final static Logger LOGGER = LoggerFactory.getLogger(HandleControllerException.class);

    @ExceptionHandler(Exception.class)
    public ErrorReponse handleException(Exception ex, HttpServletRequest request) {
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
    public ErrorReponse handleBaseException(BaseException ex, HttpServletRequest request) {
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
    public ErrorReponse handleBaseException(IpExcetion ex, HttpServletRequest request) {
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

    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public ErrorReponse handleValidationException(Exception ex, HttpServletRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ErrorReponse errorReponse = ErrorReponse.builder()
                .method(request.getMethod())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .date(new Date())
                .errorTypeName(ex.getClass().getName())
                .build();
        if (ex instanceof MethodArgumentNotValidException) {
            errorReponse.setCode(ErrorCodeEnum.VALID_METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getCode());
        } else if (ex instanceof ConstraintViolationException) {
            errorReponse.setCode(ErrorCodeEnum.VALID_VALIDATION_EXCEPTION.getCode());
        } else if (ex instanceof BindException) {
            errorReponse.setCode(ErrorCodeEnum.VALID_BIND_EXCEPTION.getCode());
        }
        return errorReponse;
    }
}
