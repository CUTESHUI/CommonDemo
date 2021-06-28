package com.shui.controller;

import com.shui.annotation.IpInterceptor;
import com.shui.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shui
 */
@Api(tags = "ipInterceptor测试")
@RestController
@RequestMapping("/ipInterceptor")
public class IpInterceptorTestController {


    @GetMapping(value = "/test1/{id}")
    @ApiOperation("test1")
    @IpInterceptor(requestCounts = 3, expiresTimeSecond = 3, isRestful = true, restfulParamCounts = 1)
    public Result test1(@PathVariable("id") String id) {
        return new Result().ok("处理成功--->" + id);
    }

}
