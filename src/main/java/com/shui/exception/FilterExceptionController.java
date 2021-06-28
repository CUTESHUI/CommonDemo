package com.shui.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FilterExceptionController {

    /**
     * 重新抛出过滤器的异常
     */
    @RequestMapping("/filter/exception")
    public void rethrow(HttpServletRequest request) {
        throw new BaseException(request.getAttribute("filterException").toString());
    }
}
