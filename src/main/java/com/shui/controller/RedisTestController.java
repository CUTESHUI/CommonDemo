package com.shui.controller;

import com.shui.annotation.IpInterceptor;
import com.shui.dao.UserDao;
import com.shui.domain.GeoDTO;
import com.shui.domain.entity.User;
import com.shui.service.redis.BlackUserServiceImpl;
import com.shui.service.redis.LotteryServiceImpl;
import com.shui.service.redis.RedBagServiceImpl;
import com.shui.utils.RedisGeoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private RedisGeoUtil redisGeoUtil;

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
    public String lottery() {
        return lotteryService.lottery();
    }


    @GetMapping("sendRedBag")
    @ApiOperation("发红包")
    @IpInterceptor(requestCounts = 1, expiresTimeSecond = 1, isRestful = true, restfulParamCounts = 2)
    public String sendRedBag(@ApiParam("红包总额") @RequestParam("total") Integer total, @ApiParam("红包个数") @RequestParam("count") Integer count) {
        return redBagService.sendRedBag(total, count);
    }

    @GetMapping("grabRedBag")
    @ApiOperation("抢红包")
    public String grabRedBag(@ApiParam("红包Id") @RequestParam("redBagId") Long redBagId, @ApiParam("用户Id") @RequestParam("userId") Long userId) {
        return redBagService.grabRedBag(redBagId, userId);
    }

    @GetMapping("addBlackUser")
    @ApiOperation("添加黑名单用户")
    public String addBlackUser(@ApiParam("用户Id") @RequestParam("userId") Long userId) {
        return blackUserService.addBlackUser(userId);
    }

    @GetMapping("removeBlackUser")
    @ApiOperation("移除黑名单用户")
    public String removeBlackUser(@ApiParam("用户Id") @RequestParam("userId") Long userId) {
        return blackUserService.removeBlackUser(userId);
    }

    @PostMapping("addGeoLocation")
    @ApiOperation("增加name位置")
    public void addGeoLocation(@RequestBody GeoDTO dto) {
        redisGeoUtil.addGeoLocation(dto.getGeoKey(), dto.getNames().get(0), dto.getX(), dto.getY());
    }

    @PostMapping("deleteGeoLocation")
    @ApiOperation("删除name位置")
    public void deleteGeoLocation(@RequestBody GeoDTO dto) {
        redisGeoUtil.deleteGeoLocation(dto.getGeoKey(), dto.getNames().get(0));
    }

    @PostMapping("updateGeoLocation")
    @ApiOperation("更新name位置")
    public void updateGeoLocation(@RequestBody GeoDTO dto) {
        redisGeoUtil.deleteGeoLocation(dto.getGeoKey(), dto.getNames().get(0));
        redisGeoUtil.addGeoLocation(dto.getGeoKey(), dto.getNames().get(0), dto.getX(), dto.getY());
    }

    @PostMapping("getGeoLocationByName")
    @ApiOperation("获取name位置")
    public List<Point> getGeoLocationByName(@RequestBody GeoDTO dto) {
        return redisGeoUtil.getGeoLocationByName(dto.getGeoKey(), dto.getNames().get(0));
    }

    @PostMapping("getGeoLocationByNames")
    @ApiOperation("获取names位置")
    public List<Point> getGeoLocationByNames(@RequestBody GeoDTO dto) {
        return redisGeoUtil.getGeoLocationByNames(dto.getGeoKey(), String.valueOf(dto.getNames()));
    }

    @PostMapping("getDistance")
    @ApiOperation("计算name1, name2相隔距离")
    public Distance getDistance(@RequestBody GeoDTO dto) {
        RedisGeoCommands.DistanceUnit distanceUnit = RedisGeoCommands.DistanceUnit.KILOMETERS;
        if (dto.getDistanceUnitType() == 0) {
            distanceUnit = RedisGeoCommands.DistanceUnit.MILES;
        }
        return redisGeoUtil.getDistance(dto.getGeoKey(), dto.getNames().get(0), dto.getNames().get(1), distanceUnit);
    }

    @PostMapping("getRadiusByName")
    @ApiOperation("获取name附近distance内的limit个用户")
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getRadiusByName(@RequestBody GeoDTO dto) {
        RedisGeoCommands.DistanceUnit distanceUnit = RedisGeoCommands.DistanceUnit.KILOMETERS;
        if (dto.getDistanceUnitType() == 0) {
            distanceUnit = RedisGeoCommands.DistanceUnit.METERS;
        }
        return redisGeoUtil.getRadiusByName(dto.getGeoKey(), dto.getNames().get(0), dto.getDistance(), distanceUnit, dto.getLimit());
    }

    @PostMapping("getRadiusByXY")
    @ApiOperation("获取x y附近distance内的limit个用户")
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getRadiusByXY(@RequestBody GeoDTO dto) {
        RedisGeoCommands.DistanceUnit distanceUnit = RedisGeoCommands.DistanceUnit.KILOMETERS;
        if (dto.getDistanceUnitType() == 0) {
            distanceUnit = RedisGeoCommands.DistanceUnit.METERS;
        }
        return redisGeoUtil.getRadiusByXY(dto.getGeoKey(), dto.getX(), dto.getY(), dto.getDistance(), distanceUnit, dto.getLimit());
    }

}
