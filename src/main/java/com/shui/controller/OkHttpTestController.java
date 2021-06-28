package com.shui.controller;

import com.shui.utils.OkHttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.Call;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;


@Api(tags = "okhttp测试")
@RestController
@RequestMapping("/okhttp")
public class OkHttpTestController {

    @GetMapping("testGet")
    @ApiOperation("testGet")
    public String testGet() {
        return OkHttpUtils.builder()
                .url("http://www.baidu.com")
                .addHeader("Content-Type" , "application/json; charset=utf-8")
                .get()
                .sync();
    }

    @GetMapping("testPost")
    @ApiOperation("testPost")
    public String testPost() {
        return OkHttpUtils.builder()
                .url("http://www.baidu.com")
                .addHeader("Content-Type" , "application/json; charset=utf-8")
                .post(true)
                .sync();
    }

    @GetMapping("testAsync")
    @ApiOperation("testAsync")
    public String testAsync() {
        return OkHttpUtils.builder()
                .url("http://www.baidu.com")
                .addHeader("Content-Type" , "application/json; charset=utf-8")
                .post(true)
                .async();
    }

    @GetMapping("testAsync1")
    @ApiOperation("testAsync1")
    public void testAsync1() {
        OkHttpUtils.builder()
                .url("http://www.baidu.com")
                .addHeader("Content-Type" , "application/json; charset=utf-8")
                .post(true)
                .async(new OkHttpUtils.ICallBack() {
                    @Override
                    public void onSuccessful(Call call, String data) {
                        // 请求成功后的处理
                        System.out.println("成功");
                    }

                    @Override
                    public void onFailure(Call call, String errorMsg) {
                        // 请求失败后的处理
                        System.out.println("失败");
                    }
                });
    }

    @GetMapping("testAsync2")
    @ApiOperation("testAsync2")
    public void testAsync2() {
        File file = new File("/data");
        OkHttpUtils.builder()
                .url("http://www.baidu.com")
                .addHeader("Content-Type" , "application/json; charset=utf-8")
                .post(true)
                .async(new OkHttpUtils.ICallBack() {
                    @Override
                    public void onSuccessful(Call call, String data) {
                        // 请求成功后的处理
                        System.out.println("成功");
                    }

                    @Override
                    public void onFailure(Call call, String errorMsg) {
                        // 请求失败后的处理
                        System.out.println("失败");
                    }
                });
    }


}
