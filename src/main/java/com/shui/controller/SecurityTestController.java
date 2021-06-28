package com.shui.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shui
 */
@RestController
@Api(tags = "security测试")
public class SecurityTestController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("login")
    public String login() {
        return "login";
    }
}
