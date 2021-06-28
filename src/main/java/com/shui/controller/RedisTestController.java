package com.shui.controller;

import com.shui.annotation.IpInterceptor;
import com.shui.common.Result;
import com.shui.dao.UserDao;
import com.shui.domain.User;
import com.shui.service.redis.BlackUserServiceImpl;
import com.shui.service.redis.LotteryServiceImpl;
import com.shui.service.redis.RedBagServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shui
 */
@Api(tags = "redis测试")
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LotteryServiceImpl lotteryService;
    @Autowired
    private RedBagServiceImpl redBagService;
    @Autowired
    private BlackUserServiceImpl blackUserService;

    @GetMapping("saveAll2Redis")
    @ApiOperation("保存所有user到redis")
    public void saveAll2Redis() {
        List<User> all = userDao.selectList(null);
        all.stream().peek(user -> redisTemplate.opsForValue().set(user.getId().toString(), user)).collect(Collectors.toList());
    }

    @GetMapping("getAll")
    @ApiOperation("获取所有use")
    public List getAllUser() {
        Set<String> keys = redisTemplate.keys("*");
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @GetMapping("lottery")
    @ApiOperation("抽奖Set")
    @IpInterceptor(requestCounts = 3, expiresTimeSecond = 10, isRestful = true, restfulParamCounts = 0)
    public Result<String> lottery() {
        return new Result<String>().ok(lotteryService.lottery());
    }


    @GetMapping("sendRedBag")
    @ApiOperation("发红包")
    @IpInterceptor(requestCounts = 1, expiresTimeSecond = 1, isRestful = true, restfulParamCounts = 2)
    public Result<String> sendRedBag(@ApiParam("红包总额") @RequestParam("total") Integer total, @ApiParam("红包个数") @RequestParam("count") Integer count) {
        return new Result<String>().ok(redBagService.sendRedBag(total, count));
    }

    @GetMapping("grabRedBag")
    @ApiOperation("抢红包")
    public Result<String> grabRedBag(@ApiParam("红包Id") @RequestParam("redBagId") Long redBagId, @ApiParam("用户Id") @RequestParam("userId") Long userId) {
        return new Result<String>().ok(redBagService.grabRedBag(redBagId, userId));
    }

    @GetMapping("addBlackUser")
    @ApiOperation("添加黑名单用户")
    public Result<String> addBlackUser(@ApiParam("用户Id") @RequestParam("userId") Long userId) {
        return new Result<String>().ok(blackUserService.addBlackUser(userId));
    }

    @GetMapping("removeBlackUser")
    @ApiOperation("移除黑名单用户")
    public Result<String> removeBlackUser(@ApiParam("用户Id") @RequestParam("userId") Long userId) {
        return new Result<String>().ok(blackUserService.removeBlackUser(userId));
    }


}
