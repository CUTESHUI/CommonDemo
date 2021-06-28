package com.shui.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author shui
 */
@Api(tags = "request/filter测试")
@RestController
@RequestMapping("/request/filter")
public class RequestFilterTestController {

    @GetMapping("test1")
    @ApiOperation("test1")
    public void test1(HttpServletRequest request) {
        String name = request.getHeader("name");
        Enumeration<String> headerNames = request.getHeaderNames();
        Enumeration<String> names = request.getHeaders("name");
    }

    @GetMapping("test2")
    @ApiOperation("test2")
    public void test2(HttpServletRequest request) {
        String name = request.getHeader("name");
    }

    @GetMapping("test3")
    @ApiOperation("test3")
    public void test3(HttpServletRequest request) {
        String name = request.getParameter("name");
    }


}
